package com.Petching.petching.question.repository;


import com.Petching.petching.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "SELECT * FROM question WHERE user_id = :userId", nativeQuery = true)
    Page<Question> findQuestionsByUserId(Long userId, Pageable pageable);



}
