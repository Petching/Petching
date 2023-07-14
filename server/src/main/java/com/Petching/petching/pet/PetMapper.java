package com.Petching.petching.pet;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {
    Pet petPostDtoToPet(PetDto.Post requestBody);
    Pet petPatchDtoToPet(PetDto.Patch requestBody);
    PetDto.Response petToPetPostResponseDto(Pet pet);
    List<PetDto.Response> petToPetPostResponseDtos(List<Pet> pets);


}
