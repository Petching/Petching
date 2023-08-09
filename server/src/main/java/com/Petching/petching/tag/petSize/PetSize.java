package com.Petching.petching.tag.petSize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
public class PetSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petSizeId;

    @Column
    @Setter
    @Getter
    private String body;

    @OneToMany(mappedBy = "petSize", cascade = CascadeType.ALL)
    private List<CarePost_PetSize> postPetSizes = new ArrayList<>();

    public PetSize(String body) {
        this.body = body;
    }
}
