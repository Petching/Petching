package com.Petching.petching.tag.conditionTag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ConditionTagRepository extends JpaRepository<ConditionTag, Long> {
    ConditionTag findByBody(String body);
}
