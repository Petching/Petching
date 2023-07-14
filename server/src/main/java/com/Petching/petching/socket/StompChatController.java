//package com.Petching.petching.socket;
//
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//public class StompChatController {
//
//    //특정 메세지를 전달
//    private final SimpMessageSendingOperations template;
//    private final RoomServiceTransient roomService;
//    private final RoomService roomService2;
//    private final ChatRoomService chatRoomService;
//    private final WebSocketSessionService sessionService;
//
//    /*
//     *  1. 방 입장
//     *  2. 메시지 전송
//     *  3. 방 퇴장(세션만 종료)
//     * */
//
//    //Client 가 SEND할 수 있는 경로
//    //stompWebSocketConfig 에서 설정한 applicationDestinationPrefixes 와 @MessageMapping 경로가 병합됨
//    //"/pub/chat/enter"
//    @MessageMapping("/chat/enter")
//    public void enterMember(@Payload ChatMessageDto message, SimpMessageHeaderAccessor headerAccessor) {
//        //DB, 방 인원수 +1 증가 & 유저추가
//        log.info(message.getWriter());
//        log.info(message.getRoomId());
//
//        Long longroomId = roomService2.findRoom(message.getRoomId()).getRoomId();
//        roomService.enterChatRoom(longroomId, message.getWriter());
//
//        //Session, 새로운 멤버 정보 세션서비스로 저장
//        String writer = message.getWriter();
//        String roomId = Long.toString(longroomId);
//        String sessionId = headerAccessor.getSessionId();
//        sessionService.registerSession(sessionId, writer, roomId);
//        log.info("{} info is saved in session", writer);
//
//        //서버에 싱글톤 형태로 채팅룸에 저장
//        chatRoomService.addParticipant(roomId, writer);
//        log.info("{} info is saved in server", writer);
//
//        //새로운 멤버 입장을 Sub 에게 전달
//        message.setMessage(message.getWriter() + "님이 체팅방에 입장하였습니다");
//        message.setType(ChatMessageDto.MessageType.ENTER);
//
//        //유저 목록을 어떻게 넘길 것인가 2가지 방법이 존재함
//        // 1. 만약 dto에 값을 설정해서 보낸다 -> null 값처리에 대한 문제가 발생할 수 있음
//        // 2. 메시지를 2번 보낸다. -> 클라이언트에서 2개를 다 처리해야하지만. 데어터 전송량을 최소화 할 수 있음(최종선택)
//        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
//        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), chatRoomService.getParticipants(roomId));
//        log.info(chatRoomService.getParticipants(roomId).toString());
//    }
//
//    @MessageMapping("/chat/message")
//    public void sendMessage(@Payload ChatMessageDto message) {
//
//        message.setType(ChatMessageDto.MessageType.TALK);
//        message.setTime(LocalDateTime.now());
//        //pub 으로 전달된 메세지를 다시 sub에게 전달하자
//        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
//    }
//
//    @EventListener
//    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
//        //이벤트 발생 로그 찍기
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//        log.info("headAccessor {}", headerAccessor);
//
//        //이벤트에서 세션 찾아와서 값 불러오기
//        String sessionId = event.getSessionId();
//        UserSessionInfo sessionInfo = sessionService.getSessionInfo(sessionId);
//        String nickname = sessionInfo.getNickname();
//        String roomId = sessionInfo.getRoomId();
//
//        // 세션 내용을 이용하여 DB 에서 값을 지우지는 않고, 현재 인원 수만 1 감소시킴
//        // 해당 부분을 고도화 가능함 -> 현재 세션은 종료되었지만, 서버에서 값을 좀 가지고 있을 수 있음.
//        roomService.leaveSession(roomId);
//        sessionService.removeSession(sessionId);
//        chatRoomService.removeParticipant(roomId,nickname);
//        roomService2.leaveRoom(nickname, roomId);
//
//        // 세션 변경 내용을 sub 에게 전달
//        if (nickname != null) {
//            log.info("User Disconnected : " + nickname);
//
//            ChatMessageDto chat = ChatMessageDto.builder()
//                    .writer(nickname)
//                    .message(nickname + " 님 퇴장!!")
//                    .build();
//
//            template.convertAndSend("/sub/chat/room/" + roomId, chat);
//            template.convertAndSend("/sub/chat/room/" + roomId, chatRoomService.getParticipants(roomId));
//        }
//    }
//
//    @GetMapping("/chat/userlist")
//    public List<String> userList(String roomId) {
//        return chatRoomService.getParticipants(roomId);
//    }
//
//
//}