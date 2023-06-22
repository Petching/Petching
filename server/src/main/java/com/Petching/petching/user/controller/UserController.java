package com.Petching.petching.user.controller;

import com.Petching.petching.user.dto.UserPostDto;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.mapper.UserMapper;
import com.Petching.petching.user.service.UserService;
import com.Petching.petching.utils.URICreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
@Validated
public class UserController {
    private final UserMapper mapper;
    private final UserService service;

    @PostMapping("sing-up")
    public ResponseEntity postUser (@RequestBody @Valid UserPostDto postDto) {
        User user = service.savedUser(postDto);
        URI uri = URICreator.createUri("sing-up", user.getUserId());

        return ResponseEntity.created(uri).build();
    }
}
