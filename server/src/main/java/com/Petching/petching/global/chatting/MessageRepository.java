package com.Petching.petching.global.chatting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findAllByChatRoomOrderBySendTimeDesc(ChatRoom chatRoom, Pageable pageable);
}