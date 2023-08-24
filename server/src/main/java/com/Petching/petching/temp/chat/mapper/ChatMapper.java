package com.Petching.petching.temp.chat.mapper;

import com.Petching.petching.temp.chat.dto.ChatDto;
import com.Petching.petching.temp.chat.entity.ChatMessage;
import com.Petching.petching.temp.chat.entity.ChatRoom;
import com.Petching.petching.user.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    User ChatPostDtoToMember(ChatDto.Post post);

    ChatDto.RoomResponse chatRoomToRoomResponseDto(ChatRoom chatRoom);
    List<ChatDto.RoomResponse> chatRoomListToRoomResponseDtos(List<ChatRoom> chatRooms);

    ChatDto.MessageResponse messageToMessageResponseDto(ChatMessage message);

    List<ChatDto.MessageResponse> messagesToMessageResponseDtos(List<ChatMessage> messages);

}
