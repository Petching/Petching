//package com.Petching.petching.socket;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//@Slf4j
//public class WebSocketSessionService {
//
//    private final Map<String, UserSessionInfo> sessionMap = new ConcurrentHashMap<>();
//
//    public void registerSession(String sessionId, String nickname, String roomId) {
//        sessionMap.put(sessionId, new UserSessionInfo(nickname, roomId));
//        log.info("Registering SessionInfo...");
//        log.info("SessionId : " + sessionId);
//        log.info("nickname : " + nickname);
//        log.info("roomId : " + roomId);
//    }
//
//    public UserSessionInfo getSessionInfo(String sessionId) {
//        log.info("Deleting SessionInfo...");
//        log.info("SessionId : " + sessionId);
//        return sessionMap.get(sessionId);
//    }
//
//    public void removeSession(String sessionId) {
//        sessionMap.remove(sessionId);
//    }
//
//    public static class UserSessionInfo {
//        private final String nickname;
//        private final String roomId;
//
//        public UserSessionInfo(String nickname, String roomId) {
//            this.nickname = nickname;
//            this.roomId = roomId;
//        }
//
//        public String getNickname() {
//            return nickname;
//        }
//
//        public String getRoomId() {
//            return roomId;
//        }
//
//    }
//}