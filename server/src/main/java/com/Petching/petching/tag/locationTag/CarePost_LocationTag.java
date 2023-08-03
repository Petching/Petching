package com.Petching.petching.tag.locationTag;

import com.Petching.petching.carepost.entity.CarePost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter
@Setter
@NoArgsConstructor
public class CarePost_LocationTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carePostLocationTagId;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "POST_ID")
    private CarePost carePost;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LOCATION_TAG_ID")
    private LocationTag locationTag;

    public void setCarePost(CarePost carePost) {
        this.carePost = carePost;
//        if(!this.carePost.getPostLocationTags().contains(this)) {
//            this.carePost.setCarePost_LocationTag(this);
//        }

    }

    public CarePost_LocationTag(CarePost carePost, LocationTag locationTag) {
        this.carePost = carePost;
        this.locationTag = locationTag;
    }
}
