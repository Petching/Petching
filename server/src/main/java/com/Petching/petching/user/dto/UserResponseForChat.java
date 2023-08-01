package com.Petching.petching.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseForChat {
    private Long userId;
    private String nickName;
    private String profileImgUrl;
}
