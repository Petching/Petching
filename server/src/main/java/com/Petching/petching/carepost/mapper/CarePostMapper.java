package com.Petching.petching.carepost.mapper;

import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.tag.conditionTag.CarePost_ConditionTag;
import com.Petching.petching.tag.locationTag.CarePost_LocationTag;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CarePostMapper {
    CarePost carePostPostDtoToCarePost(CarePostDto.Post requestBody);

    CarePost carePostPatchDtoToCarePost(CarePostDto.Patch requestBody);

    default  CarePostDto.Response carePostToCarePostResponseDto(CarePost carePost) {
        if ( carePost == null ) {
            return null;
        }

        List<CarePost_ConditionTag> postConditionTags = carePost.getPostConditionTags();
        List<CarePost_LocationTag> postLocationTags = carePost.getPostLocationTags();

        String title = null;
        String content = null;
        List<String> imgUrls = new ArrayList<>();
        Date startDate = null;
        Date endDate = null;

        title = carePost.getTitle();
        content = carePost.getContent();
        imgUrls = carePost.getImgUrls();
        startDate = carePost.getStartDate();
        endDate = carePost.getEndDate();

        List<String> conditionTags = postConditionTagDtoResponse(postConditionTags);
        List<String> locationTags = postLocationTagDtoResponse(postLocationTags);

        CarePostDto.Response response =
                CarePostDto.Response.builder()
                        .title(title)
                        .imgUrls(imgUrls)
                        .startDate(startDate)
                        .endDate(endDate)
                        .conditionTags(conditionTags)
                        .locationTags(locationTags)
                        .build();

        return response;
    }

    //Todo : null발생 (수동매핑 필요)
    List<CarePostDto.Response> carePostsToCarePostResponseDtos(List<CarePost> carePosts);

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
