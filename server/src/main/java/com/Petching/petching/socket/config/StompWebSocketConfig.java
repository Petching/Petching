package com.Petching.petching.socket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // 1. 모든 클라이언트에서 WebSocket을 지원한다는 보장이 없다.
        // 2. 일부 Proxy가 HTTP요청의 Upgrade 헤더를 처리하지 못할 수 있음
        // 3. 유휴 상태에서 소켓 연결이 끊어질 수 있다
        // -> SockJS() 사용

        // GET /stomp/info 요청이 들어오고
        // Json으로 웹소켓 요청에 관한 응답이 전달됨
        registry.addEndpoint("/stomp")
                .setAllowedOrigins("http://localhost:3000", "http://localhost:8080","https://petching.net","https://server.petching.net")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // STOMP 메세지의 "destination" 헤더는 @Controller 객체의 @MessageMapping 메서드로 라우팅
        config.setApplicationDestinationPrefixes("/pub");

        config.enableSimpleBroker("/sub");
    }

}

