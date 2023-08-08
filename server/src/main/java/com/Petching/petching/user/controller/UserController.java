package com.Petching.petching.user.controller;

import com.Petching.petching.response.SingleResponse;
import com.Petching.petching.user.dto.CheckDto;
import com.Petching.petching.user.dto.UserPatchDto;
import com.Petching.petching.user.dto.UserPostDto;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.mapper.UserMapper;
import com.Petching.petching.user.service.UserService;
import com.Petching.petching.utils.URICreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/users/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserMapper mapper;
    private final UserService service;

    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("sign-up")
    public ResponseEntity postUser (@RequestBody @Valid UserPostDto postDto) {
        User user = service.savedUser(postDto);
        URI uri = URICreator.createUri("sign-up", user.getUserId());

        return ResponseEntity.created(uri).build();
    }
    @PostMapping("check-id")
    public boolean doubleCheckId (@RequestBody @Valid CheckDto dto) {
        return service.checkId(dto);
    }

    @PostMapping("check-nick")
    public boolean doubleCheckNick (@RequestBody @Valid CheckDto dto) {
        return service.checkNick(dto);
    }

    @PatchMapping
    public ResponseEntity patchUser (@RequestBody @Valid UserPatchDto patchDto) {

        User update = service.updatedUser(patchDto);
        return new ResponseEntity(new SingleResponse<>(mapper.EntityToResponseDto(update)), HttpStatus.OK);
    }
    @PostMapping("check-pw")
    public boolean doubleCheckPassword (@RequestBody CheckDto dto) {
        return service.checkPassword(dto);
    }

    @GetMapping("{users-id}")
    public ResponseEntity getUser (@PathVariable("users-id") @Positive long usersId) {

        return new ResponseEntity(new SingleResponse<>
                (mapper.EntityToGetResponseDto(service.findUser(usersId))), HttpStatus.OK);
    }

    @DeleteMapping("{users-id}")
    public ResponseEntity deleteUser (@PathVariable("users-id") @Positive long usersId) {

        service.deletedUser(usersId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
