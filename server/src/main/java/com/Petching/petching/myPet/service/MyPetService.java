package com.Petching.petching.myPet.service;

import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.myPet.dto.MyPetDto;
import com.Petching.petching.myPet.entity.MyPet;
import com.Petching.petching.myPet.repository.MyPetRepository;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public MyPet updatePet (MyPet pet) {
        long userId = userService.findSecurityContextHolderUserId();
        User user = userService.verifiedUser(userId);

        MyPet petSave = verifiedPet(user, pet.getMyPetId());

        Optional.ofNullable(pet.getName()).ifPresent(name -> petSave.updateName(name));
        Optional.of(pet.getAge()).ifPresent(age -> petSave.updateAge(age));
        Optional.ofNullable(pet.getGender()).ifPresent(gender -> petSave.updateGender(gender));
        Optional.ofNullable(pet.getSpecies()).ifPresent(species -> petSave.updateSpecies(species));
        Optional.ofNullable(pet.getSignificant()).ifPresent(significant -> petSave.updateSignificant(significant));
        Optional.ofNullable(pet.getPetImgUrl()).ifPresent(img -> petSave.updateImgUrl(img));

        return petRepository.save(petSave);
    }

    public List<MyPet> findAllPet (long userId) {
        List<MyPet> op = petRepository.findAll()
                .stream().filter(user -> user.getUser().getUserId().equals(userId))
                .collect(Collectors.toList());

        return op;
    }

    public void deletePet (long petId) {
        long userId = userService.findSecurityContextHolderUserId();
        User user = userService.verifiedUser(userId);

        MyPet pet = verifiedPet(user, petId);

        petRepository.delete(pet);
    }

    public MyPet verifiedPet(User user, long petId) {
        Optional<MyPet> optionalMyPet = petRepository.findById(petId);
        MyPet pet = optionalMyPet.orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));

        if (!pet.getUser().equals(user)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

        return pet;
    }

}
