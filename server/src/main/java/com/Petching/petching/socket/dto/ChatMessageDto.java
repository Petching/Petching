package com.Petching.petching.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private String roomId;
    private String writer;
    private String message;
    private LocalDateTime time;
    private MessageType type;
    public enum MessageType {

        ENTER, LEAVE, TALK
    }
}

