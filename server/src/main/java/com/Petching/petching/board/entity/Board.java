package com.Petching.petching.board.entity;

import com.Petching.petching.audit.BaseEntity;
import com.Petching.petching.comment.entity.Comment;
import com.Petching.petching.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Builder
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Long likes;

    @ElementCollection
    private List<Long> likedUserIds;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();

    @Column
    private long commentCount;

    @Column
    @Nullable
    private String imgUrl;

    @ElementCollection
    private List<String> imgUrls;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Board() {
        this.likes = 0L;
    }

    public void addComment(Comment comment){comments.add(comment);}





    // N : 1(Member) 양방향 매핑
    // todo : 질문이 삭제 될 때, 질문에 달린 답변도 삭제되어야 함으로, 질문 엔티티에 cascade 설정이 되어있어야 한다.
    // todo : 상위 엔티티에서 cascade 설정이 붙어야 함!



}
