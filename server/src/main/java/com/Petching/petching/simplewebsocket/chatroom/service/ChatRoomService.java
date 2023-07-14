//package com.Petching.petching.simplewebsocket.chatroom.service;
//
//import com.Petching.petching.simplewebsocket.chatroom.model.ChatRoom;
////import com.Petching.petching.simplewebsocket.chatroom.repository.ChatRoomJpaRepository;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ChatRoomService {
////    private final ChatRoomJpaRepository repository;
//    @Getter
//    private final ChatRoom chatRoom;
//
////    public ChatRoom createChatRoom(String name) {
////        ChatRoom created = new ChatRoom();
////        created.id = UUID.randomUUID().toString();
////        created.name = name;
////        return created;
////    }
//
//
////    public ChatRoom getChatRoom(Long id) {
////        return repository.findById(id).orElse(null);
////    }
//
//    public void remove(WebSocketSession session) {
//        repository.findAll().forEach(chatRoom -> chatRoom.remove(session));
//    }
//}