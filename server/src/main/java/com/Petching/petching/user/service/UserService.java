package com.Petching.petching.user.service;

import com.Petching.petching.exception.BusinessLoginException;
import com.Petching.petching.exception.ExceptionCode;
import com.Petching.petching.user.dto.UserPostDto;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user = User.builder()
                        .email(postDto.getEmail())
                                .password(postDto.getPassword())
                                        .nickName(postDto.getNickName())
                                                .build();

         return repository.save(user);
    }
}
