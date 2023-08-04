package com.Petching.petching.myPet.repository;

import com.Petching.petching.myPet.entity.MyPet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPetRepository extends JpaRepository<MyPet, Long> {
}
