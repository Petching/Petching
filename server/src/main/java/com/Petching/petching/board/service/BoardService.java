package com.Petching.petching.board.service;

import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.repository.BoardRepository;
import com.Petching.petching.global.aws.s3.service.S3Service;
import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    private final S3Service s3Service;

    // DI
    public BoardService(BoardRepository boardRepository, S3Service s3Service) {
        this.boardRepository = boardRepository;
        this.s3Service = s3Service;
    }

    public Board createBoard(Board board){

        return boardRepository.save(board);
    }
    public Board updateBoard(Board board){
        Board findBoard = findVerifiedBoard(board.getBoardId());

        Optional.ofNullable(board.getTitle())
                .ifPresent(title->findBoard.setTitle(title));
        Optional.ofNullable(board.getContent())
                .ifPresent(content->findBoard.setContent(content));
        Optional.ofNullable(board.getImgUrl())
                .ifPresent(imgUrl->findBoard.setImgUrl(imgUrl));

        return boardRepository.save(findBoard);
    }

    public Board findBoard(long boardId) {
        List<Object[]> resultList = boardRepository.findBoardWithCommentCount(boardId);

        if (!resultList.isEmpty()) {
            Object[] result = resultList.get(0);
            Board board = (Board) result[0];
            Long commentCount = (Long) result[1];
            board.setCommentCount(commentCount);
            return board;
        } else {
            throw new BusinessLogicException(ExceptionCode.CONTENT_NOT_FOUND);
        }

    }
    public Page<Board> findBoards(Pageable pageable){

        return boardRepository.findAll(pageable);
    }

    public void deleteBoard(long boardId){
        Board board = findVerifiedBoard(boardId);
        deleteImageFromBoard(board);
        boardRepository.delete(board);
    }


    public Board findBoardByMK(long boardId){

        Board board = findVerifiedBoard(boardId);

        return board;
    }

    // Board 에 존재하는 img 중 최대 4개를 가져오는 메서드
    public List<String> findBoardRandomImg(){

        Optional<List<String>> randomImgList = boardRepository.findRandomBoardImages();

        return randomImgList.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.CONTENT_NOT_FOUND));
    }


    // 존재하는 Board 인지 검증하는 메서드
    public Board findVerifiedBoard(long boardId){
        Optional<Board> optionalBoard = boardRepository.findById(boardId);

        return optionalBoard.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.CONTENT_NOT_FOUND));
    }

    public void deleteImageFromBoard(Board board) {

        Optional.ofNullable(board.getImgUrl())
                .ifPresent(imgUrl -> s3Service.deleteImage(imgUrl));

    }


}
