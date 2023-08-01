package com.Petching.petching.comment.entity;

import com.Petching.petching.audit.BaseEntity;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;
    @Column(nullable=false)
    private String content;

    @ManyToOne
    @JoinColumn(name="boardId")
    @JsonIgnore
    private Board board;


    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;


    @Builder
    public Comment(long commentId, String content, Board board, User user) {
        this.commentId = commentId;
        this.content = content;
        this.board = board;
        this.user = user;
    }
    public long getBoardId(){
        return board.getBoardId();
    }

}
