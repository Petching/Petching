package com.Petching.petching.room.service;

import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import com.Petching.petching.room.entity.Room;
import com.Petching.petching.room.entity.UserRoom;
import com.Petching.petching.room.repository.RoomRepository;
import com.Petching.petching.room.repository.UserRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RoomServiceTransient {

    private final RoomRepository roomRepository;
    private final UserService userService;
    private final UserRoomRepository userRoomRepository;



    public void enterChatRoom(Long roomId, String nickname) {
        /*
         * 1. 방, 멤버 엔티티 찾기
         * 2. 새로운 멤버 룸 생성
         * 3. 현재 인원 1증가
         * 4. 현재 연관관계 메서드가 없으나, 룸리스트에 새로운 멤버룸 추가
         * */

        Room foundRoom = roomRepository.findById(roomId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));
        User foundUser = userService.verifiedUser(nickname);
        log.info("Room {} is founded", foundRoom.getRoomId());
        // 2
        UserRoom userRoom = new UserRoom();
        userRoom.setRequestUser(foundUser);
        userRoom.setRoom(foundRoom);
        UserRoom madeMemberRoom = userRoomRepository.save(userRoom);
        log.info("Member {} is entering the room. . . ", madeMemberRoom.getRequestUser().getUserId());
        // 3
        foundRoom.setUserCurrentCount(foundRoom.getUserCurrentCount() + 1);
        log.info("Current Count is {}", foundRoom.getUserCurrentCount());
        // 4
        foundRoom.getUserRoomList().add(madeMemberRoom);
        log.info("New member {} is in Room {}", madeMemberRoom.getRequestUser().getUserId(), roomId);
    }

    public void leaveSession(String roomId) {
        /*
         *
         * */
        Long longRoomId = Long.parseLong(roomId);
        Room foundRoom = roomRepository.findById(longRoomId).get();
        log.info("A member leave {} room", foundRoom.getRoomId());


        foundRoom.setUserCurrentCount(foundRoom.getUserCurrentCount() -1);
        log.info("Current count of this room is  {}", foundRoom.getUserCurrentCount());
    }
}
