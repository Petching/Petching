package com.Petching.petching.conditionTag;

import com.Petching.petching.carepost.entity.CarePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CarePost_ConditionTagRepository extends JpaRepository<CarePost_ConditionTag, Long> {

    Optional<CarePost_ConditionTag> findByConditionTag_ConditionTagId(long tagId);
    List<CarePost_ConditionTag> findByCarePost (CarePost carePost);
    List<CarePost_ConditionTag> findByConditionTag (ConditionTag conditionTag);
    void deleteAllByCarePost_PostId(long postId);
}
