package com.Petching.petching.login.oauth;

import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component @RequiredArgsConstructor
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtToken jwtToken;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = ((OAuth2User) authentication.getPrincipal());
        String userEmail = oAuth2User.getAttribute("email");
        if (userEmail == null) {
            Map<String, Object> email = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            userEmail = (String) email.get("email");
        }
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        redirect(request, response, user);
    }

    private void redirect (HttpServletRequest request, HttpServletResponse response, User user) throws IOException{
        String accessToken = jwtToken.delegateAccessToken(user);
        String refreshToken = jwtToken.delegateRefreshToken(user);

        String uri = createURI(accessToken, refreshToken).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }
    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("petching.net")
//                .port(80)
//                .path("/receive-token.html")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
