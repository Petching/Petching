package com.Petching.petching.global.api.jwt.service;


import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class ExtractService {

    private final JwtToken jwtToken;

    private final UserService userService;


    public ExtractService(JwtToken jwtToken, UserService userService) {
        this.jwtToken = jwtToken;
        this.userService = userService;
    }


    public User getUserFromToken(String authorization) {
        authorization = authorization.replaceAll("Bearer ","");

        return userService.findUser(jwtToken.extractUserIdFromToken(authorization));
    }


}
