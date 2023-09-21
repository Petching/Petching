package com.Petching.petching.user.mapper;

import com.Petching.petching.user.dto.UserPatchDto;
import com.Petching.petching.user.dto.UserResponseDto;
import com.Petching.petching.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-19T14:08:17+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto EntityToResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        String nickName = null;
        String email = null;
        String address = null;

        nickName = user.getNickName();
        email = user.getEmail();
        address = user.getAddress();

        UserResponseDto userResponseDto = new UserResponseDto( nickName, email, address );

        return userResponseDto;
    }

    @Override
    public User userPatchDtoToUser(UserPatchDto userPatchDto) {
        if ( userPatchDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userId( userPatchDto.getUserId() );
        user.nickName( userPatchDto.getNickName() );
        user.password( userPatchDto.getPassword() );
        user.email( userPatchDto.getEmail() );
        user.address( userPatchDto.getAddress() );

        return user.build();
    }
}
