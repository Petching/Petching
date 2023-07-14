//package com.Petching.petching.chat;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//
//import javax.annotation.PostConstruct;
//import java.util.*;
//
//@Repository
//@Slf4j
////추후 DB와 연결 시 Service 와 Repository 로 분리 예정
//public class ChatRepository {
//
//    private Map<String, ChatRoom> chatRoomMap;
//
//    @PostConstruct
//    public void init(){
//        chatRoomMap = new HashMap<>();
//    }
//
//    // 전체 채팅방 조회
//    public List<ChatRoom> findAllRoom(){
//        //채팅방 생성 순서를 최근순으로 반환
//        List chatRooms = new ArrayList<>(chatRoomMap.values());
//        Collections.reverse(chatRooms);
//
//        return chatRooms;
//    }
//
//    // roomId 기준으로 채팅방 찾기
//    public ChatRoom findByRoomId(String roomId){
//
//        return chatRoomMap.get(roomId);
//    }
//
//    // roomName 으로 채팅방 만들기
//    public ChatRoom createChatRoom(String roomName){
//        //채팅방 이름으로 채팅 방 생성후
//        ChatRoom chatRoom = new ChatRoom().create(roomName);
//        //map에 채팅방 아이디와 만들어진 채팅룸을 저장
//        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
//
//        return chatRoom;
//    }
//
//    // 채팅방 인원 +1
//    public void increaseUser(String roomId){
//
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//        chatRoom.setUserCount(chatRoom.getUserCount()+1);
//    }
//
//    // 채팅방 인원 -1
//    public void decreaseUser(String roomId){
//
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//        chatRoom.setUserCount(chatRoom.getUserCount()-1);
//    }
//
//    //채팅방 유저 리스트에 유저추가
//    public  String addUser(String roomId, String userName){
//
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//        String userUUID = UUID.randomUUID().toString();
//        //아이디 중복 확인 후 userList에 추가
//        chatRoom.getUserList().put(userUUID,userName);
//
//        return userUUID;
//    }
//
//    // 채팅방 유저 이름 중복 확인
//    public String isDuplicateName(String roomId,String username){
//
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//        String temp = username;
//
//        // 만약 username이 중복이라면 랜덤한 숫자를 붙여준다.
//        // 이 때 랜덤한 숫자를 붙였을때 getUserList 안에 있는 닉네임이라면 다시 랜덤한 숫자 붙이기
//        while(chatRoom.getUserList().containsValue(temp)){
//            int ranNum = (int) (Math.random() * 100) + 1;
//            temp = username+ranNum;
//        }
//
//        return temp;
//    }
//
//    // 채팅방 유저 리스트 삭제
//    public void deleteUser(String roomId,String userUUID){
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//        chatRoom.getUserList().remove(userUUID);
//    }
//
//    // 채팅방 userName 조회
//    public String getUserName(String roomId,String userUUID){
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//
//        return chatRoom.getUserList().get(userUUID);
//    }
//
//    //채팅방 전체 userList 조회
//    public List<String> getUserList(String roomId){
//        List<String> list = new ArrayList<>();
//
//        ChatRoom chatRoom = chatRoomMap.get(roomId);
//
//        chatRoom.getUserList().forEach((key,value) -> list.add(value));
//
//        return list;
//    }
//
//}