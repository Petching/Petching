package com.Petching.petching.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserChatResponseDto {
    private long userId;
    private String nickName;
    private String profileImgUrl;

}
