package com.Petching.petching.login.oauth.userInfo;

import com.Petching.petching.login.jwt.service.JwtService;
import com.Petching.petching.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
}
