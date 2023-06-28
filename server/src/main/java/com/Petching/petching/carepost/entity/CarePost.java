package com.Petching.petching.carepost.entity;

import com.Petching.petching.audit.Auditable;
import com.Petching.petching.conditionTag.CarePost_ConditionTag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarePost extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String image;

    @Column
    private String address;

    @Column
    private String condition;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @JsonIgnore
    @OneToMany(mappedBy = "carePost", cascade = CascadeType.ALL)
    private List<CarePost_ConditionTag> postConditionTags = new ArrayList<>();

    public void setCarePost_ConditionTag(CarePost_ConditionTag postConditionTags) {
        this.postConditionTags.add(postConditionTags);
        if (postConditionTags.getCarePost() != this) {
            postConditionTags.setCarePost(this);
        }
    }

}
