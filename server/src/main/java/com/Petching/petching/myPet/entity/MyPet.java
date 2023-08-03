package com.Petching.petching.myPet.entity;

import com.Petching.petching.audit.Auditable;
import com.Petching.petching.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity @Getter @Builder @Setter
@NoArgsConstructor @AllArgsConstructor
public class MyPet extends Auditable {
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

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

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
    public void updateGender (String gender) {
        this.gender = gender;
    }
}
