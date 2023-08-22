//package com.Petching.petching.chat.entity;
//
//import com.Petching.petching.user.entity.User;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//public class ChatRoom {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long roomId;
//
//    @Column
//    private String roomName;
//
//    @Column
//    private String userNickName;
//
//    @Column
//    private String keeperNickName;
//
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "USER_ID")
//    private User user;
//
//    public ChatRoom(String roomName) {
//        this.roomName = roomName;
//    }
//}
