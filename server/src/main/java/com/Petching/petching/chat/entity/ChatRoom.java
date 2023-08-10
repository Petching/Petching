//package com.Petching.petching.chat.entity;
//
//import com.Petching.petching.user.entity.User;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
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
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "USER_ID")
//    private User user;
//
//    public ChatRoom(String roomName) {
//        this.roomName = roomName;
//    }
//}
