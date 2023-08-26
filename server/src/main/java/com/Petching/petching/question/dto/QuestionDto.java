package com.Petching.petching.question.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class QuestionDto {

    @Getter
    @Builder
    public static class Post{

        @NotBlank
        private String title;

        @NotBlank
        private String content;

        @NotBlank
        private String questionType;

        @Nullable
        private List<String> imgUrls;
    }

    @Getter
    @Builder
    public static class Patch{

    }

    @Getter
    @Builder
    public static class Response{

    }


    public static class Detail {
    }
}
