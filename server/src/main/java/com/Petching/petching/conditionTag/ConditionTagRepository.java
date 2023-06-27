package com.Petching.petching.conditionTag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConditionTagRepository extends JpaRepository<ConditionTag, Long> {
    ConditionTag findByBody(String body);
}
