package com.Petching.petching.comment.service;


import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.service.BoardService;
import com.Petching.petching.comment.entity.Comment;
import com.Petching.petching.comment.repository.CommentRepository;
import com.Petching.petching.exception.BusinessLogicException;
import com.Petching.petching.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardService boardService;

    public CommentService(CommentRepository commentRepository, BoardService boardService) {
        this.commentRepository = commentRepository;
        this.boardService = boardService;
    }

    public Comment createComment(Comment comment){
        Board board = verifyExistingBoard(comment.getBoard());
        comment.setBoard(board);
        board.addComment(comment);

        return commentRepository.save(comment);
    }
    public Comment updateComment(Comment comment) {
        Comment findComment = findVerifiedComment(comment.getCommentId());

        Optional.ofNullable(comment.getContent())
                .ifPresent(text->findComment.setContent(text));
        return commentRepository.save(findComment);
    }

    public void deleteComment(long commentId) {

        Comment comment	= commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }
    public List<Comment> findComments(){
        return commentRepository.findAll();
    }
    private Board verifyExistingBoard(Board board){
        return boardService.findVerifiedBoard(board.getBoardId());
    }
    public Comment findVerifiedComment(long c_id) {
        Optional<Comment> optionalBoard =
                commentRepository.findById(c_id);
        Comment findComment =
                optionalBoard.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return findComment;
    }
//    private final CommentRepository commentRepository;
//    private final BoardService boardService;
//
//    public CommentService(CommentRepository commentRepository, BoardService boardService) {
//        this.commentRepository = commentRepository;
//        this.boardService = boardService;
//    }
//
//    // 댓글 생성
//    public Comment createComment(Comment comment){
//        return commentRepository.save(comment);
//    }
//    // 댓글 수정
//    public Comment updateComment(Comment comment){
//        Comment findComment = findVeritiedComment(comment.getCommentId());
//
//        //글 수정
//        Optional.ofNullable(comment.getContent())
//                .ifPresent(text->findComment.setContent(text));
//        return commentRepository.save(findComment);
//
//    }
//    // 댓글 전체 조회
//    public List<Comment> findComments(){
//        return commentRepository.findAll();
//    }
//    // 댓글 삭제
//    public void deleteComment(long commentId){
//        Comment comment = commentRepository.findById(commentId)
//                .orElseThrow(()->new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
//    }
//    public Comment findVeritiedComment(long commentId){
//        Optional<Comment> optionalComment =
//                commentRepository.findById(commentId);
//        Comment findComment = optionalComment.orElseThrow(()->
//                new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
//        return findComment;
//    }
//
//

}
