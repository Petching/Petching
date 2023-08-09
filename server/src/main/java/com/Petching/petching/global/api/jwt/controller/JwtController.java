package com.Petching.petching.global.api.jwt.controller;



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
@RequestMapping("/api/jwt")
public class JwtController {
    private final JwtToken jwtToken;

    private final UserService userService;

    public JwtController(JwtToken jwtToken, UserService userService) {
        this.jwtToken = jwtToken;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity postRefresh(@RequestHeader("Refresh") String refreshHeader){

        try {

            if(jwtToken.verifyTokenExpiration(refreshHeader)){

                User requestUser = userService.findUserByEmail(jwtToken.extractUserEmailFromToken(refreshHeader));

                String accessToken = jwtToken.delegateAccessToken(requestUser);

                return new ResponseEntity("Bearer ".concat(accessToken), HttpStatus.CREATED);
            }else {
                return new ResponseEntity("UNAUTHORIZED",HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }



    }
}
