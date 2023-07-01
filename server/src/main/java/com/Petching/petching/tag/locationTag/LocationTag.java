package com.Petching.petching.tag.locationTag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity @Getter
@NoArgsConstructor
public class LocationTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationTagId;

    @Column
    @Setter
    @Getter
    private String body;

    @OneToMany(mappedBy = "locationTag", cascade = CascadeType.ALL)
    private List<CarePost_LocationTag> postLocationTags = new ArrayList<>();

    public LocationTag(String body) {
        this.body = body;
    }

}
