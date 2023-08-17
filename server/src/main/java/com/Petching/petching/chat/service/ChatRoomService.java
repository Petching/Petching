//package com.Petching.petching.chat.service;
//
//import com.Petching.petching.chat.model.ChatRoom;
//import com.Petching.petching.chat.repository.ChatRoomRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class ChatRoomService {
//
//    private final ChatRoomRepository chatRoomRepository;
//
//    //채팅방 불러오기
//    public List<ChatRoom> findAllRoom() {
//        //채팅방 최근 생성 순으로 반환
//        return chatRoomRepository.findAll();
//    }
//
//    //채팅방 하나 불러오기
//    public ChatRoom findById(Long roomId) {
//
//        return chatRoomRepository.findById(roomId).orElse(null);
//    }
//
//    //채팅방 생성
//    public ChatRoom createRoom(String name) {
//        ChatRoom chatRoom = new ChatRoom(name);
//        chatRoomRepository.save(chatRoom);
//        return chatRoom;
//    }
//}