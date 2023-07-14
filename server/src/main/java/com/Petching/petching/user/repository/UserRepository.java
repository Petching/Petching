package com.Petching.petching.user.repository;

import com.Petching.petching.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String email);
    Optional<User> findByNickName (String nickName);

//    Optional<User> findByRefreshToken (String refreshToken);

//    Optional<User> findBySo
}
