package com.Petching.petching.socket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
/*
 * 싱글톤 형태로 서버 세션에 방에 따른 유저 저장하기
 *
 * */
public class ChatRoomService {

    private ConcurrentHashMap<String, List<String>> chatRooms;

    public ChatRoomService() {
        this.chatRooms = new ConcurrentHashMap<>();
    }

    public List<String> getParticipants(String roomId) {
        return chatRooms.getOrDefault(roomId, new ArrayList<>());
    }

    public void addParticipant(String roomId, String participant) {
        chatRooms.computeIfAbsent(roomId, f -> new ArrayList<>()).add(participant);
    }

    public void removeParticipant(String roomId, String participant) {
        List<String> participants = chatRooms.get(roomId);
        if (participants != null) {
            participants.remove(participant);
        }
    }
}
