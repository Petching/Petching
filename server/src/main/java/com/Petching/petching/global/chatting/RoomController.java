package com.Petching.petching.global.chatting;

import com.Petching.petching.comment.entity.Comment;
import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.login.service.UserLoginService;
import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.response.PageInfo;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/chats")
public class RoomController {

    private final UserService userService;
    private final RoomService roomService;
    private final ChatMapper mapper;

    private final UserLoginService userLoginService;

    public RoomController(UserService userService, RoomService roomService, ChatMapper mapper, UserLoginService userLoginService) {
        this.userService = userService;
        this.roomService = roomService;
        this.mapper = mapper;
        this.userLoginService = userLoginService;
    }

    // 채팅방 주소 가져오기
    @PostMapping
    public ResponseEntity getOrCreateRoom(@Valid @RequestBody ChatDto.Post postDto,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        if(userDetails == null) {
            throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
        }

        // 탈퇴한 회원 확인
        User receiver = userService.findUser(mapper.ChatPostDtoToUser(postDto).getUserId());

        long roomId = roomService.createRoom(receiver.getUserId(), userDetails);

        URI location = UriComponentsBuilder.newInstance()
                .path("/chats/{roomId}")
                .buildAndExpand(roomId)
                .toUri();

        return ResponseEntity.created(location).build();

    }

    // 채팅방 열기
    @GetMapping("/{roomId}")
    public ResponseEntity getChatRoom(@Positive @PathVariable("roomId") long roomId,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails == null) {
            throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
        }

        ChatRoom chatRoom = roomService.findRoom(roomId);
        ChatDto.RoomResponse response = mapper.chatRoomToRoomResponseDto(chatRoom);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 채팅 목록 조회 -> 로그인한 유저가 참여하고 있는 채팅 목록
    @GetMapping
    public ResponseEntity getChatRooms(@AuthenticationPrincipal UserDetails userDetails,
                                       @Positive @RequestParam(defaultValue = "1") int page,
                                       @Positive @RequestParam(defaultValue = "10") int size) {

        if(userDetails == null) {
            throw new BusinessLogicException(ExceptionCode.BAD_REQUEST);
        }

        Page<ChatRoom> roomPage = roomService.findRooms(userDetails, page, size);

        List<ChatRoom> rooms = roomPage.getContent();
        List<ChatDto.RoomResponse> responses = mapper.chatRoomListToRoomResponseDtos(rooms);

        return new ResponseEntity<>(new MultiResponse<>(responses, roomPage), HttpStatus.OK);
    }
}