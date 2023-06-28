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
        @Positive
        private long boardId;

        public Board getBoard() {
            Board board = new Board();
            board.setBoardId(boardId);
            return board;
        }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{
        private long commentId;
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
        //멤버아이디명
        //멤버아이디번호

        //public void setMember(Member member){this.memberId= member.getMemberId();}
        //public void setBoard(Board board){this.b_id = board.getB_id();}
    }
}
