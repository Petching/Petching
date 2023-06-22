package com.Petching.petching.board.entity;

import com.Petching.petching.audit.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @Column
    private String content;
    // 좋아요 눌렀는지 여부
    @Column
    private boolean checkLike;

    // N : 1(Member) 양방향 매핑
    // todo : 질문이 삭제 될 때, 질문에 달린 답변도 삭제되어야 함으로, 질문 엔티티에 cascade 설정이 되어있어야 한다.
    // todo : 상위 엔티티에서 cascade 설정이 붙어야 함!
//    @ManyToOne
//    @JoinColumn(name="MEMBER_ID")
//    private Member member;
//
//    // todo : 게시글이 삭제 될 때, 게시글에 달린 댓글도 삭제되어야 함으로, Board 엔티티에 cascade 설정이 되어있어야 한다.
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
//    List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private Long likes;
    // 좋아요 누른 유저의 Id들이 저장된 리스트
    @ElementCollection
    private List<Long> likedUserIds;


}
