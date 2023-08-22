package com.Petching.petching.room.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomHistoryRepository extends JpaRepository<RoomHistory, Long> {
    RoomHistory findByUser_UserIdAndRoom_Title(long userId, String roomTitle);
    List<RoomHistory> findAll();
}
