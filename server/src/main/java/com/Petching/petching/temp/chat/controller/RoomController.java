package com.Petching.petching.temp.chat.controller;

import com.Petching.petching.global.api.jwt.service.ExtractService;
import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.login.service.UserLoginService;
import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.response.PageInfo;
import com.Petching.petching.temp.chat.dto.ChatDto;
import com.Petching.petching.temp.chat.entity.ChatRoom;
import com.Petching.petching.temp.chat.mapper.ChatMapper;
import com.Petching.petching.temp.chat.service.RoomService;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/chats")
@RequiredArgsConstructor
public class RoomController {

    private final UserService userService;
    private final RoomService roomService;
    private final ChatMapper mapper;

    private final ExtractService extractService;



    // 채팅방 주소 가져오기
    @PostMapping
    public ResponseEntity getOrCreateRoom(@RequestBody ChatDto.Post postDto,
                                          @RequestHeader("Authorization") String authorization) {

        User user = extractService.getUserFromToken(authorization);

        User receiver = userService.findUser(mapper.ChatPostDtoToMember(postDto).getUserId());
        long roomId = roomService.createRoom(receiver.getUserId(), user.getEmail());

        URI location = UriComponentsBuilder.newInstance()
                .path("/chats/{roomId}")
                .buildAndExpand(roomId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    //  채팅방 열기
    @GetMapping("/{room-id}")
    public ResponseEntity getChatRoom(@Positive @PathVariable("roomId") long roomId,
                                      @RequestHeader("Authorization") String authorization) {

        User user = extractService.getUserFromToken(authorization);

        ChatRoom chatRoom = roomService.findRoom(roomId);
        ChatDto.RoomResponse response = mapper.chatRoomToRoomResponseDto(chatRoom);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 채팅 목록 조회 -> 로그인한 유저가 참여하고 있는 채팅 목록
    @GetMapping
    public ResponseEntity getChatRooms(@RequestHeader("Authorization") String authorization,
                                       @Positive @RequestParam(defaultValue = "1") int page,
                                       @Positive @RequestParam(defaultValue = "10") int size) {

        User user = extractService.getUserFromToken(authorization);

        Page<ChatRoom> roomPage = roomService.findRooms(user, page, size);
        PageInfo pageInfo = new PageInfo(page, size, (int)roomPage.getTotalElements(), roomPage.getTotalPages());

        List<ChatRoom> rooms = roomPage.getContent();
        List<ChatDto.RoomResponse> responses = mapper.chatRoomListToRoomResponseDtos(rooms);

        return new ResponseEntity<>(new MultiResponse<>(responses, pageInfo), HttpStatus.OK);
    }

}