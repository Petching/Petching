package com.Petching.petching.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

public class PetDto {

    @Getter
    @Setter
    public static class Post {

        private long petId;
        private String name;

        private String breed;

        private String gender;
        private int age;

        private String weight;
        private Boolean isVaccination;
        private String memo;
    }

    @Getter @Setter
    public static class Patch {
        private long petId;
        private String name;

        private String breed;

        private String gender;
        private Integer age;
        private String weight;
        private Boolean isVaccination;
        private String memo;
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class Response {
        private String name;

        private String breed;

        private String gender;
        private int age;
        private String weight;
        private Boolean isVaccination;
        private String memo;
    }

}
