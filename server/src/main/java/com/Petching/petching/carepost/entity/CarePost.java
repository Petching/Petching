package com.Petching.petching.carepost.entity;


import com.Petching.petching.audit.Auditable;
import com.Petching.petching.tag.conditionTag.CarePost_ConditionTag;
import com.Petching.petching.tag.locationTag.CarePost_LocationTag;
import com.Petching.petching.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
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

    @ElementCollection
    private List<String > imgUrls;
//    @Column
//    private String address;
//
//    @Column
//    private String condition;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "carePost", cascade = CascadeType.ALL)
    private List<CarePost_ConditionTag> postConditionTags = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "carePost", cascade = CascadeType.ALL)
    private List<CarePost_LocationTag> postLocationTags = new ArrayList<>();

    public void setCarePost_ConditionTag(CarePost_ConditionTag postConditionTags) {
        this.postConditionTags.add(postConditionTags);
        if (postConditionTags.getCarePost() != this) {
            postConditionTags.setCarePost(this);
        }
    }

    public void setCarePost_LocationTag(CarePost_LocationTag postLocationTags) {
        this.postLocationTags.add(postLocationTags);
        if (postLocationTags.getCarePost() != this) {
            postLocationTags.setCarePost(this);
        }
    }

    public CarePost(){

    }
}
