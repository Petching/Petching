package com.Petching.petching.temp.chat.service;

import com.Petching.petching.temp.chat.service.RoomService;
import com.Petching.petching.temp.chat.dto.MessageDto;
import com.Petching.petching.temp.chat.entity.ChatMessage;
import com.Petching.petching.temp.chat.entity.ChatRoom;
import com.Petching.petching.temp.chat.repository.MessageRepository;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatService {

    private final UserService userService;
    private final RoomService roomService;
    private final MessageRepository messageRepository;

    public ChatService(UserService userService, RoomService roomService, MessageRepository messageRepository) {
        this.userService = userService;
        this.roomService = roomService;
        this.messageRepository = messageRepository;
    }

    public void saveMessage(MessageDto dto, Long roomId) {
        User member = userService.findUser(dto.getSenderId());
        ChatRoom chatRoom = roomService.findRoom(roomId);

        ChatMessage chatMessage = ChatMessage
                .builder()
                .content(dto.getContent())
                .sender(member)
                .chatRoom(chatRoom)
                .sendTime(LocalDateTime.now())
                .build();

        messageRepository.save(chatMessage);
    }

    public Page<ChatMessage> findMessages(long roomId, int page, int size) {
        ChatRoom chatRoom = roomService.findRoom(roomId);

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("messageId").descending());
        Page<ChatMessage> messages = messageRepository.findByChatRoom(pageable, chatRoom);

        return messages;
    }
}