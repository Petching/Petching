package com.Petching.petching.board.repository;

import com.Petching.petching.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b, COUNT(c) FROM Board b LEFT JOIN b.comments c WHERE b.boardId = :boardId GROUP BY b")
    List<Object[]> findBoardWithCommentCount(Long boardId);
}
