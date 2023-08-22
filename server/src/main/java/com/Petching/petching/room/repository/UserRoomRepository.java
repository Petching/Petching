package com.Petching.petching.room.repository;

import com.Petching.petching.room.entity.Room;
import com.Petching.petching.room.entity.UserRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Long> {
    UserRoom findByRoom(Room findRoom);
}
