package com.Petching.petching.user.service;

import com.Petching.petching.global.exception.BusinessLoginException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.login.jwt.util.CustomAuthorityUtils;
import com.Petching.petching.user.dto.CheckDto;
import com.Petching.petching.user.dto.UserPatchDto;
import com.Petching.petching.user.dto.UserPostDto;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthorityUtils authorityUtils;

    public User savedUser (UserPostDto postDto){

        if (repository.findByEmail(postDto.getEmail()).isPresent()) {
            throw new BusinessLoginException(ExceptionCode.USER_EXIST);
        }
        if (repository.findByNickName(postDto.getNickName()).isPresent()) {
            throw new BusinessLoginException(ExceptionCode.NICKNAME_EXIST);
        }

        List<String> roles = authorityUtils.createRoles(postDto.getEmail());

        User user = User.builder()
                .nickName(postDto.getNickName())
                .email(postDto.getEmail())
                .password(passwordEncoder.encode(postDto.getPassword()))
                .roles(roles)
//                .profileImgUrl(postDto.getImgUrl())
                .build();

         return repository.save(user);
    }

    public User updatedUser (UserPatchDto patchDto) {
        User findUser = verifiedUser(patchDto.getUserId());

        Optional.ofNullable(patchDto.getNickName()).ifPresent(nickname -> findUser.updateNickName(nickname));
        Optional.ofNullable(patchDto.getAddress()).ifPresent(adr -> findUser.updateAddress(adr));
        Optional.ofNullable(patchDto.getPassword())
                .ifPresent(pw -> findUser.updatePassword(passwordEncoder.encode(pw)));

        return repository.save(findUser);
    }
    public User findUser (long userId) {
        User user = verifiedUser(userId);
        return user;
    }

    public String checkId (CheckDto dto) {
        if (repository.findByEmail(dto.getCheckEmail()).isPresent()) {
            return ExceptionCode.USER_EXIST.getMessage();
        }
        return "사용 가능한 이메일입니다.";
    }

    public String checkNick (CheckDto dto) {
        if (repository.findByNickName(dto.getCheckNickName()).isPresent()) {
            return ExceptionCode.NICKNAME_EXIST.getMessage();
        }
        return "사용 가능한 닉네임입니다.";
    }

    public void deletedUser (long userId) {
        User user = verifiedUser(userId);

        repository.delete(user);
    }

    public User verifiedUser (long userId) {
        Optional<User> optional = repository.findById(userId);
        User find = optional.orElseThrow(() -> new BusinessLoginException(ExceptionCode.MEMBER_NOT_FOUND));

        return find;
    }


    public User findUserByEmail (String email) {
        User user = verifiedUser(email);

        return user;
    }

    public User verifiedUser (String email) {

        Optional<User> optional = repository.findByEmail(email);
        User find = optional.orElseThrow(() -> new BusinessLoginException(ExceptionCode.MEMBER_NOT_FOUND));

        return find;
    }

}
