package com.Petching.petching.room.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
public class RoomDto {

    @Getter
    @Setter
    public static class Post {
        private String nickName;
        private String title;
        private int userCurrentCount;
    }



    @Getter
    @Setter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class PostResponseDto {
        private long roomId;
        private String title;
        private String nickName;
        private int userCurrentCount;
    }

    @Getter
    @Setter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GetNewRoomResponseDtos {
        @Positive
        private long roomId;
        @NotBlank
        private String title;
        private int userCurrentCount;
    }



    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SearchResponseDto {
        private long roomId;
        private String title;
        private int userCurrentCount;
        @JsonProperty("created_at")
        private LocalDateTime createdAt;
    }


    @Getter
    @Setter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DeleteResponseDto {
        private long adminMemberId;
    }


    @Getter
    @Setter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class CheckTitle {
        private String title;
    }



    //Todo : 방장정보 DTO (사용X)
    @Getter
    @Setter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RoomAdminDto {
        @JsonProperty("admin_member_id")
        private long adminMemberId;
        @JsonProperty("admin_nickname")
        private String adminNickname;
        @JsonProperty("image_url")
        private String imageUrl;
    }

}
