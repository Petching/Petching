package com.Petching.petching.myPet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class MyPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myPetId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column
    private String species;

    @Column
    private String gender;

    @Column
    private String significant;

    @Column
    private String petImgUrl;

    public void updateName (String name) {
        this.name = name;
    }
    public void updateAge (int age) {
        this.age = age;
    }
    public void updateSpecies (String species) {
        this.species = species;
    }
    public void updateSignificant (String significant) {
        this.significant = significant;
    }
    public void updateImgUrl (String imgUrl) {
        this.petImgUrl = imgUrl;
    }
}
