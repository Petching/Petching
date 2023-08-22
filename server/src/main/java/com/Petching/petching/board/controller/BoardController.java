package com.Petching.petching.board.controller;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.mapper.BoardMapper;
import com.Petching.petching.board.service.BoardService;
import com.Petching.petching.comment.mapper.CommentMapper;
import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.login.service.UserLoginService;
import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.response.SingleResponse;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;


@RestController
@Slf4j
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final BoardMapper mapper;
    private final UserService userService;

    private final CommentMapper commentMapper;

    private final JwtToken jwtToken;

    public BoardController(BoardService boardService, BoardMapper mapper, UserService userService, CommentMapper commentMapper, JwtToken jwtToken) {
        this.boardService = boardService;
        this.mapper = mapper;
        this.userService = userService;
        this.commentMapper = commentMapper;
        this.jwtToken = jwtToken;
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

    @GetMapping("/test")
    public void test(@AuthenticationPrincipal UserLoginService.UserDetail userDetail){
        System.out.println(userDetail.getUsername());
        System.out.println(userDetail.getAuthorities());
    }

    /* 글을 올리는 API
     * userId 는 request header 로 요청받아서 유저를 검증하는 방법으로 리팩토링 필요
     * */
    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody BoardDto.Post requestBody,
                                    @RequestHeader("Authorization") String authorization){

        authorization = authorization.replaceAll("Bearer ","");
        User user = userService.findUser(jwtToken.extractUserIdFromToken(authorization));

        Board board = mapper.boardPostDtoToBoard(requestBody);
        board.setUser(user);

        Board savedBoard = boardService.createBoard(board);

        URI uri = UriComponentsBuilder.newInstance()
                .path("/boards/"+savedBoard.getBoardId())
                .build().toUri();

        return ResponseEntity.created(uri).build();

    }


    /* 글을 수정하는 API
     * request header 로 요청받아서 유저를 검증하는 방법으로 리팩토링 필요
     * */
    @PatchMapping("/{boardId}")
    public ResponseEntity patchBoard(@PathVariable("boardId") long boardId,
                                     @RequestBody BoardDto.Patch requestBody,
                                     @RequestHeader("Authorization") String authorization){

        authorization = authorization.replaceAll("Bearer ","");
        User requestUser = userService.findUser(jwtToken.extractUserIdFromToken(authorization));

        Board originalBoard = boardService.findBoardByMK(boardId);
        User writer = originalBoard.getUser();

        if(Objects.equals(writer.getUserId(), requestUser.getUserId())){
            Board board = mapper.boardPatchDtoToBoard(requestBody);
            board.setBoardId(boardId);
            Board response = boardService.updateBoard(board);
            return new ResponseEntity<>(new SingleResponse<>(mapper.boardToBoardDetailDto(response)), HttpStatus.OK);
        }

        throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
    }

    /* 글을 삭제하는 API
     * request header 로 요청받아서 유저를 검증하는 방법으로 리팩토링 필요
     * */

    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable("boardId") long boardId,
                                      @RequestHeader("Authorization") String authorization){

        authorization = authorization.replaceAll("Bearer ","");
        User requestUser = userService.findUser(jwtToken.extractUserIdFromToken(authorization));

        Board originalBoard = boardService.findBoardByMK(boardId);
        User writer = originalBoard.getUser();

        if(Objects.equals(writer.getUserId(), requestUser.getUserId())){
            boardService.deleteBoard(boardId);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
    }

    // 전체 게시글 목록 조회
    @GetMapping
    public ResponseEntity getBoards(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @Nullable @RequestHeader("Authorization") String authorization
    ){

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Board> boardPage = boardService.findBoards(pageable);

        List<BoardDto.Response> response = mapper.boardPageToBoardResponseListDto(boardPage);

        if(!StringUtils.isEmpty(authorization)){
            authorization = authorization.replaceAll("Bearer ","");
            User requestUser = userService.findUser(jwtToken.extractUserIdFromToken(authorization));

            List<Long> userLiked = requestUser.getLikedBoardList();

            boardService.extracted(response, userLiked);
        }

        return new ResponseEntity<>(new MultiResponse<>(response, boardPage), HttpStatus.OK);

    }

    // 특정 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity getBoard(@PathVariable("boardId") long id,
                                   @Nullable @RequestHeader("Authorization") String authorization){

        Board board = boardService.findBoardByMK(id);
        BoardDto.Detail response = mapper.boardToBoardDetailDto(board);
        response.setComments(commentMapper.commentListToCommentResponseListDto(board.getComments()));

        if(!StringUtils.isEmpty(authorization)){
            authorization = authorization.replaceAll("Bearer ","");
            User requestUser = userService.findUser(jwtToken.extractUserIdFromToken(authorization));

            if(requestUser.getLikedBoardList().contains(board.getBoardId()))
                response.setCheckLike(true);
        }

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
    public ResponseEntity updateLike(@PathVariable("boardId") long boardId,
                                     @RequestHeader("Authorization") String authorization){

        authorization = authorization.replaceAll("Bearer ","");
        User requestUser = userService.findUser(jwtToken.extractUserIdFromToken(authorization));
        requestUser.addLikedBoard(boardId);
        userService.updatedByBoardLike(requestUser);

        boardService.updateBoardLike(boardId, requestUser);


        return new ResponseEntity( HttpStatus.OK);
    }

    @GetMapping("/{user-id}/like")
    public ResponseEntity getUserLikeBoards(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @PathVariable("user-id") long userId) {

        Pageable pageable = PageRequest.of(page -1, size);
        Page<Board> boardPage = boardService.findBoards(pageable);
        List<BoardDto.Response> result = mapper.listToListResponseDto(userService.findUserLikeBoards(userId));

        return new ResponseEntity<>(new MultiResponse<>(result, boardPage), HttpStatus.OK);
    }


}
