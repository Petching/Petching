package com.Petching.petching.restDocs.global.helper;

import com.Petching.petching.user.dto.UserPatchDto;
import com.Petching.petching.user.dto.UserPostDto;
import com.Petching.petching.user.dto.UserResponseDto;
import com.Petching.petching.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class StubData {
    public static class MockUser {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, new UserPostDto("email@example.com","exPassword",
                    "exNickName"));
            stubRequestBody.put(HttpMethod.PATCH, new UserPatchDto(1, "email@example.com","exPassword", "exNickName","exAddress"));
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static UserResponseDto getSingleResponseBody() {
            return new UserResponseDto("exNickName", "email@example.com", "exAddress");
        }

        public static List<UserResponseDto> getMultiResponseBody() {
            return List.of(
                    new UserResponseDto("exNickName1", "email1@example.com", "exAddress1"),
                    new UserResponseDto("exNickName2", "email2@example.com", "exAddress2")
            );
        }

        public static User getSingleResultUser() {
            User user = User.builder()
                    .userId(1L)
                    .nickName("exNickName")
                    .password("exPassword")
                    .email("email@example.com")
                    .address("exAddress")
                    .build();

            return user;
        }

        public static Page<User> getMultiResultUser() {
            User user1 = User.builder()
                    .userId(1L)
                    .nickName("exNickName1")
                    .password("exPassword1")
                    .email("email1@example.com")
                    .address("exAddress1")
                    .build();

            User user2 = User.builder()
                    .userId(2L)
                    .nickName("exNickName2")
                    .password("exPassword2")
                    .email("email2@example.com")
                    .address("exAddress2")
                    .build();


            return new PageImpl<>(List.of(user1, user2),
                    PageRequest.of(0, 10, Sort.by("userId").descending()),
                    2);
        }

        public static User getSingleResultUser(long userId) {

            User user = User.builder()
                    .userId(1L)
                    .nickName("exNickName")
                    .password("exPassword")
                    .email("email@example.com")
                    .address("exAddress")
                    .build();

            return user;
        }

    }


}
