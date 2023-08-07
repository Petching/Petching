package com.Petching.petching.login.oauth.userInfo;

import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.login.jwt.service.JwtService;
import com.Petching.petching.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class JwtToken {
    private final JwtService jwtService;


    public String delegateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("roles", user.getRoles());
        claims.put("userId", user.getUserId());


        String subject = user.getEmail();
        Date expiration = jwtService.getTokenExpiration(jwtService.getRefreshTokenExpirationPeriod());

        String base64EncodedSecretKey = jwtService.encodeBase64SecretKey(jwtService.getSecretKey());

        String accessToken = jwtService.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    public String delegateRefreshToken(User user) {
        String subject = user.getEmail();
        Date expiration = jwtService.getTokenExpiration(jwtService.getRefreshTokenExpirationPeriod());
        String base64EncodedSecretKey = jwtService.encodeBase64SecretKey(jwtService.getSecretKey());

        String refreshToken = jwtService.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    public Long extractUserIdFromToken(String requestToken){

        if(!StringUtils.isEmpty(requestToken)) {

            Jws<Claims> claims =
                    jwtService.getClaims(requestToken, jwtService.encodeBase64SecretKey(jwtService.getSecretKey()));

            if(Optional.ofNullable(claims.getBody().get("userId")).isPresent()){
                return Long.parseLong(String.valueOf(claims.getBody().get("userId")));
            }

        }
        return null;
    }

    public String  extractUserEmailFromToken(String requestToken){

        if(!StringUtils.isEmpty(requestToken)) {

            Jws<Claims> claims =
                    jwtService.getClaims(requestToken, jwtService.encodeBase64SecretKey(jwtService.getSecretKey()));

            if(Optional.ofNullable(claims.getBody().get("sub")).isPresent()){
                return String.valueOf(claims.getBody().get("sub"));
            }

        }
        return null;
    }

    public boolean verifyTokenExpiration(String requestToken) {

        if (!StringUtils.isEmpty(requestToken)) {
            Jws<Claims> claims = jwtService.getClaims(requestToken, jwtService.encodeBase64SecretKey(jwtService.getSecretKey()));
            Date expiration = null;
            //System.out.println("claims: "+claims.getBody());
            if(Optional.ofNullable(claims.getBody().getExpiration()).isPresent()) {
                expiration = claims.getBody().getExpiration();
                Date now = new Date();

                return now.before(expiration);
                // 현재 시간이 토큰의 만료 시간 이전인지 확인
            }
        }

        return false;
    }


}
