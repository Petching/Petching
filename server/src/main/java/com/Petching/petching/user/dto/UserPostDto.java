package com.Petching.petching.user.dto;

import com.Petching.petching.user.entity.Role;
import com.Petching.petching.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Builder @AllArgsConstructor
public class UserPostDto {
    @Email @NotBlank(message = "이메일은 공백일 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;

    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    private String nickName;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .nickName(this.nickName)
                .build();
    }
}
