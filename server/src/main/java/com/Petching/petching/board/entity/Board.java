package com.Petching.petching.board.entity;

import com.Petching.petching.audit.BaseEntity;
import com.Petching.petching.comment.entity.Comment;
import com.Petching.petching.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    @ColumnDefault("0")
    private int likes;

    @ElementCollection
    private List<Long> likedUserIds;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Comment> comments = new ArrayList<>();

    @Column
    private int commentCount = comments.size();


    @Builder
    public Board(long boardId, String title, String content, int likes, List<Long> likedUserIds, List<Comment> comments, int commentCount, @Nullable String imgUrl, List<String> imgUrls, User user) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.likedUserIds = likedUserIds;
        this.comments = comments;
        this.commentCount = commentCount;
        this.imgUrl = imgUrl;
        this.imgUrls = imgUrls;
        this.user = user;
    }


    @Column
    @Nullable
    private String imgUrl;

    @ElementCollection
    private List<String> imgUrls;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    public void addComment(Comment comment){comments.add(comment);}

    public void addLikedUserId(Long boardId){likedUserIds.add(boardId);}

    public void setCommentCount(){commentCount = comments.size();}


    // N : 1(Member) 양방향 매핑
    // todo : 질문이 삭제 될 때, 질문에 달린 답변도 삭제되어야 함으로, 질문 엔티티에 cascade 설정이 되어있어야 한다.
    // todo : 상위 엔티티에서 cascade 설정이 붙어야 함!



}
