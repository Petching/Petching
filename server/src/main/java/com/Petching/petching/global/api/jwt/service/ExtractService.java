package com.Petching.petching.global.api.jwt.service;


import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

        Long userId = null;
        userId = jwtToken.extractUserIdFromToken(authorization);

        if(userId != null)
            return userService.findUser(userId);

        throw new BusinessLogicException(ExceptionCode.NOT_AUTHORIZED);
    }


    public void isSameUser(User original, User request) {

        if(!Objects.equals(original.getUserId(), request.getUserId()))
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);

    }
}
