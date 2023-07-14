//package com.Petching.petching.room;
//
//import com.Petching.petching.audit.Auditable;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@NoArgsConstructor
//@Entity
//@Getter
//@Setter
//public class RoomTag extends Auditable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long roomTagId;
//
//    @ManyToOne
//    @JoinColumn(name = "ROOM_ID")
//    private Room room;
//
////    @ManyToOne
////    @JoinColumn(name = "TAG_ID")
////    private Tag tag;
//}