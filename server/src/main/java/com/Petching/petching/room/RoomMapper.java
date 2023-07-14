//package com.Petching.petching.room;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.ReportingPolicy;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public interface RoomMapper {
//
//
//    //Todo : 방생성
//    default Room postDtoToRoom(RoomDto.Post requestBody) {
//        if (requestBody == null) return null;
//
//        Room room = new Room();
//        room.setAdminMemberId(requestBody.getAdminMemberId());
//        room.setTitle(requestBody.getTitle());
//        room.setInfo(requestBody.getInfo());
//        room.setImageUrl(requestBody.getImageUrl());
//        room.setPassword(requestBody.getPassword());
//        room.setPrivate(requestBody.isPrivate());
//        room.setMemberMaxCount(requestBody.getMemberMaxCount());
//
//        return room;
//    }
//
//
//    //Todo : 방생성 응답
//    default RoomDto.PostResponseDto roomToPostResponseDto(Room createRoom) {
//        if (createRoom == null) return null;
//        RoomDto.PostResponseDto postResponseDto = new RoomDto.PostResponseDto();
//
//        if (createRoom.getRoomId() != null) postResponseDto.setRoomId(createRoom.getRoomId());
//        postResponseDto.setTitle(createRoom.getTitle());
//        postResponseDto.setInfo(createRoom.getInfo());
//
//        if (createRoom.getAdminMemberId() != null)
//            postResponseDto.setAdminMemberId(createRoom.getAdminMemberId());
//
//        postResponseDto.setImageUrl(createRoom.getImageUrl());
//        postResponseDto.setMemberMaxCount(createRoom.getMemberMaxCount());
//        postResponseDto.setMemberCurrentCount(createRoom.getMemberCurrentCount());
//        postResponseDto.setPrivate(createRoom.isPrivate());
//        postResponseDto.setPassword(createRoom.getPassword());
//        postResponseDto.setFavoriteCount(createRoom.getFavoriteCount());
//
//        return postResponseDto;
//    }
//
//
//    //Todo : 방수정
//
//    default Room patchDtoToRoom(RoomDto.Patch requestBody) {
//        if (requestBody == null) {
//            return null;
//        }
//
//        Room room = new Room();
//
//        room.setRoomId(requestBody.getRoomId());
//        room.setAdminMemberId(requestBody.getAdminMemberId());
//        room.setTitle(requestBody.getTitle());
//        room.setInfo(requestBody.getInfo());
//
//        room.setPassword(requestBody.getPassword());
//        System.out.println(requestBody.getPassword()); // 비밀번호 가져오기
//
//        room.setPrivate(requestBody.isPrivate());
//        room.setMemberMaxCount(requestBody.getMemberMaxCount());
//        return room;
//    }
//
//
//    //Todo : 방수정 응답
//    default RoomDto.PatchResponseDto roomToPatchResponseDto(Room room) {
//        if (room == null) return null;
//        RoomDto.PatchResponseDto patchResponseDto = new RoomDto.PatchResponseDto();
//
//        if (room.getRoomId() != null) patchResponseDto.setRoomId(room.getRoomId());
//        patchResponseDto.setTitle(room.getTitle());
//        patchResponseDto.setInfo(room.getInfo());
//        patchResponseDto.setAdminNickname(room.getAdminNickname());
//        patchResponseDto.setImageUrl(room.getImageUrl());
//        patchResponseDto.setMemberMaxCount(room.getMemberMaxCount());
//        patchResponseDto.setMemberCurrentCount(room.getMemberCurrentCount());
//        patchResponseDto.setPrivate(room.isPrivate());
//        patchResponseDto.setPassword(room.getPassword());
//        patchResponseDto.setPassword(room.getPassword());
//        patchResponseDto.setFavoriteCount(room.getFavoriteCount());
//        patchResponseDto.setFavoriteStatus(getRoomFavorite(room, room.getMemberRoomList()));
//
//        return patchResponseDto;
//    }
//
//
//    //Todo : 찜하기 & 찜취소
//    default Room PostFavoriteDtoToRoom(RoomDto.PostFavorite requestBody) {
//        if (requestBody == null) return null;
//
//        Room room = new Room();
//        room.setFavoriteCount(room.getFavoriteCount());
//        room.setRoomId(requestBody.getRoomId());
//        return room;
//    }
//
//
//    //Todo : 방장권한 위임 & 응답
//    default Room patchAdminDtoToRoom(RoomDto.PatchAdmin requestBody) {
//        if (requestBody == null) return null;
//
//        Room room = new Room();
//        room.setAdminMemberId(requestBody.getNewAdminId());
//        room.setRoomId(requestBody.getRoomId());
//        return room;
//    }
//
//    default RoomDto.PatchAdminResponseDto roomToPatchAdminResponseDto(Room room) {
//        if (room == null) return null;
//
//        RoomDto.PatchAdminResponseDto patchAdminResponseDto = new RoomDto.PatchAdminResponseDto();
//
//        if (room.getRoomId() != null) patchAdminResponseDto.setRoomId(room.getRoomId());
//        patchAdminResponseDto.setRoomId(room.getRoomId());
//        patchAdminResponseDto.setAdminMemberId(room.getAdminMemberId());
//        patchAdminResponseDto.setAdminNickname(room.getAdminNickname());
//        patchAdminResponseDto.setImageUrl(room.getImageUrl());
//
//        return patchAdminResponseDto;
//    }
//
//
//    //Todo : 최신순 방목록조회 응답
//    default List<RoomDto.GetNewRoomResponseDtos> roomToNewRoomResponseDtos(List<Room> roomList) {
//        if (roomList == null) return null;
//        List<RoomDto.GetNewRoomResponseDtos> list = new ArrayList<RoomDto.GetNewRoomResponseDtos>(roomList.size());
//
//        for (Room room : roomList) {
//            list.add(roomToGetNewRoomResponseDtos(room));
//        }
//        return list;
//    }
//
//    default RoomDto.GetNewRoomResponseDtos roomToGetNewRoomResponseDtos(Room room) {
//        if (room == null) return null;
//        RoomDto.GetNewRoomResponseDtos getNewRoomResponseDtos = new RoomDto.GetNewRoomResponseDtos();
//
//        if (room.getRoomId() != null) getNewRoomResponseDtos.setRoomId(room.getRoomId());
//        getNewRoomResponseDtos.setTitle(room.getTitle());
//        getNewRoomResponseDtos.setInfo(room.getInfo());
//        getNewRoomResponseDtos.setImageUrl(room.getImageUrl());
//        getNewRoomResponseDtos.setMemberMaxCount(room.getMemberMaxCount());
//        getNewRoomResponseDtos.setMemberCurrentCount(room.getMemberCurrentCount());
//        getNewRoomResponseDtos.setPrivate(room.isPrivate());
//        getNewRoomResponseDtos.setPassword(room.getPassword());
//        getNewRoomResponseDtos.setFavoriteCount(room.getFavoriteCount());
//        getNewRoomResponseDtos.setFavoriteStatus(getRoomFavorite(room, room.getMemberRoomList()));
//
//        return getNewRoomResponseDtos;
//    }
//
////    default List<TagDto.TagResponseDto> getRoomTags(List<RoomTag> roomTagList) {
////        return roomTagList.stream()
////                .map(roomTag -> new TagDto.TagResponseDto(roomTag.getTag().getTagId(), roomTag.getTag().getName()))
////                .collect(Collectors.toList());
////    }
//
//    default MemberRoom.Favorite getRoomFavorite(Room room, List<MemberRoom> memberRoomList) {
//        if (memberRoomList == null) return null;
//
//        MemberRoom memberRoom = memberRoomList.stream()
//                .filter(r -> r.getRoom().getRoomId().equals(room.getRoomId())).findFirst().get();
//
//        return memberRoom.getFavorite();
//    }
//
//    default List<RoomDto.RoomAdminDto> getRoomAdmin(Room room) {
//        if (room == null) return null;
//        List<RoomDto.RoomAdminDto> responseDtoList = new ArrayList<>();
//        RoomDto.RoomAdminDto roomAdminDto = new RoomDto.RoomAdminDto();
//        roomAdminDto.setAdminMemberId(room.getAdminMemberId());
//        roomAdminDto.setAdminNickname(room.getAdminNickname());
//        responseDtoList.add(roomAdminDto);
//
//        return responseDtoList;
//    }
//
//
//    //Todo : 추천스터디방 조회 (비회원)
//    default List<RoomDto.GetRecommendRoomResponseDtos> memberToNonMemberRecommendResponseDtos(List<Room> recommendList) {
//        if (recommendList == null) return null;
//        List<RoomDto.GetRecommendRoomResponseDtos> list = new ArrayList<RoomDto.GetRecommendRoomResponseDtos>(recommendList.size());
//
//        for (Room room : recommendList) {
//            list.add(roomToGetNonMemberResponseDtos(room));
//        }
//        return list;
//    }
//
//    default RoomDto.GetRecommendRoomResponseDtos roomToGetNonMemberResponseDtos(Room room) {
//        if (room == null) return null;
//        RoomDto.GetRecommendRoomResponseDtos recommendRoomResponseDtos = new RoomDto.GetRecommendRoomResponseDtos();
//
//        if (room.getRoomId() != null) recommendRoomResponseDtos.setRoomId(room.getRoomId());
//        recommendRoomResponseDtos.setTitle(room.getTitle());
//        recommendRoomResponseDtos.setInfo(room.getInfo());
//        recommendRoomResponseDtos.setImageUrl(room.getImageUrl());
//        recommendRoomResponseDtos.setMemberMaxCount(room.getMemberMaxCount());
//        recommendRoomResponseDtos.setMemberCurrentCount(room.getMemberCurrentCount());
//        recommendRoomResponseDtos.setPrivate(room.isPrivate());
//        recommendRoomResponseDtos.setPassword(room.getPassword());
//        recommendRoomResponseDtos.setFavoriteCount(room.getFavoriteCount());
//
//        return recommendRoomResponseDtos;
//    }
//
//
//    //Todo : 추천스터디방 조회 (회원)
//    default List<RoomDto.GetRecommendRoomResponseDtos> memberToRecommendResponseDtos(List<Room> recommendList) {
//        if (recommendList == null) return null;
//        List<RoomDto.GetRecommendRoomResponseDtos> list = new ArrayList<RoomDto.GetRecommendRoomResponseDtos>(recommendList.size());
//
//        for (Room room : recommendList) {
//            list.add(roomToGetRecommendResponseDtos(room));
//        }
//        return list;
//    }
//
//
//    default RoomDto.GetRecommendRoomResponseDtos roomToGetRecommendResponseDtos(Room room) {
//        if (room == null) return null;
//        RoomDto.GetRecommendRoomResponseDtos recommendRoomResponseDtos = new RoomDto.GetRecommendRoomResponseDtos();
//
//        if (room.getRoomId() != null) recommendRoomResponseDtos.setRoomId(room.getRoomId());
//        recommendRoomResponseDtos.setTitle(room.getTitle());
//        recommendRoomResponseDtos.setInfo(room.getInfo());
//        recommendRoomResponseDtos.setImageUrl(room.getImageUrl());
//        recommendRoomResponseDtos.setMemberMaxCount(room.getMemberMaxCount());
//        recommendRoomResponseDtos.setMemberCurrentCount(room.getMemberCurrentCount());
//        recommendRoomResponseDtos.setPrivate(room.isPrivate());
//        recommendRoomResponseDtos.setPassword(room.getPassword());
//        recommendRoomResponseDtos.setFavoriteCount(room.getFavoriteCount());
//        recommendRoomResponseDtos.setFavoriteStatus(getRoomFavorite(room, room.getMemberRoomList()));
//
//        return recommendRoomResponseDtos;
//    }
//}