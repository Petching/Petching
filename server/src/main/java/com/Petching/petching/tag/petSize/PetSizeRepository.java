package com.Petching.petching.tag.petSize;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface PetSizeRepository extends JpaRepository<PetSize, Long> {
    PetSize findByBody(String body);
}
