package com.Petching.petching.global.chatting;


import com.Petching.petching.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    User ChatPostDtoToUser(ChatDto.Post post);

    ChatDto.RoomResponse chatRoomToRoomResponseDto(ChatRoom chatRoom);

    List<ChatDto.RoomResponse> chatRoomListToRoomResponseDtos(List<ChatRoom> chatRooms);

    ChatDto.MessageResponse messageToMessageResponseDto(ChatMessage message);

    List<ChatDto.MessageResponse> messagesToMessageResponseDtos(List<ChatMessage> messages);

}
