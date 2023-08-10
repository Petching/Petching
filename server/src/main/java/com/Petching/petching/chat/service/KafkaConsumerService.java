//package com.Petching.petching.chat.service;
//
//import com.Petching.petching.chat.entity.ChatMessage;
//import com.Petching.petching.chat.repository.ChatMessageRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class KafkaConsumerService {
//
//    private final SimpMessageSendingOperations sendingOperations;
//    private final ChatMessageRepository repository;
//
//    @KafkaListener(topics = "testTopic", groupId = "testgroup", containerFactory = "kafkaListener")
//    public void consume(ChatMessage message) {
//        System.out.println("카프카 컨슈머 = " + message.getMessage());
//
//        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
//            message.setMessage(message.getSender()+"님이 입장하였습니다.");
//            repository.save(message);
//        }
//
//        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(), message );
//    }
//}
