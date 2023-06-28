package com.Petching.petching.user.mapper;

import com.Petching.petching.user.dto.UserResponseDto;
import com.Petching.petching.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto EntityToResponseDto (User user);
}
