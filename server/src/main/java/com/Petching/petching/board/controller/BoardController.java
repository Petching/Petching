package com.Petching.petching.board.controller;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.mapper.BoardMapper;
import com.Petching.petching.board.service.BoardService;
import com.Petching.petching.response.PageInfo;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final BoardMapper mapper;
    private final UserService userService;

    public BoardController(BoardService boardService, BoardMapper mapper, UserService userService) {
        this.boardService = boardService;
        this.mapper = mapper;
        this.userService = userService;
    }
/**
 * 아래는 글쓰는 요청 시 post Dto 와 File 을 한 번에 받을 수 있는 메서드
 * 아직 미정
 * */

/*    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity postBoard(@RequestPart(required = false) MultipartFile img,
                                    @Valid @RequestPart BoardDto.Post requestBody){

        Board board = boardService.createBoard(mapper.boardPostDtoToBoard(requestBody));

        if(img!=null){
            String imgUrl = s3Service.uploadImageToS3(img);
            board.setImgUrl(imgUrl);
            boardService.updateBoard(board);
        }

        URI uri = UriComponentsBuilder.newInstance()
                .path("/boards/"+board.getBoardId())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }*/
    @PostMapping
    public ResponseEntity postBoard(@RequestBody BoardDto.Post requestBody){

        User user = userService.findUser(requestBody.getUserId());

        Board board = mapper.boardPostDtoToBoard(requestBody);
        board.setUser(user);

        Board savedBoard = boardService.createBoard(board);


        URI uri = UriComponentsBuilder.newInstance()
                .path("/boards/"+savedBoard.getBoardId())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(@PathVariable("board-id") long id, @RequestBody BoardDto.Patch requestBody){

        Board board = mapper.boardPatchDtoToBoard(requestBody);
        board.setBoardId(id);

        Board response = boardService.updateBoard(board);

        return new ResponseEntity<>(mapper.boardToBoardDetailDto(response), HttpStatus.OK);

    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(@PathVariable("board-id") long boardId){

        boardService.deleteBoard(boardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    // 전체 게시글 목록 조회
    @GetMapping
    public ResponseEntity getBoards(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Board> boardPage = boardService.findBoards(pageable);

        List<BoardDto.Response> response = boardPage
                .stream()
                .map(board->mapper.boardToBoardResponseDto(board))
                .collect(Collectors.toList());

        // 페이지 정보 헤더에 추가
        PageInfo pageInfo = new PageInfo(
                boardPage.getNumber(),
                boardPage.getSize(),
                boardPage.getTotalElements(),
                boardPage.getTotalPages()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Info", new Gson().toJson(pageInfo));

        return ResponseEntity.ok().headers(headers).body(response);

    }


    // 특정 게시글 조회
    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("board-id") long id){

        Board board = boardService.findBoardByMK(id);

        BoardDto.Detail response = mapper.boardToBoardDetailDto(board);

        return ResponseEntity.ok(response);

    }

    // Board 에 존재하는 img 중 최대 4개를 랜덤으로 가져오는 API
    @GetMapping("/recently-created")
    public ResponseEntity<List<String >> getRecentlyBoardImg(){

        List<String> randomImgList = boardService.findBoardRandomImg();

        return new ResponseEntity<>(randomImgList, HttpStatus.OK);

    }

}
