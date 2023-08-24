package com.Petching.petching.temp.chat.repository;

import com.Petching.petching.temp.chat.entity.ChatRoom;
import com.Petching.petching.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySenderAndReceiver(User sender, User receiver);
    Page<ChatRoom> findAllBySenderOrReceiver(Pageable pageable, User sender, User receiver);
}
