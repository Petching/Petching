package com.Petching.petching.global.chatting;

import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.login.service.UserLoginService;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    private final UserService userService;
    private final RoomRepository roomRepository;
    private final UserLoginService userLoginService;

    public RoomService(UserService userService, RoomRepository roomRepository, UserLoginService userLoginService) {
        this.userService = userService;
        this.roomRepository = roomRepository;
        this.userLoginService = userLoginService;
    }

    public Long createRoom(long receiverId, UserDetails userDetails) {

        User receiver = userService.findUser(receiverId);

        User sender = userService.findUserByEmail(userDetails.getUsername());


        // 둘의 채팅이 있는 지 확인
        Optional<ChatRoom> optionalChatRoom = roomRepository.findBySenderAndReceiver(sender, receiver);
        Optional<ChatRoom> optionalChatRoom2 = roomRepository.findBySenderAndReceiver(receiver, sender);

        ChatRoom chatRoom = null;

        if(optionalChatRoom.isPresent()) {
            chatRoom = optionalChatRoom.get();
            return chatRoom.getRoomId();
        } else if (optionalChatRoom2.isPresent()) {
            chatRoom = optionalChatRoom2.get();
            return chatRoom.getRoomId();
        } else {
            chatRoom = ChatRoom.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .build();
        }

        ChatRoom saveChatRoom = roomRepository.save(chatRoom);

        return saveChatRoom.getRoomId();
    }

    // 유저의 채팅 목록 가져오기
    public Page<ChatRoom> findRooms(UserDetails userDetails, int page, int size) {

        User sender = userService.findUserByEmail(userDetails.getUsername());
        Pageable pageable = PageRequest.of(page-1 , size, Sort.by("roomId").descending());
        Page<ChatRoom> chatRooms = roomRepository.findAllBySenderOrReceiver(pageable, sender, sender);

        return chatRooms;
    }

    // 채팅방 하나 찾기
    public ChatRoom findRoom(long roomId) {
        ChatRoom chatRoom = findExistRoom(roomId);

        return chatRoom;
    }

    // 채팅방 존재 검증
    private ChatRoom findExistRoom(long roomId) {
        Optional<ChatRoom> optionalChatRoom = roomRepository.findById(roomId);

        ChatRoom findChatRoom = optionalChatRoom.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND)
        );

        return findChatRoom;
    }

}
