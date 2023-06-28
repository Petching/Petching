package com.Petching.petching.user.service;

import com.Petching.petching.exception.BusinessLoginException;
import com.Petching.petching.exception.ExceptionCode;
import com.Petching.petching.user.dto.UserPatchDto;
import com.Petching.petching.user.dto.UserPostDto;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User savedUser (UserPostDto postDto){

        if (repository.findByEmail(postDto.getEmail()).isPresent()) {
            throw new BusinessLoginException(ExceptionCode.USER_EXIST);
        }
        if (repository.findByNickName(postDto.getNickName()).isPresent()) {
            throw new BusinessLoginException(ExceptionCode.NICKNAME_EXIST);
        }
        User user = postDto.toEntity();

         return repository.save(user);
    }

    public User updatedUser (UserPatchDto patchDto) {
        User findUser = verifiedUser(patchDto.getUserId());

        Optional.ofNullable(patchDto.getEmail()).ifPresent(email -> findUser.updateEmail(email));
        Optional.ofNullable(patchDto.getNickname()).ifPresent(nickname -> findUser.updateNickName(nickname));
        Optional.ofNullable(patchDto.getAddress()).ifPresent(adr -> findUser.updateAddress(adr));
        Optional.ofNullable(patchDto.getPassword()).ifPresent(pw -> findUser.updatePassword(pw));

        return repository.save(findUser);
    }
    public User findUser (long userId) {
        User user = verifiedUser(userId);
        return user;
    }

    public void deletedUser (long userId) {
        User user = verifiedUser(userId);

        repository.delete(user);
    }

    public User verifiedUser (long userId) {
        Optional<User> optional = repository.findById(userId);
        User find = optional.orElseThrow(() -> new BusinessLoginException(ExceptionCode.USER_NOT_FOUND));

        return find;
    }
}
