package com.Petching.petching.board.dto;

import com.Petching.petching.comment.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{
        private long boardId;
        @NotBlank(message = "title not null")
        private String title;
        @NotBlank(message = "content not null")
        private String content;
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

        // public void setMember(Member member){this.memberId = member.getMemberId();}
    }
}
