package com.Petching.petching.board.repository;

import com.Petching.petching.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b, COUNT(c) FROM Board b LEFT JOIN b.comments c WHERE b.boardId = :boardId GROUP BY b")
    List<Object[]> findBoardWithCommentCount(Long boardId);


    // Board 에서 random 한 img 를 최대 4개 불러오는 쿼리 NULL 은 제외.
    @Query(nativeQuery = true, value = "SELECT b.img_urls FROM board_img_urls b WHERE b.img_urls IS NOT NULL ORDER BY RAND() LIMIT 4")
    Optional<List<String>> findRandomBoardImages();

    @Query(value = "SELECT * FROM Board ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<Board> findRandomBoards();

    @Query(value = "SELECT * FROM board WHERE user_id = :userId", nativeQuery = true)
    Page<Board> findBoardsByUserId(Long userId, Pageable pageable);

}
