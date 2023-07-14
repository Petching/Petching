package com.Petching.petching.pet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet,Long> {

    Pet findByPetId(Long petId);
}
