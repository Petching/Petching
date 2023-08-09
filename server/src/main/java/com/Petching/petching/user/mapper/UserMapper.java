package com.Petching.petching.user.mapper;

import com.Petching.petching.user.dto.UserPatchDto;
import com.Petching.petching.user.dto.UserResponseDto;
import com.Petching.petching.user.entity.User;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto EntityToResponseDto (User user);

    User userPatchDtoToUser(UserPatchDto userPatchDto);

    default UserResponseDto.UserGetResponseDto EntityToGetResponseDto (User user) {
        String socialType;

        if (user.getSocialType() == null) {
            socialType = "null";
        } else socialType = user.getSocialType().name();

        UserResponseDto.UserGetResponseDto result = new UserResponseDto.UserGetResponseDto(
                user.isUserDivision(),
                user.getNickName(),
                user.getEmail(),
                user.getAddress(),
                user.getProfileImgUrl(),
                socialType
        );
        return result;
    }

}
