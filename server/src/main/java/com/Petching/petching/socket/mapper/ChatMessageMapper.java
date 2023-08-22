package com.Petching.petching.socket.mapper;

import com.Petching.petching.socket.dto.ChatMessageDto;
import com.Petching.petching.socket.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMessageMapper {
    ChatMessage chatMessageDtoToChatMessage(ChatMessageDto dto);
}
