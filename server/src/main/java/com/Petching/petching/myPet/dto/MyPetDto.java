package com.Petching.petching.myPet.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class MyPetDto {
    @Getter
    public static class Post {
        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @NotBlank(message = "종(세부종) 입력해주세요.")
        private String species;

        @NotBlank(message = "성별을 입력해주세요.")
        private String gender;

        @NotNull(message = "나이를 입력해주세요.")
        private int age;

        private String significant;

        private String petImgUrl;

        @Builder
        public Post(String name, String species, String gender, int age, String significant, String petImgUrl) {
            this.name = name;
            this.species = species;
            this.gender = gender;
            this.age = age;
            this.significant = significant;
            this.petImgUrl = petImgUrl;
        }


    }

    @Getter
    public static class Patch {
        @Positive
        private long myPetId;
        private String name;
        private String species;
        private int age;
        private String significant;
        private String gender;
        private String petImgUrl;

        @Builder
        public Patch(long myPetId, String name, String species, int age, String significant, String gender, String petImgUrl) {
            this.myPetId = myPetId;
            this.name = name;
            this.species = species;
            this.age = age;
            this.significant = significant;
            this.gender = gender;
            this.petImgUrl = petImgUrl;
        }
    }

    @Getter @Setter
    public static class Response {
        private long myPetId;
        private String name;
        private String species;
        private String gender;
        private int age;
        private String significant;
        private String petImgUrl;

        @Builder
        public Response(long myPetId, String name, String species, String gender, int age, String significant, String petImgUrl) {
            this.myPetId = myPetId;
            this.name = name;
            this.species = species;
            this.gender = gender;
            this.age = age;
            this.significant = significant;
            this.petImgUrl = petImgUrl;
        }
    }
}
