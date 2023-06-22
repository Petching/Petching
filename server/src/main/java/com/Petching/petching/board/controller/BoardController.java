package com.Petching.petching.board.controller;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.mapper.BoardMapper;
import com.Petching.petching.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping("/api/boards")
public class BoardController {
    private BoardService boardService;
    private BoardMapper mapper;

    public BoardController(BoardService boardService, BoardMapper mapper) {
        this.boardService = boardService;
        this.mapper = mapper;
    }

    // 게시판 글 작성(C)
    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody BoardDto.Post postDto){
        Board board = boardService.createBoard(mapper.boardPostDtoToBoard(postDto));

        URI uri = UriComponentsBuilder.newInstance()
                .path("/api/boards/"+board.getBoardId())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }
    // 게시판 수정(U)
    @PatchMapping("/{id}")
    public ResponseEntity patchBoard(@PathVariable("id") long id, @Valid @RequestBody BoardDto.Patch patchDto){
        patchDto.setBoardId(id);
        Board board = mapper.boardPatchDtoToBoard(patchDto);
        Board response = boardService.updateBoard(board);

        return new ResponseEntity<>(mapper.boardToBoardResponseDto(response), HttpStatus.OK);
    }
    // 게시판 삭제(D)
    @DeleteMapping("/{id}")
    public ResponseEntity deleteBoard(@PathVariable("id") long id) {
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{boardId}")
    public ResponseEntity findBoard(@PathVariable("boardId") Long id){
        // 게시판 상세 조회(Detailed R)
        Board board = boardService.findBoard(id);
        if(board ==null){
            return ResponseEntity.notFound().build();
        }
        BoardDto.Detail details = mapper.BoardToDetailDto(board);
        return ResponseEntity.ok(details);
    }

    // 게시판 전체 조회(R)
    @GetMapping
    public ResponseEntity getBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue ="20") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardService.findBoards(pageable);

        List<BoardDto.Response> response = boardPage
                .stream()
                .map(board->mapper.boardToBoardResponseDto(board))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(response);
    }

}
