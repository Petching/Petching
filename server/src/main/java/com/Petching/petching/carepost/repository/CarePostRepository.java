package com.Petching.petching.carepost.repository;

import com.Petching.petching.carepost.entity.CarePost;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CarePostRepository extends JpaRepository<CarePost, Long> {
    CarePost findByPostId(Long postId);
    List<CarePost> findByLocationTagAndStartDayAndStartMonthAndStartYearAndEndDayAndEndMonthAndEndYear(
            String locationTag,
            Integer startDay, Integer startMonth, Integer startYear,
            Integer endDay, Integer endMonth, Integer endYear
    );

    @Query("SELECT c FROM CarePost c WHERE c.locationTag = :locationTag " +
            "AND (c.startDate <= :endDate AND c.endDate >= :startDate)")
    List<CarePost> findByLocationTagAndDates(
            @Param("locationTag") String locationTag,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
