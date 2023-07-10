package com.Petching.petching.user.entity;

import com.Petching.petching.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity @Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "USERS")
public class User extends Auditable {
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

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    private String refreshToken;

   /* public void passwordEncode (PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }*/

    public void updateRefreshToken (String updateRefreshToke) {
        this.refreshToken = updateRefreshToke;
    }

    public void updateNickName (String nickName) {
        this.nickName = nickName;
    }
    public void updateEmail (String email) {
        this.email = email;
    }
    public void updatePassword (String password) {
        this.password = password;
    }
    public void updateAddress (String address) {
        this.address = address;
    }
}
