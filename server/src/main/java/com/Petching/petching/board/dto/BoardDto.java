package com.Petching.petching.board.dto;

import com.Petching.petching.comment.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

public class BoardDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post{
        @NotBlank(message = "title not null")
        private String title;
        @NotBlank(message = "content not null")
        private String content;

        // TODO: JWT 구현 이후 userId 삭제 후 token 을 받아 요청한 유저를 찾아 Board 객체에 유저 지정
        @NotBlank(message = "userId not null, JWT 구현이후 token 으로 바꿀 예정입니다.")
        private long userId;

        private String imgUrl;

        Post(){

        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{

        // TODO: JWT 구현 이후 userId 삭제 후 token 을 받아 요청한 유저가 글을 작성한 유저인지 검증
        @NotBlank(message = "userId not null, JWT 구현이후 token 으로 바꿀 예정입니다.")
        private long userId;

        private long boardId;
        @NotBlank(message = "title not null")
        private String title;
        @NotBlank(message = "content not null")
        private String content;

        private String imgUrl;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response{
        private long boardId;
        private String title;
        private String content;
        private long likes;
        // 멤버 아이디
        // 멤버 닉네임
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        // 좋아요 눌렀는지 여부
        private boolean checkLike;

        private String imgUrl;

//        private List<CommentDto.Response> comments;
//        private long commentCount;

        // public void setMember(Member member){this.memberId = member.getMemberId();}
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Detail{
        private long boardId;
        private String title;
        private String content;
        private long likes;
        // 멤버 아이디
        // 멤버 닉네임
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        // 좋아요 눌렀는지 여부
        private boolean checkLike;
        private List<CommentDto.Response> comments;
        private long commentCount;

        private String imgUrl;

        // public void setMember(Member member){this.memberId = member.getMemberId();
    }
}
