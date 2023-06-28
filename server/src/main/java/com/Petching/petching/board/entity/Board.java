package com.Petching.petching.board.entity;

import com.Petching.petching.audit.BaseEntity;
import com.Petching.petching.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private boolean checkLike;

    @Column(nullable = false)
    private Long likes;

    @ElementCollection
    private List<Long> likedUserIds;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();

    @Column
    private long commentCount;

    public Board() {
        this.likes = 0L;
    }
    public void addComment(Comment comment){comments.add(comment);}
   //TODO:이미지
   //TODO:게시글이 삭제 될 때, 게시글에 달린 댓글도 삭제되어야 함으로, Board 엔티티에 cascade 설정이 되어있어야 한다.







    // N : 1(Member) 양방향 매핑
    // todo : 질문이 삭제 될 때, 질문에 달린 답변도 삭제되어야 함으로, 질문 엔티티에 cascade 설정이 되어있어야 한다.
    // todo : 상위 엔티티에서 cascade 설정이 붙어야 함!
//    @ManyToOne
//    @JoinColumn(name="MEMBER_ID")
//    private Member member;


}
