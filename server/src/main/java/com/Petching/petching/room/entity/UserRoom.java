package com.Petching.petching.room.entity;

import com.Petching.petching.audit.BaseEntity;
import com.Petching.petching.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@Table(name = "member_room")
public class UserRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoomId;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @ManyToOne  //(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_USER_ID")
    private User adminUser;

    @ManyToOne  //(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUEST_USER_ID")
    private User requestUser;

}
