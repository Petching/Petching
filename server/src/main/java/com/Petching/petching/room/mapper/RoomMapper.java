package com.Petching.petching.room.mapper;

import com.Petching.petching.room.dto.RoomDto;
import com.Petching.petching.room.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {


    //Todo : 방생성
    default Room postDtoToRoom(RoomDto.Post requestBody) {
        if (requestBody == null) return null;

        Room room = new Room();
        room.setAdminUserNickname(requestBody.getNickName());
        room.setTitle(requestBody.getTitle());

        return room;
    }


    //Todo : 방생성 응답
    default RoomDto.PostResponseDto roomToPostResponseDto(Room createRoom) {
        if (createRoom == null) return null;
        RoomDto.PostResponseDto postResponseDto = new RoomDto.PostResponseDto();

        if (createRoom.getRoomId() != null) postResponseDto.setRoomId(createRoom.getRoomId());
        postResponseDto.setTitle(createRoom.getTitle());

        if (createRoom.getAdminUserId() != null)
            postResponseDto.setNickName(createRoom.getAdminUserNickname());

        return postResponseDto;
    }




    //Todo : 최신순 방목록조회 응답
    default List<RoomDto.GetNewRoomResponseDtos> roomToNewRoomResponseDtos(List<Room> roomList) {
        if (roomList == null) return null;
        List<RoomDto.GetNewRoomResponseDtos> list = new ArrayList<RoomDto.GetNewRoomResponseDtos>(roomList.size());

        for (Room room : roomList) {
            list.add(roomToGetNewRoomResponseDtos(room));
        }
        return list;
    }

    default RoomDto.GetNewRoomResponseDtos roomToGetNewRoomResponseDtos(Room room) {
        if (room == null) return null;
        RoomDto.GetNewRoomResponseDtos getNewRoomResponseDtos = new RoomDto.GetNewRoomResponseDtos();

        if (room.getRoomId() != null) getNewRoomResponseDtos.setRoomId(room.getRoomId());
        getNewRoomResponseDtos.setTitle(room.getTitle());

        return getNewRoomResponseDtos;
    }
}
