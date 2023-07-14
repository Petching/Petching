package com.Petching.petching.pet;

import com.Petching.petching.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pet extends Auditable {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long petId;

    @Column
    private String name;

    @Column
    private String breed;

    @Column
    private String gender;

    @Column
    private int age;
    @Column
    private String weight;
    @Column
    private Boolean isVaccination;
    @Column
    private String memo;
}
