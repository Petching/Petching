package com.Petching.petching.room.entity;

import com.Petching.petching.audit.BaseEntity;
import com.Petching.petching.room.history.RoomHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Column(nullable = false)
    private Long adminUserId;

    @Column
    private String adminUserNickname;

    @Column(nullable = false)
    private long requestUserId;

    @Column
    private String requestUserNickname;

    @Column(nullable = false)
    private String title;

    @Column
    private int userCurrentCount;

    @OneToMany(mappedBy = "room")
    private List<UserRoom> userRoomList = new ArrayList<>();

    @OneToMany(mappedBy = "room")
    private List<RoomHistory> roomHistoryList = new ArrayList<>(); //history

}

