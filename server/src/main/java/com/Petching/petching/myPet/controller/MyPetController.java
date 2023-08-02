package com.Petching.petching.myPet.controller;

import com.Petching.petching.myPet.dto.MyPetDto;
import com.Petching.petching.myPet.entity.MyPet;
import com.Petching.petching.myPet.mapper.MyPetMapper;
import com.Petching.petching.myPet.service.MyPetService;
import com.Petching.petching.user.service.UserService;
import com.Petching.petching.utils.URICreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users/pet")
@RequiredArgsConstructor
public class MyPetController {
    private final MyPetService petService;
    private final MyPetMapper petMapper;

    @PostMapping
    public ResponseEntity postPet (@RequestBody @Valid MyPetDto.Post post) {
        MyPet myPet = petService.savedPet(petMapper.PostToEntity(post));
        URI uri = URICreator.createUri("/users/pet", myPet.getMyPetId());

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping
    public ResponseEntity patchPet (@RequestBody MyPetDto.Patch patch) {
        return null;
    }

    @GetMapping
    public ResponseEntity getAllPet () {
        return null;
    }

    @DeleteMapping
    public ResponseEntity deletePet() {
        return null;
    }
}
