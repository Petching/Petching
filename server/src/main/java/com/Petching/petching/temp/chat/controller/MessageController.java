package com.Petching.petching.temp.chat.controller;

import com.Petching.petching.global.api.jwt.service.ExtractService;
import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.response.PageInfo;
import com.Petching.petching.temp.chat.dto.ChatDto;
import com.Petching.petching.temp.chat.dto.MessageDto;
import com.Petching.petching.temp.chat.entity.ChatMessage;
import com.Petching.petching.temp.chat.entity.PublishMessage;
import com.Petching.petching.temp.chat.mapper.ChatMapper;
import com.Petching.petching.temp.chat.service.ChatService;
import com.Petching.petching.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final ChatService chatService;
    private final ChatMapper mapper;

    private final ChannelTopic topic;

    @Resource(name = "chatRedisTemplate")
    private final RedisTemplate redisTemplate;

    private final ExtractService extractService;

    @MessageMapping("/chats/messages/{roomId}")
    public void message(@DestinationVariable("roomId") Long roomId, MessageDto messageDto) {

        PublishMessage publishMessage =
                new PublishMessage(messageDto.getRoomId(), messageDto.getSenderId(), messageDto.getContent(), LocalDateTime.now());

        // 채팅방에 메세지 전송
        redisTemplate.convertAndSend(topic.getTopic(), publishMessage);

        chatService.saveMessage(messageDto, roomId);
    }

    // 채팅메세지 가져오기
    @GetMapping("/chats/messages/{roomId}")
    public ResponseEntity getMessages(@Positive @PathVariable("roomId") long roomId,
                                      @Positive @RequestParam(defaultValue = "1") int page,
                                      @Positive @RequestParam(defaultValue = "10") int size,
                                      @RequestHeader("Authorization") String authorization) {

        User user = extractService.getUserFromToken(authorization);

        // 해당 채팅방의 메세지를 가져와야 함
        Page<ChatMessage> messages = chatService.findMessages(roomId, page, size);
        PageInfo pageInfo = new PageInfo(page, size, (int)messages.getTotalElements(), messages.getTotalPages());

        List<ChatMessage> messageList = messages.getContent();
        List<ChatDto.MessageResponse> messageResponses = mapper.messagesToMessageResponseDtos(messageList);

        return new ResponseEntity<>(new MultiResponse<>(messageResponses, pageInfo), HttpStatus.OK);
    }
}