package com.Petching.petching.user.repository;

import com.Petching.petching.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String email);
    Optional<User> findByNickName (String nickName);

//    Optional<User> findByRefreshToken (String refreshToken);

//    Optional<User> findBySo
}
