//package com.Petching.petching.chat.controller;
//
//import com.Petching.petching.chat.model.ChatMessage;
//import com.Petching.petching.chat.service.KafkaProducerService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class MessageController {
//
//    private final KafkaProducerService producerService;
//
//    @MessageMapping("/chat/message")
//    public void enter(ChatMessage message) {
//        producerService.sendMessage(message);
//    }
//}
