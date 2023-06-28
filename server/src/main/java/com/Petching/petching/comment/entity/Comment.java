package com.Petching.petching.comment.entity;

import com.Petching.petching.audit.BaseEntity;
import com.Petching.petching.board.entity.Board;
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
    @JoinColumn(name="BOARD_ID")
    private Board board;

    public long getBoardId(){
        return board.getBoardId();
    }

}
