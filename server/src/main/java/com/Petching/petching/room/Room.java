//package com.Petching.petching.room;
//
//
//import com.Petching.petching.audit.Auditable;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//public class Room extends Auditable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long roomId;
//
//    @Column(nullable = false) //member 고유식별자랑 달라도 상관없는지. 요청시 그냥 파라미터로 넣을지.
//    private Long adminMemberId;
//
//    @Column
//    private String adminNickname;
//
//    @Column(nullable = false)
//    private String title;
//
//    @Column
//    private String info;
//
//    @Column
//    private String imageUrl;
//
//    @Column
//    private String password;
//
//    @Column(nullable = false)
//    private boolean isPrivate;
//
//    @Column(nullable = false)
//    private int memberMaxCount;
//
//    @Column
//    private int memberCurrentCount;
//
//    @Column
//    private int favoriteCount;
//
//    @OneToMany(mappedBy = "room")
//    private List<MemberRoom> memberRoomList = new ArrayList<>();
//
//
////    @OneToMany(mappedBy = "room" , cascade = CascadeType.ALL) //ALL
////    private List<RoomTag> roomTagList = new ArrayList<>();
//
////    @OneToMany(mappedBy = "room")
////    private List<RoomHistory> roomHistoryList = new ArrayList<>(); //history
//
////    public List<RoomTag> setRoomTagList(List<RoomTag> roomTagList) {
////        this.roomTagList = roomTagList;
////        return roomTagList;
////    }
//}
