package com.Petching.petching.conditionTag;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity @Getter
@NoArgsConstructor
public class ConditionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conditionTagId;

    @Column @Setter @Getter
    private String body;

    @OneToMany(mappedBy = "conditionTag", cascade = CascadeType.ALL)
    private List<CarePost_ConditionTag> postTags = new ArrayList<>();

    public ConditionTag(String body) {
        this.body = body;
    }

}