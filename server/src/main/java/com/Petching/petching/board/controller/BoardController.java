package com.Petching.petching.board.controller;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.mapper.BoardMapper;
import com.Petching.petching.board.service.BoardService;
import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.response.PageInfo;
import com.Petching.petching.response.SingleResponse;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /* 글을 올리는 API
     * userId 는 request header 로 요청받아서 유저를 검증하는 방법으로 리팩토링 필요
     * */
    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody BoardDto.Post requestBody){


        Board board = mapper.boardPostDtoToBoard(requestBody);
        User user = userService.findUser(requestBody.getUserId());
        board.setUser(user);

        Board savedBoard = boardService.createBoard(board);
        System.out.println("board createdAt: " + board.getCreatedAt());

        URI uri = UriComponentsBuilder.newInstance()
                .path("/boards/"+savedBoard.getBoardId())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }


    /* 글을 수정하는 API
     * request header 로 요청받아서 유저를 검증하는 방법으로 리팩토링 필요
     * */
    @PatchMapping("/{boardId}")
    public ResponseEntity patchBoard(@PathVariable("boardId") long id, @RequestBody BoardDto.Patch requestBody){

        Board board = mapper.boardPatchDtoToBoard(requestBody);
        board.setBoardId(id);

        Board response = boardService.updateBoard(board);

        return new ResponseEntity<>(new SingleResponse<>(mapper.boardToBoardDetailDto(response)), HttpStatus.OK);

    }

    /* 글을 삭제하는 API
     * request header 로 요청받아서 유저를 검증하는 방법으로 리팩토링 필요
     * */

    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable("boardId") long boardId){

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

        /** board service 에 넣을 것
         * User 가 해당 게시물을 좋아하는 지 확인
         * user 엔티티에서 LikedBoardList 추가 필요
         * User user = userService.findUser(userId);
         * List<Long> userLiked = user.getLikedBoardList()
         * .stream()
         * .filter( boardId -> boardId == id)
         * .collect(Collectors.toList());
         * if(!userLiked.isEmpty())
         * board.setCheckLike(true);
         * */


        List<BoardDto.Response> response = mapper.boardPageToBoardResponseListDto(boardPage);

        // 페이지 정보 헤더에 추가
        PageInfo pageInfo = new PageInfo(
                boardPage.getNumber(),
                boardPage.getSize(),
                boardPage.getTotalElements(),
                boardPage.getTotalPages()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Info", new Gson().toJson(pageInfo));

        return new ResponseEntity<>(new MultiResponse<>(response, boardPage), HttpStatus.OK);

    }

    // 특정 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity getBoard(@PathVariable("boardId") long id, @RequestParam long userId){

        Board board = boardService.findBoardByMK(id);

        /**
         * board service 에 넣을 것
         * User 가 해당 게시물을 좋아하는 지 확인
         * user 엔티티에서 LikedBoardList 추가 필요
         * User user = userService.findUser(userId);
         * List<Long> userLiked = user.getLikedBoardList()
         * .stream()
         * .filter( boardId -> boardId == id)
         * .collect(Collectors.toList());
         * if(!userLiked.isEmpty())
         * board.setCheckLike(true);
         * */

        BoardDto.Detail response = mapper.boardToBoardDetailDto(board);

        return new ResponseEntity<>( new SingleResponse<>(response), HttpStatus.OK);

    }


    // Board 에 존재하는 img 중 최대 4개를 랜덤으로 가져오는 API
    @GetMapping("/recently-created")
    public ResponseEntity getRecentlyBoardImg(){

        List<String> randomImgList = boardService.findBoardRandomImg();
        Map<String ,List<String >> map = new HashMap<>();
        map.put("ImgUrls", randomImgList);

        return new ResponseEntity<>(new SingleResponse<>(map), HttpStatus.OK);
    }

    /* 글에 작성된 좋아요 수를 올리는 API
    *  userId 는 request header 로 요청받아서 유저를 찾는 방법으로 리팩토링 필요
    * */
    @PostMapping("/{boardId}/like")
    public ResponseEntity updateLike(@PathVariable("boardId") long boardId, @RequestParam long userId){

        Board updatedBoard = boardService.updateBoardLike(boardId);

        /**
         *  User 가 좋아하는 게시글 저장
         *  user service 에서 구현 필요.
         *  User user = userService.findUser(userId);
         *  user.addLikedBoard(updatedBoard.getBoardId());
         *  userService.updateFromBoard(user);
         *
         * */

        return new ResponseEntity( HttpStatus.OK);
    }
}
