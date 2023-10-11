package com.Petching.petching.carepost.mapper;

import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.tag.conditionTag.CarePost_ConditionTag;
import com.Petching.petching.tag.locationTag.CarePost_LocationTag;
import com.Petching.petching.tag.petSize.CarePost_PetSize;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CarePostMapper {
    default CarePost carePostPostDtoToCarePost(CarePostDto.Post requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        CarePost.CarePostBuilder carePost = CarePost.builder();

        carePost.title( requestBody.getTitle() );
        carePost.content( requestBody.getContent() );
        List<String> list = requestBody.getImgUrls();
        if ( list != null ) {
            carePost.imgUrls( new ArrayList<String>( list ) );
        }
        carePost.startDay( requestBody.getStartDate().get("day") );
        carePost.startMonth( requestBody.getStartDate().get("month") );
        carePost.startYear( requestBody.getStartDate().get("year") );
        carePost.endDay( requestBody.getEndDate().get("day") );
        carePost.endMonth( requestBody.getEndDate().get("month") );
        carePost.endYear( requestBody.getEndDate().get("year") );
        carePost.memo(requestBody.getMemo());
        carePost.conditionTag(requestBody.getConditionTag());
        carePost.locationTag(requestBody.getLocationTag());
        carePost.startDate(LocalDate.of(requestBody.getStartDate().get("year"),requestBody.getStartDate().get("month"),requestBody.getStartDate().get("day")));
        carePost.endDate(LocalDate.of(requestBody.getEndDate().get("year"),requestBody.getEndDate().get("month"),requestBody.getEndDate().get("day")));



        return carePost.build();
    }

    default CarePost carePostPatchDtoToCarePost(CarePostDto.Patch requestBody) {
        if ( requestBody == null ) {
            return null;
        }

        CarePost.CarePostBuilder carePost = CarePost.builder();

        carePost.title( requestBody.getTitle() );
        carePost.content( requestBody.getContent() );
        List<String> list = requestBody.getImgUrls();
        if ( list != null ) {
            carePost.imgUrls( new ArrayList<String>( list ) );
        }
        carePost.startDay( requestBody.getStartDate().get("day") );
        carePost.startMonth( requestBody.getStartDate().get("month") );
        carePost.startYear( requestBody.getStartDate().get("year") );
        carePost.endDay( requestBody.getEndDate().get("day") );
        carePost.endMonth( requestBody.getEndDate().get("month") );
        carePost.endYear( requestBody.getEndDate().get("year") );
        carePost.memo( requestBody.getMemo() );
        carePost.conditionTag( requestBody.getConditionTag() );
        carePost.locationTag( requestBody.getLocationTag() );
        carePost.startDate(LocalDate.of(requestBody.getStartDate().get("year"),requestBody.getStartDate().get("month"),requestBody.getStartDate().get("day")));
        carePost.endDate(LocalDate.of(requestBody.getEndDate().get("year"),requestBody.getEndDate().get("month"),requestBody.getEndDate().get("day")));

        return carePost.build();
    }

    default  CarePostDto.Response carePostToCarePostResponseDto(CarePost carePost) {
        if ( carePost == null ) {
            return null;
        }

        List<CarePost_PetSize> postPetSizes = carePost.getPostPetSizes();

        String title = null;
        String content = null;
        List<String> imgUrls = new ArrayList<>();
        Integer startDay = null;
        Integer startMonth = null;
        Integer startYear = null;
        Integer endDay = null;
        Integer endMonth = null;
        Integer endYear = null;
        String memo = null;
        String conditionTag = null;
        String locationTag = null;
        String nickName = null;
        String profileImgUrl = null;
        Long postId = null;
        Long userId = null;


        title = carePost.getTitle();
        content = carePost.getContent();
        imgUrls = carePost.getImgUrls();
        startDay = carePost.getStartDay();
        startMonth = carePost.getStartMonth();
        startYear = carePost.getStartYear();
        endDay = carePost.getEndDay();
        endMonth = carePost.getEndMonth();
        endYear = carePost.getEndYear();
        memo = carePost.getMemo();
        conditionTag = carePost.getConditionTag();
        locationTag = carePost.getLocationTag();
        nickName = carePost.getUser().getNickName();
        profileImgUrl = carePost.getUser().getProfileImgUrl();
        postId = carePost.getPostId();
        userId = carePost.getUser().getUserId();



        List<String> petSizes = postPetSizeDtoResponse(postPetSizes);

        // enddate 및 startdate 정의 및 초기화
        Map<String,Integer> startDate = new HashMap<>();
        Map<String,Integer> endDate = new HashMap<>();

        // enddate 및 statrdate 값 넣기
        startDate.put("day",startDay);
        startDate.put("month",startMonth);
        startDate.put("year",startYear);
        endDate.put("day",endDay);
        endDate.put("month",endMonth);
        endDate.put("year",endYear);


        CarePostDto.Response response =
                CarePostDto.Response.builder()
                        .postId(postId)
                        .userId(userId)
                        .title(title)
                        .content(content)
                        .imgUrls(imgUrls)
                        .startDate(startDate)
                        .endDate(endDate)
                        .conditionTag(conditionTag)
                        .locationTag(locationTag)
                        .nickName(nickName)
                        .profileImgUrl(profileImgUrl)
                        .memo(memo)
                        .petSizes(petSizes)
                        .build();

        return response;
    }

    //Todo : null발생 (수동매핑 필요)
    List<CarePostDto.Response> carePostsToCarePostResponseDtos(List<CarePost> carePosts);


    default List<String> postPetSizeDtoResponse (List<CarePost_PetSize> postPetSizes) {
        List<String> tagName = postPetSizes.stream().map(tag -> tag.getPetSize().getBody())
          .collect(Collectors.toList());
        return tagName;
    }

    default List<CarePostDto.MyPage> carePostsToCarePostMyPageDto (List<CarePost> carePosts) {
        return carePosts.stream()
                .map(carePost -> {
                    CarePostDto.MyPage myPage = new CarePostDto.MyPage(
                            carePost.getTitle(), postPetSizeDtoResponse(carePost.getPostPetSizes()), carePost.getImgUrls(),
                            carePost.getUser().getProfileImgUrl(), carePost.getUser().getNickName(),
                            carePost.getLocationTag()
                    );
                    return myPage;
                }).collect(Collectors.toList());
    }

    default List<String> postConditionTagDtoResponse (List<CarePost_ConditionTag> postConditionTags) {
        List<String> tagName = postConditionTags.stream().map(tag -> tag.getConditionTag().getBody())
                .collect(Collectors.toList());
        return tagName;
    }

    default List<String> postLocationTagDtoResponse (List<CarePost_LocationTag> postLocationTags) {
        List<String> tagName = postLocationTags.stream().map(tag -> tag.getLocationTag().getBody())
                .collect(Collectors.toList());
        return tagName;
    }


}
