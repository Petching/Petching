package com.Petching.petching.myPet.service;

import com.Petching.petching.myPet.dto.MyPetDto;
import com.Petching.petching.myPet.entity.MyPet;
import com.Petching.petching.myPet.repository.MyPetRepository;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor
@Transactional
public class MyPetService {
    private final MyPetRepository petRepository;
    private final UserService userService;

    public MyPet savedPet (MyPet pet) {
        long userId = userService.findSecurityContextHolderUserId();
        User user = userService.verifiedUser(userId);
        pet.setUser(user);

        return petRepository.save(pet);
    }


}
