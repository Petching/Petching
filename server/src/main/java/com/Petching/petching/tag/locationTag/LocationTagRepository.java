package com.Petching.petching.tag.locationTag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationTagRepository extends JpaRepository<LocationTag, Long> {
    LocationTag findByBody(String body);
}
