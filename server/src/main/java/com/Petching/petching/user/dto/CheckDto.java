package com.Petching.petching.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class CheckDto {
    @Email
    private String email;

    private String nickName;
}
