package com.Petching.petching.temp.chat.dto;

import com.Petching.petching.user.dto.UserChatResponseDto;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ChatDto {
    @Getter
    // 채팅을 받는 사람
    public static class Post {
        @NotNull
        private long userId;
    }
    @Getter
    @Builder
    public static class RoomResponse {
        private long roomId;
        private UserChatResponseDto sender;
        private UserChatResponseDto receiver;
    }
    @Getter
    @Builder
    // 채팅방 속 하나의 메세지
    public static class MessageResponse {
        private long messageId;
        private UserChatResponseDto sender;
        private String content;
        private LocalDateTime sendTime;
    }

}