package com.Petching.petching.board.mapper;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.comment.dto.CommentDto;
import com.Petching.petching.comment.entity.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-19T14:08:17+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board boardPostDtoToBoard(BoardDto.Post postDto) {
        if ( postDto == null ) {
            return null;
        }

        Board.BoardBuilder board = Board.builder();

        board.title( postDto.getTitle() );
        board.content( postDto.getContent() );
        List<String> list = postDto.getImgUrls();
        if ( list != null ) {
            board.imgUrls( new ArrayList<String>( list ) );
        }

        return board.build();
    }

    @Override
    public Board boardPatchDtoToBoard(BoardDto.Patch patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        Board.BoardBuilder board = Board.builder();

        board.boardId( patchDto.getBoardId() );
        board.title( patchDto.getTitle() );
        board.content( patchDto.getContent() );
        List<String> list = patchDto.getImgUrls();
        if ( list != null ) {
            board.imgUrls( new ArrayList<String>( list ) );
        }

        return board.build();
    }

    @Override
    public BoardDto.Response boardToBoardResponseDto(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDto.Response.ResponseBuilder response = BoardDto.Response.builder();

        response.boardId( board.getBoardId() );
        response.title( board.getTitle() );
        if ( board.getLikes() != 0 ) {
            response.likes( board.getLikes() );
        }
        response.createdAt( board.getCreatedAt() );
        List<String> list = board.getImgUrls();
        if ( list != null ) {
            response.imgUrls( new ArrayList<String>( list ) );
        }

        return response.build();
    }

    @Override
    public BoardDto.Detail boardToBoardDetailDto(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDto.Detail.DetailBuilder detail = BoardDto.Detail.builder();
        if ( board.getLikes() != 0 ) {
            detail.likes( board.getLikes() );
        }
        detail.boardId( board.getBoardId() );
        detail.title( board.getTitle() );
        detail.content( board.getContent() );
        detail.createdAt( board.getCreatedAt() );
        detail.comments( commentListToResponseList( board.getComments() ) );
        detail.commentCount( board.getCommentCount() );
        List<String> list1 = board.getImgUrls();
        if ( list1 != null ) {
            detail.imgUrls( new ArrayList<String>( list1 ) );
        }

        return detail.build();
    }

    protected CommentDto.Response commentToResponse(Comment comment) {
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

    protected List<CommentDto.Response> commentListToResponseList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDto.Response> list1 = new ArrayList<CommentDto.Response>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentToResponse( comment ) );
        }

        return list1;
    }
}
