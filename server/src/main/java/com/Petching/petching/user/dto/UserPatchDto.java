package com.Petching.petching.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
public class UserPatchDto {
    @Positive
    private long userId;

    @Email
    private String email;

    private String password;

    private String nickName;

    private String address;


}
