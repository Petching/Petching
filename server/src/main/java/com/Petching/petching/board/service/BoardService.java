package com.Petching.petching.board.service;

import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.repository.BoardRepository;
import com.Petching.petching.exception.BusinessLogicException;
import com.Petching.petching.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board createBoard(Board board){
        return boardRepository.save(board);
    }
    public Board updateBoard(Board board){

    }
    public Board findBoard(long boardId){


    }
    public Page<Board> findBoards(Pageable pageable){
        return boardRepository.findAll(pageable);
    }
    public void deleteBoard(long boardId){
        Board board = findVerifiedBoard(boardId);
        boardRepository.delete(board);
    }
    // 존재하는 Board인지 검증하는 메서드
    public Board findVerifiedBoard(long boardId){
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard = optionalBoard.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findBoard;

    }


}
