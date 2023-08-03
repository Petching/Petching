package com.Petching.petching.myPet.mapper;

import com.Petching.petching.myPet.dto.MyPetDto;
import com.Petching.petching.myPet.entity.MyPet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MyPetMapper {
    MyPet PostToEntity (MyPetDto.Post post);
    MyPet PatchToEntity (MyPetDto.Patch patch);
    MyPetDto.Response EntityToResponse (MyPet pet);

    List<MyPetDto.Response> ListEntityToListDto (List<MyPet> pets);
}
