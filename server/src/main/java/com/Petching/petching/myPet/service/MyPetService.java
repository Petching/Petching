package com.Petching.petching.myPet.service;

import com.Petching.petching.myPet.dto.MyPetDto;
import com.Petching.petching.myPet.entity.MyPet;
import com.Petching.petching.myPet.repository.MyPetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor
@Transactional
public class MyPetService {
    private final MyPetRepository petRepository;

    private MyPet savedPet (MyPetDto.Post post) {
        return null;
    }
}
