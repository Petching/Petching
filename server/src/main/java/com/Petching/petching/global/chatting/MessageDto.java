package com.Petching.petching.global.chatting;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {

    @NotNull
    private Long roomId;
    @NotNull
    private Long senderId;
    @NotBlank
    private String content;


    @Builder
    public MessageDto(Long roomId, Long senderId, String content) {
        this.roomId = roomId;
        this.senderId = senderId;
        this.content = content;
    }

}
