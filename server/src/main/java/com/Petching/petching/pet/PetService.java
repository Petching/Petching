package com.Petching.petching.pet;

import com.Petching.petching.carepost.entity.CarePost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;
    private final PetMapper mapper;

    public Pet savePet(PetDto.Post post) {

        Pet newPet = mapper.petPostDtoToPet(post);

        return repository.save(newPet);
    }

    public Pet updatePet(PetDto.Patch patch, Long petId) {

        Pet findPet = repository.findByPetId(petId);

        Optional.ofNullable(patch.getName())
                .ifPresent(findPet::setName);
        Optional.ofNullable(patch.getBreed())
                .ifPresent(findPet::setBreed);
        Optional.ofNullable(patch.getGender())
                .ifPresent(findPet::setGender);
        Optional.ofNullable(patch.getAge())
                .ifPresent(findPet::setAge);
        Optional.ofNullable(patch.getWeight())
                .ifPresent(findPet::setWeight);
        Optional.ofNullable(patch.getIsVaccination())
                .ifPresent(findPet::setIsVaccination);
        Optional.ofNullable(patch.getMemo())
                .ifPresent(findPet::setMemo);

        return repository.save(findPet);
    }

    public Pet findPet(long petId) {

        return repository.findByPetId(petId);

    }

    public List<Pet> findAllPet() {

        return repository.findAll(Sort.by("petId").descending());

    }

    public void deletePet(long petId) {

        repository.deleteById(petId);

    }

}
