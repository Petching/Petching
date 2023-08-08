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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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
                .profileImgUrl("https://s3.ap-northeast-2.amazonaws.com/petching.image/dog-5960092_1920.jpg")
                .build();

         return repository.save(user);
    }

    public User updatedUser (UserPatchDto patchDto) {
        User findUser = verifiedUser(patchDto.getUserId());

        Optional.ofNullable(patchDto.getNickName()).ifPresent(nickname -> findUser.updateNickName(nickname));
        Optional.ofNullable(patchDto.getAddress()).ifPresent(adr -> findUser.updateAddress(adr));
        Optional.ofNullable(patchDto.getPassword())
                .ifPresent(pw -> findUser.updatePassword(passwordEncoder.encode(pw)));
        Optional.ofNullable(patchDto.getProfileImgUrl()).ifPresent(img -> findUser.updateProfileImgUrl(img));

        return repository.save(findUser);
    }
    @Transactional(readOnly = true)
    public User findUser (long userId) {
        User user = verifiedUser(userId);
        if (findSecurityContextHolderUserId() != null) {
            User requestUser = verifiedUser(findSecurityContextHolderUserId());
            if (requestUser.getEmail().equals(user.getEmail())) {
                user.setUserDivision(true);
            }
        } else user.setUserDivision(false);

        return user;
    }

    public boolean checkId (CheckDto dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            return true;
        }
        return false;
    }

    public boolean checkNick (CheckDto dto) {
        if (repository.findByNickName(dto.getNickName()).isPresent()) {
            return true;
        }
        return false;
    }

    public boolean checkPassword (CheckDto dto) {
        User user = verifiedUser(findSecurityContextHolderUserId());
        return passwordEncoder.matches(dto.getPassword(),user.getPassword());
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


    public User updatedByBoardLike (User user) {

        return repository.save(user);
    }

    public Long findSecurityContextHolderUserId() {
        try {
            Map principal = (Map) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return (Long) principal.get("userId");
        } catch (Exception e) {
            return null;
        }
    }
}
