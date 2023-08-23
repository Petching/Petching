package com.Petching.petching.room.search;

import com.Petching.petching.room.entity.Room;
import com.Petching.petching.room.history.RoomHistory;
import lombok.Getter;

import java.util.Comparator;

@Getter
public class SortMethod {


    public static Comparator<Room> sortByOld(){
        return Comparator.comparing(Room::getCreatedAt);
    }

    public static Comparator<Room> sortByNew(){
        return Comparator.comparing(Room::getCreatedAt).reversed();
    }

    public static Comparator<RoomHistory> sortByNewHistory(){
        return Comparator.comparing(RoomHistory::getVisitTime).reversed();
    }

}
