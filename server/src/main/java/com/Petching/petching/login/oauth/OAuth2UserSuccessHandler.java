package com.Petching.petching.login.oauth;

import com.Petching.petching.login.jwt.util.CustomAuthorityUtils;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.user.entity.SocialType;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
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
import java.util.Optional;

@Component @RequiredArgsConstructor
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtToken jwtToken;
    private final UserRepository userRepository;
    private final CustomAuthorityUtils customAuthorityUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = ((OAuth2User) authentication.getPrincipal());

        String type = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(); // oauth 타입
        String email;
        String nickName;
        String proImg;
        SocialType oauth;

        if ("google".equals(type)) {
            email = String.valueOf(oAuth2User.getAttributes().get("email"));
            nickName = String.valueOf(oAuth2User.getAttributes().get("name"));
            proImg = String.valueOf(oAuth2User.getAttributes().get("picture"));
            oauth = SocialType.GOOGLE;
        }
        else {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) kakaoAccount.get("email");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            nickName = (String) profile.get("nickname");
            proImg = (String) profile.get("profile_image_url");
            oauth = SocialType.KAKAO;
        }

        User user;
        Optional<User> userOptional = userRepository.findByEmail(email);
        user = userOptional.orElseGet(() -> saveUser(email, oauth, nickName, proImg));

        redirect(request, response, user);
    }
    private User saveUser (String email, SocialType oauth, String name, String profileImg) {
        User user = new User();
        user.setEmail(email);
        user.setSocialType(oauth);
        user.setNickName(name);
        user.setProfileImgUrl(profileImg);
        user.setRoles(customAuthorityUtils.createRoles(email));

        return userRepository.save(user);
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
