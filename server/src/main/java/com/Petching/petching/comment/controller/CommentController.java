package com.Petching.petching.comment.controller;

import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.service.BoardService;
import com.Petching.petching.comment.dto.CommentDto;
import com.Petching.petching.comment.entity.Comment;
import com.Petching.petching.comment.mapper.CommentMapper;
import com.Petching.petching.comment.service.CommentService;
import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.response.SingleResponse;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/boards/{boardId}")
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper mapper;
    private final BoardService boardService;
    private final UserService userService;
    private final JwtToken jwtToken;


    public CommentController(CommentService commentService, CommentMapper mapper, BoardService boardService, UserService userService, JwtToken jwtToken) {
        this.commentService = commentService;
        this.mapper = mapper;
        this.boardService = boardService;
        this.userService = userService;
        this.jwtToken = jwtToken;
    }

    @PostMapping
    public ResponseEntity postComment(@PathVariable("boardId") long boardId,
                                      @RequestBody CommentDto.Post requestBody,
                                      @RequestHeader("Authorization") String authorization) {

        authorization = authorization.replaceAll("Bearer ","");
        User requestUser = userService.findUser(jwtToken.extractUserIdFromToken(authorization));
        Board board = boardService.findBoardByMK(boardId);

        Comment comment = mapper.commentPostDtoToComment(requestBody);
        comment.setBoard(board);
        comment.setUser(requestUser);
        commentService.createComment(comment);

        board.setCommentCount(board.getCommentCount() + 1);
        boardService.updateBoard(board);


        URI uri = UriComponentsBuilder.newInstance()
                .path("/"+boardId+"/" + comment.getCommentId())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity patchComment(
            @PathVariable("commentId") long commentId,
            @RequestBody CommentDto.Patch requestBody,
            @RequestHeader("Authorization") String authorization) {

        authorization = authorization.replaceAll("Bearer ","");
        User requestUser = userService.findUser(jwtToken.extractUserIdFromToken(authorization));

        Comment comment = commentService.findComment(commentId);
        User writer = comment.getUser();

        if(Objects.equals(writer.getUserId(), requestUser.getUserId())){
            comment = mapper.commentPatchDtoToComment(requestBody);
            comment.setCommentId(commentId);
            Comment response = commentService.updateComment(comment);

            CommentDto.Response responseDto = mapper.commentToCommentResponseDto(response);

            return new ResponseEntity<>(new SingleResponse<>(responseDto), HttpStatus.OK);
        }

        throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(
            @PathVariable("boardId") long boardId,
            @PathVariable("commentId") long commentId,
            @RequestHeader("Authorization") String authorization) {

        authorization = authorization.replaceAll("Bearer ","");
        User requestUser = userService.findUser(jwtToken.extractUserIdFromToken(authorization));

        Comment comment = commentService.findComment(commentId);
        User writer = comment.getUser();

        if(Objects.equals(writer.getUserId(), requestUser.getUserId())){

            commentService.deleteComment(commentId);
            Board board = boardService.findBoardByMK(boardId);

            board.setCommentCount(board.getCommentCount() - 1);
            boardService.updateBoard(board);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
    }

    @GetMapping("/comments")
    public ResponseEntity getComments(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {


        Pageable pageable = PageRequest.of(page-1, size);
        Page<Comment> commentPage = commentService.findComments(pageable);


        List<CommentDto.Response> responses =
                mapper.commentPageToCommentResponseListDto(commentPage);



        return new ResponseEntity<>(new MultiResponse<>(responses, commentPage), HttpStatus.OK);
    }

    @GetMapping("{commentId}")
    public ResponseEntity getComment(
            @PathVariable("commentId") Long commentId) {
        Comment comment = commentService.findComment(commentId);

        CommentDto.Response responseDto = mapper.commentToCommentResponseDto(comment);

        return new ResponseEntity<>(new SingleResponse<>(responseDto), HttpStatus.OK);
    }
}
