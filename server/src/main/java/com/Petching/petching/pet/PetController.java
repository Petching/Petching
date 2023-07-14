package com.Petching.petching.pet;

import com.Petching.petching.response.SingleResponse;
import com.Petching.petching.utils.URICreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/pets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PetController {
    private final PetService service;
    private final PetMapper mapper;

    @PostMapping
    public ResponseEntity postPet(@Validated @RequestBody PetDto.Post requestBody) {

        Pet createdPet = service.savePet(requestBody);

        URI uri = URICreator.createUri("/pets", createdPet.getPetId());

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{pet-id}")
    public ResponseEntity patchPet(@Validated @RequestBody PetDto.Patch requestBody,
                                   @PathVariable("pet-id") @Positive long petId) {

        Pet updatedPet = service.updatePet(requestBody,petId);
        return new ResponseEntity(new SingleResponse<>(mapper.petToPetPostResponseDto(updatedPet)), HttpStatus.OK);
    }

    @GetMapping("/{pet-id}")
    public ResponseEntity getPet(@PathVariable("pet-id") @Positive long petId) {


        Pet findPet = service.findPet(petId);

        return new ResponseEntity(new SingleResponse<>(mapper.petToPetPostResponseDto(findPet)), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity getAllPet() {


        List<Pet> findPets = service.findAllPet();

        return new ResponseEntity(new SingleResponse<>(mapper.petToPetPostResponseDtos(findPets)), HttpStatus.OK);
    }

    @DeleteMapping("/{pet-id}")
    public ResponseEntity deletePet(@PathVariable("pet-id") @Positive long petId) {

        service.deletePet(petId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
