package com.Petching.petching.temp.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹소켓이 handshake를 하기 위해 연결하는 endpoint. ex: ws://localhost:8080/ws/chat
        registry.addEndpoint("/ws/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    //메세지 브로커에 관한 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 서버 -> 클라이언트로 발행하는 메세지에 대한 endpoint 설정 : 구독
        registry.enableSimpleBroker("/sub");
        // 클라이언트->서버로 발행하는 메세지에 대한 endpoint 설정 : 구독에 대한 메세지
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
