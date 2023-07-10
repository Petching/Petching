package com.Petching.petching.carepost.repository;

import com.Petching.petching.carepost.entity.CarePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarePostRepository extends JpaRepository<CarePost, Long> {
    CarePost findByPostId(Long postId);
}
