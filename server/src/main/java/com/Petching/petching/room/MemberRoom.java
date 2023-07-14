//package com.Petching.petching.room;
//
//
//import com.Petching.petching.audit.Auditable;
//import com.Petching.petching.user.entity.User;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
////@Table(name = "member_room")
//public class MemberRoom extends Auditable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long memberRoomId;
//
//    @Enumerated(value = EnumType.STRING)
//    private Favorite favorite = Favorite.NONE;
//
//    @Enumerated(value = EnumType.STRING)
//    private Authority authority;
//
//    @ManyToOne
//    @JoinColumn(name = "ROOM_ID")
//    private Room room;
//
//    @ManyToOne  //(fetch = FetchType.LAZY)
//    @JoinColumn(name = "USER_ID")
//    private User user;
//
//
//    @Getter
//    public enum Authority {
//        ADMIN, USER
//    }
//
//
//    @Getter
//    public enum Favorite {
//        NONE , LIKE
//    }
//
////    public enum History { VISITED }
//}