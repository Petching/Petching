package com.Petching.petching.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class BoardDto {
    // 게시판 생성
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post{
        @NotBlank(message = "title is not null")
        private String title;
        @NotBlank(message = "content is not null")
        private String content;
    }
    // 게시판 수정
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{
        private long boardId;
        @NotBlank(message = "title is not null")
        private String title;
        @NotBlank(message = "content is not null")
        private String content;
    }
    // 게시판 상세 조회 응답내용
    @Getter
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

        // public void setMember(Member member){this.memberId = member.getMemberId();}
        //

    }



}
