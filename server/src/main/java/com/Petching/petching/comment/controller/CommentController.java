package com.Petching.petching.comment.controller;

import com.Petching.petching.comment.dto.CommentDto;
import com.Petching.petching.comment.entity.Comment;
import com.Petching.petching.comment.mapper.CommentMapper;
import com.Petching.petching.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;
    private CommentMapper mapper;

    public CommentController(CommentService commentService, CommentMapper mapper) {
        this.commentService = commentService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postComment(@Valid @RequestBody CommentDto.Post requestBody) {
        //uri 리턴 방식
        Comment comment = commentService.createComment(mapper.commentPostDtoToComment(requestBody));

        URI uri = UriComponentsBuilder.newInstance()
                .path("/comments/" + comment.getCommentId())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity patchComment(@Valid @RequestBody CommentDto.Patch requestBody,
                                       @PathVariable("comment-id") long id) {
        requestBody.setCommentId(id);
        Comment comment = mapper.commentPatchDtoToComment(requestBody);
        Comment response = commentService.updateComment(comment);

        return new ResponseEntity<>(mapper.commentToCommentResponseDto(response), HttpStatus.OK);
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") long id) {
        commentService.deleteComment(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity getComments() {
        List<Comment> comments = commentService.findComments();

        List<CommentDto.Response> responses =
                comments.stream()
                        .map(comment -> mapper.commentToCommentResponseDto(comment))
                        .collect(Collectors.toList());

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}

    //    private CommentMapper mapper;
//    private CommentService commentService;
//    @Autowired
//    public CommentController(CommentMapper mapper, CommentService commentService) {
//        this.mapper = mapper;
//        this.commentService = commentService;
//    }
//    @PostMapping
//    public ResponseEntity postComment(@Valid @RequestBody CommentDto.Post requestBody){
//        Comment comment = commentService.createComment(mapper.commentPostDtoToComment(requestBody));
//
//        URI uri = UriComponentsBuilder.newInstance()
//                .path("/api/comments/"+comment.getCommentId())
//                .build().toUri();
//
//        return ResponseEntity.created(uri).build();
//
//    }
//    @PatchMapping("/{id}")
//    public ResponseEntity patchComment(@Valid @RequestBody CommentDto.Patch requestBody,
//                                       @PathVariable("id") long id){
//        requestBody.setCommentId(id);
//        Comment comment = mapper.commentPatchDtoToComment(requestBody);
//        Comment response = commentService.updateComment(comment);
//
//        return new ResponseEntity<>(mapper.commentToCommentResponseDto(response), HttpStatus.OK);
//    }
//    @GetMapping
//    public ResponseEntity getComments(){
//        List<Comment> comments = commentService.findComments();
//
//        List<CommentDto.Response> response = comments.stream().map(comment->mapper.commentToCommentResponseDto(comment))
//                .collect(Collectors.toList());
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteComment(@PathVariable("id") long id) {
//        commentService.deleteComment(id);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

