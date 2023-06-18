package com.Petching.petching.user.dto;

import lombok.*;

import javax.persistence.*;

@Entity @Getter @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String address;

}
