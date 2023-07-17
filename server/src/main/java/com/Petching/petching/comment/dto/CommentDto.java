package com.Petching.petching.comment.dto;

import com.Petching.petching.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class CommentDto {
    @Getter
    public static class Post{
        @NotBlank(message="content is not null")
        private String content;

        @NotBlank(message = "userId not null, JWT 구현이후 token 으로 바꿀 예정입니다.")
        private long userId;

    }
    @Getter
    @Setter
    public static class Patch{
        @NotBlank(message="content not null")
        private String content;
    }
    @Getter
    @AllArgsConstructor
    public static class Response{
        private long commentId;
        private String content;
        private long boardId;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

    }
}
