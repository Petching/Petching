package com.Petching.petching.global.chatting;

import com.Petching.petching.user.dto.UserResponseForChat;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ChatDto {

    @Getter
    @Builder
    public static class Post {
        @NotNull
        private long receiverId; // 채팅을 받는 사람
    }
    @Getter
    @Builder
    public static class RoomResponse {
        private long roomId;
        private UserResponseForChat sender;
        private UserResponseForChat receiver;
    }
    @Getter
    @Builder
    // 채팅방 속 메세지 하나
    public static class MessageResponse {
        private long messageId;
        private UserResponseForChat sender;
        private String content;
        private LocalDateTime sendTime;
    }


}
