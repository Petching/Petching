package com.Petching.petching.user.controller;

import com.Petching.petching.user.dto.UserPatchDto;
import com.Petching.petching.user.dto.UserPostDto;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.mapper.UserMapper;
import com.Petching.petching.user.service.UserService;
import com.Petching.petching.utils.URICreator;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/")
@Validated
public class UserController {
    private final UserMapper mapper;
    private final UserService service;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("sign-up")
    public ResponseEntity postUser (@RequestBody @Valid UserPostDto postDto) {
        User user = service.savedUser(postDto);
        URI uri = URICreator.createUri("sing-up", user.getUserId());

        return ResponseEntity.created(uri).build();
    }
    @PatchMapping
    public ResponseEntity patchUser (@RequestBody @Valid UserPatchDto patchDto) {

        User update = service.updatedUser(patchDto);
        return new ResponseEntity(mapper.EntityToResponseDto(update), HttpStatus.OK);
    }
    @GetMapping("{users-id}")
    public ResponseEntity getUser (@PathVariable("users-id") @Positive long usersId) {

        return new ResponseEntity(mapper.EntityToResponseDto(service.findUser(usersId)), HttpStatus.OK);
    }

    @DeleteMapping("{users-id}")
    public ResponseEntity deleteUser (@PathVariable("users-id") @Positive long usersId) {

        service.deletedUser(usersId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
