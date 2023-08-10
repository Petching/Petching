package com.Petching.petching.tag.petSize;

import com.Petching.petching.carepost.entity.CarePost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CarePost_PetSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long carePostPetSizeId;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "POST_ID")
    private CarePost carePost;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PET_SIZE_ID")
    private PetSize petSize;

    public void setCarePost(CarePost carePost) {
        this.carePost = carePost;
    }

    public CarePost_PetSize(CarePost carePost, PetSize petsize) {
        this.carePost = carePost;
        this.petSize = petsize;
    }

}
