package com.Petching.petching.board.mapper;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board boardPostDtoToBoard(BoardDto.Post postDto);
    Board boardPatchDtoToBoard(BoardDto.Patch patchDto);
    default BoardDto.Detail boardToBoardDetailDto(Board board){
        if ( board == null ) {
            return null;
        }


        BoardDto.Detail.DetailBuilder detail = BoardDto.Detail.builder();
        detail.boardId( board.getBoardId() );
        detail.title( board.getTitle() );
        detail.content(board.getContent());
        detail.likes( board.getLikes() );
        detail.createdAt( board.getCreatedAt() );
        List<String> list = board.getImgUrls();

        if ( list != null ) {
            detail.imgUrls( new ArrayList<>( list ) );
        }

        detail.commentCount(board.getCommentCount());
        detail.nickName(board.getUser().getNickName());
        detail.profileImgUrl(board.getUser().getProfileImgUrl());

        return detail.build();
    }
    default List<BoardDto.Response> boardPageToBoardResponseListDto(Page<Board> boardPage){

        if(boardPage == null)
            return null;

        List<BoardDto.Response> response =                 boardPage
                .stream()
                .map(board->boardToBoardResponseDto(board))
                .collect(Collectors.toList());

        return response;
    }
    default BoardDto.Response boardToBoardResponseDto(Board board){

        if ( board == null ) {
            return null;
        }


        BoardDto.Response.ResponseBuilder response = BoardDto.Response.builder();
        response.boardId( board.getBoardId() );
        response.title( board.getTitle() );
        response.likes( board.getLikes() );
        response.createdAt( board.getCreatedAt() );
        List<String> list = board.getImgUrls();

        if ( list != null ) {
            response.imgUrls( new ArrayList<>( list ) );
        }

        response.commentCount(board.getCommentCount());
        response.nickName(board.getUser().getNickName());
        response.profileImgUrl(board.getUser().getProfileImgUrl());

        return response.build();
    }
}
