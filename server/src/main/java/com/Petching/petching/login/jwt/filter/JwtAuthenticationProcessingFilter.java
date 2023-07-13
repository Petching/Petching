package com.Petching.petching.login.jwt.filter;

import com.Petching.petching.login.dto.LoginDto;
import com.Petching.petching.login.jwt.service.JwtService;
import com.Petching.petching.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        User user = (User) authResult.getPrincipal();

        String accessToken = delegateAccessToken(user);
        String refreshToken = delegateRefreshToken(user);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);  // 추가
    }


    private String delegateAccessToken(User user) {
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

    private String delegateRefreshToken(User user) {
        String subject = user.getEmail();
        Date expiration = jwtService.getTokenExpiration(jwtService.getRefreshTokenExpirationPeriod());
        String base64EncodedSecretKey = jwtService.encodeBase64SecretKey(jwtService.getSecretKey());

        String refreshToken = jwtService.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}