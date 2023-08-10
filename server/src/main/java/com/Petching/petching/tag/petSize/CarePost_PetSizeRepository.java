package com.Petching.petching.tag.petSize;

import com.Petching.petching.carepost.entity.CarePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarePost_PetSizeRepository extends JpaRepository<CarePost_PetSize, Long> {
    Optional<CarePost_PetSize> findByPetSize_PetSizeId(long petSizeId);
    List<CarePost_PetSize> findByCarePost (CarePost carePost);
    List<CarePost_PetSize> findByPetSize (PetSize petSize);
    void deleteAllByCarePost_PostId(long postId);
}
