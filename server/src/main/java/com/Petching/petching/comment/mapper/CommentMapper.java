package com.Petching.petching.comment.mapper;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.comment.dto.CommentDto;
import com.Petching.petching.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentPostDtoToComment(CommentDto.Post postDto);
    Comment commentPatchDtoToComment(CommentDto.Patch patchDto);

    default CommentDto.Response commentToCommentResponseDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto.Response.ResponseBuilder response = CommentDto.Response.builder();

        response.commentId( comment.getCommentId() );
        response.content( comment.getContent() );
        response.boardId( comment.getBoardId() );
        response.createdAt( comment.getCreatedAt() );

        response.nickName( comment.getUser().getNickName() );
        response.profileImgUrl( comment.getUser().getProfileImgUrl() );

        return response.build();
    }

    default List<CommentDto.Response> commentListToCommentResponseListDto(List<Comment> commentList){

        if(commentList == null)
            return null;

        List<CommentDto.Response> response =  commentList
                .stream()
                .map(comment->commentToCommentResponseDto(comment))
                .collect(Collectors.toList());

        return response;
    }

    default List<CommentDto.Response> commentPageToCommentResponseListDto(Page<Comment> commentPage){

        if(commentPage == null)
            return null;

        List<CommentDto.Response> response =                 commentPage
                .stream()
                .map(comment->commentToCommentResponseDto(comment))
                .collect(Collectors.toList());

        return response;
    }
}
