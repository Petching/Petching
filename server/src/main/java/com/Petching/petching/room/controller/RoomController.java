package com.Petching.petching.room.controller;

import com.Petching.petching.global.response.ErrorResponse;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.room.dto.RoomDto;
import com.Petching.petching.room.entity.Room;
import com.Petching.petching.room.mapper.RoomMapper;
import com.Petching.petching.room.service.RoomService;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final UserService userService;
    private final RoomMapper mapper;
    private final JwtToken jwtToken;

    @PostMapping
    public ResponseEntity postRoom(@Valid @RequestBody RoomDto.Post requestBody,
                                   @RequestHeader("Authorization") String authorization) {

        authorization = authorization.replaceAll("Bearer ","");
        User user = userService.findUser(jwtToken.extractUserIdFromToken(authorization));

        long jwtUserId = user.getUserId();

        Room room = mapper.postDtoToRoom(requestBody);
        room.setRequestUserId(jwtUserId);
        room = roomService.createRoom(room, requestBody);

        return new ResponseEntity<>(mapper.roomToPostResponseDto(room), HttpStatus.CREATED);
    }


    //방제 중복체크
    @PostMapping("/check")
    public ResponseEntity checkTitle(@Valid @RequestBody RoomDto.CheckTitle requestBody) {
        ResponseEntity checkTitle = roomService.verifyExistsCheck(requestBody.getTitle());

        if(checkTitle != null) return checkTitle;
        return new ResponseEntity(requestBody.getTitle(), HttpStatus.OK);
    }



    @GetMapping
    public ResponseEntity getNewRooms(@RequestParam(value = "page", defaultValue = "1") @Positive int page,
                                      @RequestParam(value = "size", defaultValue = "10") @Positive int size,
                                      @RequestHeader("Authorization") String authorization) {

        authorization = authorization.replaceAll("Bearer ","");
        User user = userService.findUser(jwtToken.extractUserIdFromToken(authorization));

        long jwtUserId = user.getUserId();
        String nickName = userService.verifiedUser(jwtUserId).getNickName();

        Page<Room> roomPage = roomService.findRooms(page-1, size,nickName);
        List<Room> roomList = roomPage.getContent();
        List<RoomDto.GetNewRoomResponseDtos> responseDtosList = mapper.roomToNewRoomResponseDtos(roomList);
        return new ResponseEntity<>(
                new MultiResponse<>(responseDtosList, roomPage), HttpStatus.OK);
    }




    @DeleteMapping("/{room-id}")
    public ResponseEntity deleteRoom(@PathVariable("room-id") @Positive long roomId,
                                     @RequestParam("member") @Positive long memberId,
                                     Authentication authentication){

        Map<String, Object> principal = (Map) authentication.getPrincipal();
        long jwtMemberId = ((Number) principal.get("memberId")).longValue();
        boolean isAdmin = (boolean) principal.get("isAdmin");
        Room room = roomService.findRoom(roomId);
        long adminId = room.getAdminUserId();

        if (isAdmin == false) {
            if (jwtMemberId != memberId || jwtMemberId != adminId || memberId != adminId) {
                ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.FORBIDDEN, "권한이 없는 사용자 입니다.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
            }
        }
        roomService.deleteRoom(roomId); //완전삭제

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
