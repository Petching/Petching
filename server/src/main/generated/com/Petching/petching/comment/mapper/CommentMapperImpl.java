package com.Petching.petching.comment.mapper;

import com.Petching.petching.comment.dto.CommentDto;
import com.Petching.petching.comment.entity.Comment;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-18T16:21:36+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostDtoToComment(CommentDto.Post postDto) {
        if ( postDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setContent( postDto.getContent() );

        return comment;
    }

    @Override
    public Comment commentPatchDtoToComment(CommentDto.Patch patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setContent( patchDto.getContent() );

        return comment;
    }

    @Override
    public CommentDto.Response commentToCommentResponseDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        long commentId = 0L;
        String content = null;
        long boardId = 0L;
        LocalDateTime createdAt = null;
        LocalDateTime modifiedAt = null;

        commentId = comment.getCommentId();
        content = comment.getContent();
        boardId = comment.getBoardId();
        createdAt = comment.getCreatedAt();
        modifiedAt = comment.getModifiedAt();

        CommentDto.Response response = new CommentDto.Response( commentId, content, boardId, createdAt, modifiedAt );

        return response;
    }
}
