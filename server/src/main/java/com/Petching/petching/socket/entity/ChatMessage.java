package com.Petching.petching.socket.entity;

import com.Petching.petching.socket.dto.ChatMessageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private String writer;
    private String message;
    private LocalDateTime time;
    private ChatMessageDto.MessageType type;
    public enum MessageType {
        ENTER, LEAVE, TALK
    }

}
