package com.Petching.petching.room.repository;

import com.Petching.petching.room.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByTitle(String title);
    Optional<Room> findByTitleContaining(String query);
    List<Room> findAllByTitleContaining(String query, Pageable pageable);
    Page<Room> findByTitleContaining(String query, Pageable pageable);


}
