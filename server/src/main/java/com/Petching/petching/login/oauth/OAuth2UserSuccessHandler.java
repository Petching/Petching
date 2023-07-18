package com.Petching.petching.login.oauth;

import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.login.jwt.util.CustomAuthorityUtils;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component @RequiredArgsConstructor
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtToken jwtToken;
    private final CustomAuthorityUtils authorityUtils;
    private final UserRepository userRepository;
// todo : 전체 수정
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String providerType = authToken.getAuthorizedClientRegistrationId();

        String email;
        String nickName;
        String profileImage;

        if ("google".equals(providerType)) {
            email = String.valueOf(oAuth2User.getAttributes().get("email"));
            nickName = String.valueOf(oAuth2User.getAttributes().get("name"));
            profileImage = String.valueOf(oAuth2User.getAttributes().get("picture"));

        } else if ("kakao".equals(providerType)) {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) kakaoAccount.get("email");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            nickName = (String) profile.get("nickname");
            profileImage = (String) profile.get("profile_image_url");
        }
        else {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }

        Optional<User> optionalMember = userRepository.findByEmail(email);

        User user;
        if (optionalMember.isEmpty()) {
            user = saveUser(email, nickName, profileImage);
        } else {
            user = optionalMember.get();
        }
        redirect(request, response, user);
    }

    private User saveUser(String email, String nickname, String profileImage) {
        userRepository.findByEmail(email).ifPresent(it -> {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        });
        User user = new User();
        user.setEmail(email);
        user.setNickName(nickname);
        user.setProfileImage(profileImage);
        List<String> roles = authorityUtils.createRoles(email);
        user.setRoles(roles);

        return user;
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, User member) throws IOException {
        log.info("# Authenticated 성공성공");

        String accessToken = jwtToken.delegateAccessToken(member);
        String refreshToken = jwtToken.delegateRefreshToken(member);

        response.setHeader("Authorization", accessToken);
        response.setHeader("Refresh", refreshToken);

        String uri = createURI(accessToken, refreshToken).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);


        for (String headerName : response.getHeaderNames()) {
            log.info(headerName + ": " + response.getHeader(headerName));
        }
    }

    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("petching.net")
                .path("/oauth/redirect")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
