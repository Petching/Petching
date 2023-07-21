package com.Petching.petching.board.dto;

import com.Petching.petching.comment.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Post{
        // TODO: JWT 구현 이후 userId 삭제 후 token 을 받아 요청한 유저를 찾아 Board 객체에 유저 지정
        @NotNull(message = "userId not null, JWT 구현이후 token 으로 바꿀 예정입니다.")
        private long userId;
        @NotBlank(message = "title not null")
        private String title;
        @NotBlank(message = "content not null")
        private String content;


        private List<String> imgUrls;

        Post(){

        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Patch{

        // TODO: JWT 구현 이후 userId 삭제 후 token 을 받아 요청한 유저가 글을 작성한 유저인지 검증
        @NotNull(message = "userId not null, JWT 구현이후 token 으로 바꿀 예정입니다.")
        private long userId;

        private long boardId;
        @NotBlank(message = "title not null")
        private String title;
        @NotBlank(message = "content not null")
        private String content;

        private List<String> imgUrls;
        Patch(){

        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Response{
        private long boardId;
        private String title;
        private String profileImgUrl;
        private String nickName;
        private int likes;

        private LocalDateTime createdAt;

        private int commentCount;

        // 좋아요 눌렀는지 여부
        private boolean checkLike;

        private List<String> imgUrls;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Detail{
        private long boardId;
        private String title;
        private String content;
        private int likes;
        private String profileImgUrl;
        private String nickName;
        private LocalDateTime createdAt;
        // 좋아요 눌렀는지 여부
        private boolean checkLike;
        private List<CommentDto.Response> comments;
        private int commentCount;

        private List<String> imgUrls;

    }
}
