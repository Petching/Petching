package com.Petching.petching.user.entity;

import com.Petching.petching.audit.Auditing;
import lombok.*;

import javax.persistence.*;

@Entity @Getter @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "USERS")
public class User extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 15, unique = true)
    private String nickName;

    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String address;

//    public void updatePassword (PasswordEncoder passwordEncoder, String password) {
//        this.password = passwordEncoder.encode(password);
//    }
    public void updateNickName (String nickName) {
        this.nickName = nickName;
    }
}
