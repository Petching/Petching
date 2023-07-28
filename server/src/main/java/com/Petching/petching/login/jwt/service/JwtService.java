package com.Petching.petching.login.jwt.service;

import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component @Getter
public class JwtService {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private int accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private int refreshTokenExpirationPeriod;

//    @Value("${jwt.access.header}")
//    private String accessHeader;
//
//    @Value("${jwt.refresh.header}")
//    private String refreshHeader;

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String subject, Date expiration, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
        return claims;
    }

    public void verifySignature(String jws, String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    public Date getTokenExpiration(int expirationMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expirationMinutes);
        Date expiration = calendar.getTime();

        return expiration;
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }

    public String extractTokenFromHeader(String authorizationHeader){

        if(!StringUtils.isEmpty(authorizationHeader)){

            authorizationHeader =
                    authorizationHeader.replaceAll("Bearer ", "");

            // "Bearer " 제외한 토큰 값만 추출
            if(verifyTokenExpiration(authorizationHeader)){
                return authorizationHeader;
            }

        }

        throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
    }

    public boolean verifyTokenExpiration(String requestToken) {

        if (!StringUtils.isEmpty(requestToken)) {
            Jws<Claims> claims = getClaims(requestToken, encodeBase64SecretKey(secretKey));
            Date expiration = null;

            if(Optional.ofNullable(claims.getBody().getExpiration()).isPresent()) {
                expiration = claims.getBody().getExpiration();
                Date now = new Date();

                return now.before(expiration);
                // 현재 시간이 토큰의 만료 시간 이전인지 확인
            }
        }

        return false;
    }

    public Long extractUserIdFromToken(String requestToken){

        if(!StringUtils.isEmpty(requestToken)) {

            Jws<Claims> claims =
                    getClaims(requestToken, encodeBase64SecretKey(secretKey));

            if(Optional.ofNullable(claims.getBody().get("userId")).isPresent()){
                return Long.parseLong(String.valueOf(claims.getBody().get("userId")));
            }

        }
        throw new BusinessLogicException(ExceptionCode.CONTENT_NOT_FOUND);
    }


    public Long getUserIdFromAuthHeader(String authorizationHeader) {

        System.out.println("authorizationHeader: "+authorizationHeader);

        if(!StringUtils.isEmpty(authorizationHeader)){
            String token = extractTokenFromHeader(authorizationHeader);
            return extractUserIdFromToken(token);
        }

        throw new BusinessLogicException(ExceptionCode.CONTENT_NOT_FOUND);
    }
}
