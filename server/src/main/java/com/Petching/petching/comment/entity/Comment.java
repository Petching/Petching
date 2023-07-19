package com.Petching.petching.comment.entity;

import com.Petching.petching.audit.BaseEntity;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
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
    @JoinColumn(name="board_id")
    @JsonIgnore
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    public long getBoardId(){
        return board.getBoardId();
    }

}
