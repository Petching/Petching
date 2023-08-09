//package com.Petching.petching.chat.service;
//
//import com.Petching.petching.chat.entity.ChatMessage;
//import com.Petching.petching.chat.repository.ChatMessageRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class KafkaProducerService {
//    private static final String TOPIC = "testTopic";
//    private final KafkaTemplate<String, ChatMessage> kafkaTemplate;
//    private final ChatMessageRepository repository;
//
//    public void sendMessage(ChatMessage chatmessage) {
//        System.out.println("kafka producer : " + chatmessage.getMessage());
//        repository.save(chatmessage);
//
//        kafkaTemplate.send(TOPIC, chatmessage);
//    }
//}
