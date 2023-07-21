package com.Petching.petching.carepost.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
    @Builder
    @AllArgsConstructor
    public static class Post {
        private Long userId;

        @NotBlank(message = "제목은 필수 입력 사항입니다.")
        @Size(max = 250, message = "제목은 250자를 넘을 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 사항입니다.")
        private String content;

        private List<String > imgUrls;

        private Date startDate;
        private Date endDate;

        private List<String> conditionTags;
        private List<String> locationTags;
        public Post(){

        }
    }

    @Getter @Setter
    @Builder
    @AllArgsConstructor
    public static class Patch {
        private Long userId;

        @NotBlank(message = "제목은 필수 입력 사항입니다.")
        @Size(max = 250, message = "제목은 250자를 넘을 수 없습니다.")
        private String title;

        @NotBlank(message = "내용은 필수 입력 사항입니다.")
        private String content;

        private List<String > imgUrls;

        private Date startDate;
        private Date endDate;

        private List<String> conditionTags;
        private List<String> locationTags;

        public Patch(){

        }
    }

    @Getter @Setter
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String title;

        private List<String > imgUrls;

        private String nickName;

        private String profileImgUrl;

        private Date startDate;
        private Date endDate;

        private List<String> conditionTags;
        private List<String> locationTags;

        public Response() {
        }
    }
    @Getter @Setter
    @AllArgsConstructor
    @Builder
    public static class Detail {

        private String title;

        private String content;

        private List<String > imgUrls;

        private String profileImgUrl;

        private String nickName;

        private Date startDate;
        private Date endDate;

        private List<String> conditionTags;
        private List<String> locationTags;

        public Detail(){

        }
    }

}
