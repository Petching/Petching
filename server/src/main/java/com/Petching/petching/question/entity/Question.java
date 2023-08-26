package com.Petching.petching.question.entity;

import com.Petching.petching.audit.BaseEntity;
import com.Petching.petching.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column
    private String title;

    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Column
    @Nullable
    private String imgUrl;

    @ElementCollection
    private List<String> imgUrls;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    public enum QuestionType {
        SYSTEM, PET, PET_SITTER, ETC
    }


    @Builder
    public Question(Long questionId, String title, String content, QuestionType questionType, @Nullable String imgUrl, List<String> imgUrls, User user) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
        this.questionType = questionType;
        this.imgUrl = imgUrl;
        this.imgUrls = imgUrls;
        this.user = user;
    }
}
