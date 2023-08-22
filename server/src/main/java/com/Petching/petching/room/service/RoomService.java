package com.Petching.petching.room.service;

import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.global.response.ErrorResponse;
import com.Petching.petching.room.repository.RoomRepository;
import com.Petching.petching.room.repository.UserRoomRepository;
import com.Petching.petching.room.search.SearchService;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.repository.UserRepository;
import com.Petching.petching.user.service.UserService;
import com.Petching.petching.room.dto.RoomDto;
import com.Petching.petching.room.entity.Room;
import com.Petching.petching.room.entity.UserRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final UserService userService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserRoomRepository userRoomRepository;
    private final SearchService searchService;


    public Room createRoom(Room room, RoomDto.Post requestBody) {
        long adminMemberId = requestBody.getUserId();
        User findAdminUser = userRepository.findById(adminMemberId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        User findRequestUser = userRepository.findById(room.getRequestUserId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        room.setAdminUserNickname(findAdminUser.getNickName());
        room.setRequestUserNickname(findRequestUser.getNickName());
        room.setTitle(room.getAdminUserNickname() + "과 " + room.getRequestUserNickname() + "의 대화방");

        UserRoom userRoom = new UserRoom();
        userRoom.setAdminUser(findAdminUser);
        userRoom.setRequestUser(findRequestUser);
        userRoom.setRoom(room);
        roomRepository.save(room);
        userRoomRepository.save(userRoom);
        userRepository.save(findAdminUser);
        userRepository.save(findRequestUser);

        return room;
    }


    public Room findRoom(long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));
        return room;
    }

    public Room findRoom(String roomTitle) {
        Room room = roomRepository.findByTitle(roomTitle).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));
        return room;
    }


    public Page<Room> findRooms(int page, int size, String nickName) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepository.findByTitleContaining(nickName,PageRequest.of(page, size, Sort.by("roomId").descending()));
        List<Room> roomList = roomPage.getContent();

        return new PageImpl<>(roomList, roomPage.getPageable(), roomPage.getTotalElements());
    }

    public void leaveRoom(String nickName, String roomId){
        long longRoomId = Long.parseLong(roomId);
        Room room = findVerifiedRoom(longRoomId);

        //decrease1
        room.setUserCurrentCount(room.getUserCurrentCount()-1);
//        userRoomRepository.deleteById();
    }


    // 외래키 제약조건 해결
    public void deleteRoom(long roomId) {
        Room findRoom = findVerifiedRoom(roomId);
        List<UserRoom> userRoomList = findRoom.getUserRoomList();
        for (UserRoom userRoom : userRoomList) {
            userRoomRepository.delete(userRoom);
        }
        roomRepository.delete(findRoom);
    }


    //Todo : DB 체크 메서드
    public Room findVerifiedRoom(long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        Room findRoom = room.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));
        return findRoom;
    }


    public ResponseEntity<ErrorResponse> verifyExistsCheck(String title) {
        Optional<Room> optionalRoom = roomRepository.findByTitleContaining(title);
        if (optionalRoom.isPresent()) {
            ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, "이미 사용중인 방제입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return null;
    }
}
