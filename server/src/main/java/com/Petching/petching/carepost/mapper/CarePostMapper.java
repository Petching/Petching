package com.Petching.petching.carepost.mapper;

import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.tag.conditionTag.CarePost_ConditionTag;
import com.Petching.petching.tag.locationTag.CarePost_LocationTag;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
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
        String image = null;
        Date startDate = null;
        Date endDate = null;
        LocalDateTime createdAt = null;
        LocalDateTime modifiedAt = null;

        title = carePost.getTitle();
        content = carePost.getContent();
        image = carePost.getImage();
        startDate = carePost.getStartDate();
        endDate = carePost.getEndDate();
        createdAt = carePost.getCreatedAt();
        modifiedAt = carePost.getModifiedAt();

        List<String> conditionTags = postConditionTagDtoResponse(postConditionTags);
        List<String> locationTags = postLocationTagDtoResponse(postLocationTags);

        CarePostDto.Response response = new CarePostDto.Response( title, content, image, startDate, endDate, conditionTags, locationTags, createdAt, modifiedAt);

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
