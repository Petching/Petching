package com.Petching.petching.login.jwt.service;

import com.Petching.petching.exception.BusinessLogicException;
import com.Petching.petching.exception.ExceptionCode;
import com.Petching.petching.user.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

@Service @Getter
@RequiredArgsConstructor @Slf4j
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private Long accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer";

    private final UserRepository userRepository;

    public String createAccessToken (String email) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod))
                .withClaim(EMAIL_CLAIM, email)
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void sendAccessToken (HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료!");
    }

    /**
     * 헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX 에서 Bearer 를 제외하고 순수 토큰만 가져오자.
     * 헤더를 가져온 후 replace 로 "Bearer" 를 삭제
     */
    public Optional<String> extractRefreshToken (HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refresh -> refresh.replace(BEARER, ""));
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer ~ 에서 Bearer 를 제외하고 순수 토큰만.
     * 헤더를 가져오고 replace 로 "Bearer" 삭제
     */
    public Optional<String> extractAccessToken (HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refresh -> refresh.replace(BEARER, ""));
    }

    /**
     * AccessToken 에서 email 추출
     * 추출 전 JWT.require() 로 검증기 생성
     * verify 로 AccessToken 검증 후 유효하다면 getClaim() 으로 이메일 추출
     * 유효하지 않다면 빈 Optional 객체 반환
     */
    public Optional<String> extractEmail (String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(accessToken)
                    .getClaim(EMAIL_CLAIM)
                    .asString());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    /**
     * RefreshToken DB 에 저장 & 업데이트
     */
    public void updateRefreshToken (String email, String refreshToken) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken),
                        () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND)
                );
    }

    public boolean isTokenValid (String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰. {}", e.getMessage());
            return false;
        }
    }
}
