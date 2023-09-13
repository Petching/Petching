package com.Petching.petching.user.entity;

import com.Petching.petching.audit.Auditable;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.carepost.entity.CarePost;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 15, unique = true)
    private String nickName;

    @Column
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    private String profileImgUrl;

    @Transient
    private boolean userDivision;

    @ElementCollection
    private List<Long> likedBoardList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CarePost> carePosts = new ArrayList<>();


    public void updateNickName (String nickName) {
        this.nickName = nickName;
    }
    public void updatePassword (String password) {
        this.password = password;
    }
    public void updateAddress (String address) {
        this.address = address;
    }

    public void addLikedBoard(Long boardId){
        likedBoardList.add(boardId);
    }

    public void deleteLikedBoard(Long boardId){
        likedBoardList.remove(boardId);
    }

    public void updateProfileImgUrl(String imgUrl) {
        this.profileImgUrl = imgUrl;
    }
}
