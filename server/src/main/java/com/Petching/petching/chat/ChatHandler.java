//package com.Petching.petching.chat;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class ChatHandler extends TextWebSocketHandler {
//
//    private  final ObjectMapper mapper;
//    private final ChatService service;
//
//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        String payload = (String) message.getPayload();
//        log.info("payload : {}",payload);
//
////        TextMessage intialGretting = new TextMessage("Welcome to Chat Server");
//        //JSON -> Java Object
//        ChatDto chatMessage = mapper.readValue(payload, ChatDto.class);
//        log.info("session : {}",chatMessage.toString());
//
//        ChatRoom room = service.findRoomById(chatMessage.getRoomId());
//        log.info("room : {}",room.toString());
//
//        room.handleAction(session,chatMessage, service);
//
//    }
//    /** Client가 접속 시 호출되는 메서드*/
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        log.info(session + " 클라이언트 접속");
//    }
//    /** client가 접속 시 호출되는 메서드*/
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        log.info(session + " 클라이언트 접속 해제");
//    }
//}