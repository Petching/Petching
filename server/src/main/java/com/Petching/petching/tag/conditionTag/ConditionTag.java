package com.Petching.petching.tag.conditionTag;

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
    private List<CarePost_ConditionTag> postConditionTags = new ArrayList<>();

    public ConditionTag(String body) {
        this.body = body;
    }

}