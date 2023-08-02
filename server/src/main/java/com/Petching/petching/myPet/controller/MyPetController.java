package com.Petching.petching.myPet.controller;

import com.Petching.petching.myPet.dto.MyPetDto;
import com.Petching.petching.myPet.mapper.MyPetMapper;
import com.Petching.petching.myPet.service.MyPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/pet")
@RequiredArgsConstructor
public class MyPetController {
    private final MyPetService petService;
    private final MyPetMapper petMapper;

    @PostMapping
    public ResponseEntity postPet (@RequestBody @Valid MyPetDto.Post post) {
        return null;
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
