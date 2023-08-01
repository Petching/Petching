package com.Petching.petching.login.jwt.controller;



import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jwt")
public class JwtController {
    private final JwtToken jwtTokenizer;

    private final UserService userService;

    public JwtController(JwtToken jwtTokenizer, UserService userService) {
        this.jwtTokenizer = jwtTokenizer;
        this.userService = userService;
    }


    /*@PostMapping("/refresh")
    public ResponseEntity postRefresh(@RequestHeader("refresh") String authorizationHeader){

        try {

            if(jwtTokenizer.verifyTokenExpiration(authorizationHeader)){

                Jws<Claims> jwsClaims = jwtTokenizer.getClaimsByToken(authorizationHeader);
                User foundUser = userService.findUser((String) jwsClaims.getBody().get("sub"));

                Map<String , Object> claims = new HashMap<>();
                claims.put("username", foundUser.getEmail());
                claims.put("roles", foundUser.getRoles());
                claims.put("userId",foundUser.getUserId());

                String subject = foundUser.getEmail();
                Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

                String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

                String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

                return new ResponseEntity("Bearer ".concat(accessToken), HttpStatus.CREATED);
            }else {
                return new ResponseEntity("UNAUTHORIZED",HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }



    }*/
}
