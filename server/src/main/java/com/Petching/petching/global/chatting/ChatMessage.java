package com.Petching.petching.global.chatting;

import com.Petching.petching.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime sendTime;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;


    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    public void setMember(User sender) {
        this.sender = sender;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }


    @Builder
    public ChatMessage(Long messageId, String content, LocalDateTime sendTime, User sender, ChatRoom chatRoom) {
        this.messageId = messageId;
        this.content = content;
        this.sendTime = sendTime;
        this.sender = sender;
        this.chatRoom = chatRoom;
    }
}
