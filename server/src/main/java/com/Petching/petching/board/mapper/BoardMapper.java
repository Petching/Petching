package com.Petching.petching.board.mapper;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board boardPostDtoToBoard(BoardDto.Post postDto);
    Board boardPatchDtoToBoard(BoardDto.Patch patchDto);
    BoardDto.Response boardToBoardResponseDto(Board board);
    BoardDto.Detail BoardToDetailDto(Board board);
}
