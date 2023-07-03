package com.Petching.petching.tag.locationTag;

import com.Petching.petching.carepost.entity.CarePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarePost_LocationTagRepository extends JpaRepository<CarePost_LocationTag, Long> {
    Optional<CarePost_LocationTag> findByLocationTag_LocationTagId(long tagId);
    List<CarePost_LocationTag> findByCarePost (CarePost carePost);
    List<CarePost_LocationTag> findByLocationTag (LocationTag locationTag);
    void deleteAllByCarePost_PostId(long postId);
}
