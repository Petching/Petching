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
import java.util.Map;

@Getter
public class CarePostDto {
    @Getter @Setter
    @Builder
    @AllArgsConstructor
    public static class Post {

        @NotBlank(message = "제목은 필수 입력 사항입니다.")
        @Size(max = 250, message = "제목은 250자를 넘을 수 없습니다.")
        private String title;
        @NotBlank(message = "내용은 필수 입력 사항입니다.")
        private String content;
        private List<String > imgUrls;
        private Map<String,Integer> startDate;
        private Map<String,Integer> endDate;
        @NotBlank(message = "필수 입력 사항입니다.")
        private String conditionTag;
        @NotBlank(message = "필수 입력 사항입니다.")
        private String locationTag;
        private String memo;
        private List<String> petSizes;
        public Post(){

        }
    }

    @Getter @Setter
    @Builder
    @AllArgsConstructor
    public static class Patch {

        @Size(max = 250, message = "제목은 250자를 넘을 수 없습니다.")
        private String title;
        private String content;
        private List<String > imgUrls;
        private Map<String,Integer> startDate;
        private Map<String,Integer> endDate;
        private String conditionTag;
        private String locationTag;
        private String memo;
        private List<String> petSizes;

        public Patch(){

        }
    }

    @Getter @Setter
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String title;
        private String content;
        private List<String> imgUrls;
        private String nickName;
        private String profileImgUrl;
        private Map<String,Integer> startDate;
        private Map<String,Integer> endDate;
        private String conditionTag;
        private String locationTag;
        private String memo;
        private List<String> petSizes;

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
        private Map<String,Integer> startDate;
        private Map<String,Integer> endDate;
        private String conditionTag;
        private String locationTag;
        private List<String> petSizes;

        public Detail(){

        }
    }

}
