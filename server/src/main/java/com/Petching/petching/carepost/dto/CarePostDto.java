package com.Petching.petching.carepost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
public class CarePostDto {
    @Getter @Setter
    public static class Post {
        private Long userId;

        @NotBlank(message = "제목은 필수 입력 사항입니다.")
        @Size(max = 250, message = "제목은 250자를 넘을 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 사항입니다.")
        private String content;

        @NotBlank
        private String image;

        private Date startDate;
        private Date endDate;

        private List<String> conditionTags;
        private List<String> locationTags;
    }

    @Getter @Setter
    public static class Patch {
        private Long userId;

        @NotBlank(message = "제목은 필수 입력 사항입니다.")
        @Size(max = 250, message = "제목은 250자를 넘을 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 사항입니다.")
        private String content;

        @NotBlank
        private String image;

        private Date startDate;
        private Date endDate;

        private List<String> conditionTags;
        private List<String> locationTags;
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class Response {

        private String title;

        private String content;

        private String image;

        private Date startDate;
        private Date endDate;

        private List<String> conditionTags;
        private List<String> locationTags;
    }
}
