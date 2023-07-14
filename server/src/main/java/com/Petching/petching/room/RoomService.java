//package com.Petching.petching.room;
//
//
//import com.Petching.petching.exception.BusinessLogicException;
//import com.Petching.petching.exception.ExceptionCode;
//import com.Petching.petching.user.entity.User;
//import com.Petching.petching.user.repository.UserRepository;
//import com.Petching.petching.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.*;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class RoomService {
//
//    private final UserService memberService;
////    private final TagRepository tagRepository;
//    private final RoomRepository roomRepository;
//    private final UserRepository userRepository;
//    private final MemberRoomRepository memberRoomRepository;
////    private final UserTagRepository memberTagRepository;
//
//    @Value("${default.thumbnail.image}")
//    private String thumbnail;
//
//
//    public Room createRoom(Room room, RoomDto.Post requestBody) {
////        long adminMemberId = requestBody.getAdminMemberId();
//        User findUser = userRepository.findById(adminMemberId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
//
//        room.setAdminNickname(findMember.getNickName());
//        findMember.setCreatedCount(findMember.getCreatedCount() + 1);
//
////        Optional.ofNullable(requestBody.getImageUrl())
////                        .ifPresent(room::setImageUrl);
////
////        if(requestBody == null) room.setImageUrl(thumbnail);
//
//        log.info("# 이미지 {}", room.getImageUrl());
//
////        List<RoomTag> roomTagList = room.getRoomTagList().stream()
////                .map(rt -> {
////                    String roomTagName = rt.getTag().getName();
////                    Optional<Tag> optionalTag = tagRepository.findByName(roomTagName);
////
////                    if (optionalTag.isPresent()) rt.setTag(optionalTag.get());
////                    else throw new BusinessLogicException(ExceptionCode.TAG_NOT_FOUND);
////                    return rt;
////                }).collect(Collectors.toList());
////
////        room.setRoomTagList(roomTagList);
//
//        MemberRoom memberRoom = new MemberRoom();
//        memberRoom.setUser(findMember);
//        memberRoom.setRoom(room);
//        memberRoom.setFavorite(MemberRoom.Favorite.NONE);
//        memberRoom.setAuthority(MemberRoom.Authority.ADMIN);
//        roomRepository.save(room);
//        memberRoomRepository.save(memberRoom);
//        memberRepository.save(findMember);
//
//        return room;
//    }
//
//
//    public Room updateRoom(Room room, long adminMemberId) {
//        Room findRoom = findVerifiedRoom(room.getRoomId());
//        Member findMember = memberService.findMember(adminMemberId);
//
//        if (!findRoom.getAdminMemberId().equals(findMember.getMemberId()))
//            throw new BusinessLogicException(ExceptionCode.ONLY_ADMIN);
//
//        //이미지 수정 제거
//        Optional.ofNullable(room.getTitle())
//                .ifPresent(findRoom::setTitle);
//        Optional.ofNullable(room.getInfo())
//                .ifPresent(findRoom::setInfo);
//        Optional.ofNullable(room.getMemberMaxCount())
//                .ifPresent(findRoom::setMemberMaxCount);
//        Optional.ofNullable(room.isPrivate())
//                .ifPresent(findRoom::setPrivate);
//
//
//        if (!room.isPrivate() && room.getPassword() != null && !room.getPassword().isEmpty()) {
//            throw new BusinessLogicException(ExceptionCode.NO_PASSWORD_REQUIRED);
//        }
//
//        if (room.isPrivate()) {
//            Optional.ofNullable(room.getPassword())
//                    .ifPresent(password -> {
//                        if (password != null && !password.isEmpty()) {
//                            findRoom.setPassword(password);
//                        } else {
//                            throw new BusinessLogicException(ExceptionCode.NEED_PASSWORD);
//                        }
//                    });
//
//        } else {
//            findRoom.setPassword(null);
//        }
//
//        if (room.getRoomTagList() != null) {
//            findRoom.getRoomTagList().clear();
//
//            for (RoomTag tag : room.getRoomTagList()) {
//                tag.setRoom(findRoom);
//            }
//
//            findRoom.setRoomTagList(room.getRoomTagList());
//        }
//
//        roomRepository.save(findRoom);
//        return findRoom;
//    }
//
//
//    public Room switchAdmin(Room room, long newAdminId) {
//        Member newAdminMember = memberRepository.findById(newAdminId).get();
//
//        Room findRoom = findVerifiedRoom(room.getRoomId());
//        findRoom.setAdminMemberId(newAdminMember.getMemberId());
//        findRoom.setAdminNickname(newAdminMember.getNickname());
//        findRoom.setImageUrl(newAdminMember.getImageUrl());
//        roomRepository.save(findRoom);
//
//        MemberRoom memberRoom = memberRoomRepository.findByRoom(findRoom);
//        memberRoom.setMember(newAdminMember);
//        memberRoom.setRoom(findRoom);
//        memberRoom.setAuthority(MemberRoom.Authority.ADMIN);
//        memberRoomRepository.save(memberRoom);
//        return memberRoom.getRoom();
//    }
//
//
//    public void addFavorite(Room room, boolean isFavorite, long memberId) {
//        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//        Room findRoom = findVerifiedRoom(room.getRoomId());
//
//        Optional<MemberRoom> existingFavorite = findMember.getMemberRoomList().stream()
//                .filter(mr -> mr.getFavorite().equals(MemberRoom.Favorite.LIKE))
//                .filter(mr -> mr.getRoom().getRoomId() == findRoom.getRoomId())
//                .findFirst();
//
//        if (existingFavorite.isPresent())
//            throw new BusinessLogicException(ExceptionCode.DOUBLE_VOTE);
//
//        else if (isFavorite) {
//            findMember.setFavoriteCount(findMember.getFavoriteCount() + 1);
//            findRoom.setFavoriteCount(findRoom.getFavoriteCount() + 1);
//            findMember.setVoted(true);
//
//            Optional<MemberRoom> optionalMemberRoom = findRoom.getMemberRoomList()
//                    .stream()
//                    .filter(r -> r.getRoom().equals(findRoom))
//                    .findFirst();
//
//            if (optionalMemberRoom.isPresent()) { //수정
//                MemberRoom memberRoom = optionalMemberRoom.get();
//                memberRoom.setMember(findMember);
//                memberRoom.setRoom(findRoom);
//                memberRoom.setFavorite(MemberRoom.Favorite.LIKE);
//                memberRoomRepository.save(memberRoom);
//            }
//            memberRepository.save(findMember);
//            roomRepository.save(findRoom);
//        }
//    }
//
//
//    public void undoFavorite(Room room, boolean isFavorite, long memberId) {
//        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
//        Room findRoom = findVerifiedRoom(room.getRoomId());
//
//        Optional<MemberRoom> existingFavorite = findMember.getMemberRoomList().stream()
//                .filter(mr -> mr.getFavorite().equals(MemberRoom.Favorite.LIKE))
//                .filter(mr -> mr.getRoom().getRoomId() == findRoom.getRoomId())
//                .findFirst();
//
//        if (!existingFavorite.isPresent()) {
//            throw new BusinessLogicException(ExceptionCode.PLEASE_VOTE);
//
//        } else if (!isFavorite) {
//            findMember.setFavoriteCount(findMember.getFavoriteCount() - 1);
//            findRoom.setFavoriteCount(findRoom.getFavoriteCount() - 1);
//            findMember.setVoted(false);
//
//            Optional<MemberRoom> optionalMemberRoom = findRoom.getMemberRoomList()
//                    .stream()
//                    .filter(r -> r.getRoom().equals(findRoom))
//                    .findFirst();
//
//            MemberRoom memberRoom = memberRoomRepository.findByRoom(optionalMemberRoom.get().getRoom());
//            memberRoom.setFavorite(MemberRoom.Favorite.NONE);
//
//            memberRoomRepository.save(memberRoom);
//            memberRepository.save(findMember);
//            roomRepository.save(findRoom);
//        }
//    }
//
//
//    public Room findRoom(long roomId) {
//        Room room = roomRepository.findById(roomId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));
//        return room;
//    }
//
//    public Room findRoom(String roomTitle) {
//        Room room = roomRepository.findByTitle(roomTitle).orElseThrow(() -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));
//        return room;
//    }
//
//
//    public Page<Room> findNewRooms(int page, int size) {
//        Page<Room> roomPage = roomRepository.findAll(PageRequest.of(page, size, Sort.by("roomId").descending()));
//        List<Room> roomList = roomPage.getContent();
//
//        return new PageImpl<>(roomList, roomPage.getPageable(), roomPage.getTotalElements());
//    }
//
//
//    //비회원 추천목록
//    public Page<Room> findRecommendRoomsNonMember(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        List<Room> findRooms = roomRepository.findAll();
//
//        findRooms.sort(Comparator.comparingInt(Room::getFavoriteCount).reversed()); //좋아요순으로 정렬
//        List<Room> seletedRooms = findRooms.subList(0, Math.min(findRooms.size(), 50)); //50개 선택
//        Collections.shuffle(seletedRooms);
//        seletedRooms = seletedRooms.subList(0, Math.min(seletedRooms.size(), 20));
//
//        return new PageImpl<>(seletedRooms, pageable, seletedRooms.size());
//    }
//
//
//    //회원 추천목록
//    public Page<Room> findRecommendRooms(int page, int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        List<MemberTag> memberTags = memberTagRepository.findAll();
//        //log.info("# 관심태그 {}", memberTags.isEmpty());
//
//        if (memberTags.isEmpty()) {
//            return null;
//        }
//
//        Collections.shuffle(memberTags);
//        String tag1 = null, tag2 = null, tag3 = null;
//
//        switch (memberTags.size()) {
//            case 3:
//                tag3 = memberTags.get(2).getTag().getName();
//                //log.info("# 첫번째 스위치문 {}", tag3);
//            case 2:
//                tag2 = memberTags.get(1).getTag().getName();
//                //log.info("# 두번째 스위치문 {}", tag2);
//            case 1:
//                tag1 = memberTags.get(0).getTag().getName();
//                //log.info("# 세번째 스위치문 {}", tag1);
//                break;
//        }
//
//
//        List<Room> findRooms = new ArrayList<>();
//        Stream.of(tag1, tag2, tag3)
//                .filter(tag -> tag != null)
//                .forEach(tag -> {
//                    List<Room> roomList = roomRepository.findAllByRoomTagList_TagNameContaining(tag, pageable);
//                    findRooms.addAll(roomList);
//                });
//
//        findRooms.sort(Comparator.comparingInt(Room::getFavoriteCount).reversed()); //좋아요순으로 정렬
//        List<Room> seletedRooms = findRooms.subList(0, Math.min(findRooms.size(), 50)); //50개 선택
//        Collections.shuffle(seletedRooms);
//        seletedRooms = seletedRooms.subList(0, Math.min(seletedRooms.size(), 20));
//
//
//        //log.info("# 리턴값 {}", findRooms);
//        return new PageImpl<>(seletedRooms, pageable, seletedRooms.size());
//    }
//
//    public void leaveRoom(String nickName, String roomId) {
//        long longRoomId = Long.parseLong(roomId);
//        Room room = findVerifiedRoom(longRoomId);
//
//        //decrease1
//        room.setMemberCurrentCount(room.getMemberCurrentCount() - 1);
////        memberRoomRepository.deleteById();
//    }
//
//
//    // 외래키 제약조건 해결
//    public void deleteRoom(long roomId) {
//        Room findRoom = findVerifiedRoom(roomId);
//        List<MemberRoom> memberRoomList = findRoom.getMemberRoomList();
//        for (MemberRoom memberRoom : memberRoomList) {
//            memberRoomRepository.delete(memberRoom);
//        }
//        roomRepository.delete(findRoom);
//    }
//
//
//    //Todo : DB 체크 메서드
//    public Room findVerifiedRoom(long roomId) {
//        Optional<Room> room = roomRepository.findById(roomId);
//        Room findRoom = room.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));
//        return findRoom;
//    }
//
//
//    public ResponseEntity<ErrorResponse> verifyExistsCheck(String title) {
//        Optional<Room> optionalRoom = roomRepository.findByTitle(title);
//        if (optionalRoom.isPresent()) {
//            ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, "이미 사용중인 방제입니다.");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//        }
//        return null;
//    }
//}