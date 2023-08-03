package com.Petching.petching.myPet.controller;

import com.Petching.petching.myPet.dto.MyPetDto;
import com.Petching.petching.myPet.entity.MyPet;
import com.Petching.petching.myPet.mapper.MyPetMapper;
import com.Petching.petching.myPet.service.MyPetService;
import com.Petching.petching.response.SingleResponse;
import com.Petching.petching.user.service.UserService;
import com.Petching.petching.utils.URICreator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

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
    public ResponseEntity patchPet (@RequestBody @Valid MyPetDto.Patch patch) {
        MyPet myPet = petService.updatePet(petMapper.PatchToEntity(patch));

        return new ResponseEntity<>(new SingleResponse<>(petMapper.EntityToResponse(myPet)), HttpStatus.OK);
    }

    @GetMapping("/{user-id}")
    public ResponseEntity getAllPet (@PathVariable("user-id") @Positive long userId) {
        List<MyPet> pet = petService.findAllPet(userId);

        return new ResponseEntity(petMapper.ListEntityToListDto(pet), HttpStatus.OK);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity deletePet(@PathVariable("petId") @Positive long petId) {
        petService.deletePet(petId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
