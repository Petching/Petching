package com.Petching.petching.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String nickName;
    private String email;
    private String address;
}
