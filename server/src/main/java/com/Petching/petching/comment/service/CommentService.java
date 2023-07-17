package com.Petching.petching.comment.service;


import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.service.BoardService;
import com.Petching.petching.comment.entity.Comment;
import com.Petching.petching.comment.repository.CommentRepository;
import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Comment comment){

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

    public Comment findComment(Long commentId) {
        return findVerifiedComment(commentId);
    }

    public Comment findVerifiedComment(long c_id) {
        Optional<Comment> optionalBoard =
                commentRepository.findById(c_id);

        return optionalBoard.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }


}
