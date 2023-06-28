package com.Petching.petching.board.controller;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.mapper.BoardMapper;
import com.Petching.petching.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
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

    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody BoardDto.Post requestBody){

        Board board = boardService.createBoard(mapper.boardPostDtoToBoard(requestBody));

        URI uri = UriComponentsBuilder.newInstance()
                .path("/api/boards/"+board.getBoardId())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }
    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(@PathVariable("board-id") long id, @Valid @RequestBody BoardDto.Patch requestBody){
        requestBody.setBoardId(id);
        Board board = mapper.boardPatchDtoToBoard(requestBody);
        Board response = boardService.updateBoard(board);

        return new ResponseEntity<>(mapper.boardToBoardResponseDto(response), HttpStatus.OK);
    }
    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(@PathVariable("board-id") long id){
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 전체 게시글 목록 조회
    @GetMapping
    public ResponseEntity getBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardService.findBoards(pageable);

        List<BoardDto.Response> response = boardPage
                .stream()
                .map(board->mapper.boardToBoardResponseDto(board))
                .collect(Collectors.toList());
        // TODO:현재 전체 페이지 수 헤더에 추가?
        return ResponseEntity.ok().body(response);
    }
    // 특정 게시글 조회
    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("board-id") long id){
        Board board = boardService.findBoard(id);
        if(board == null){
            return ResponseEntity.notFound().build();
        }
        BoardDto.Response response = mapper.boardToBoardResponseDto(board);
        return ResponseEntity.ok(response);
    }
}
