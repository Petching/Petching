package com.Petching.petching.carepost.mapper;

import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.conditionTag.CarePost_ConditionTag;
import com.Petching.petching.conditionTag.ConditionTag;
import org.mapstruct.Mapper;

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

        List<CarePost_ConditionTag> postTags = carePost.getPostConditionTags();

        String title = null;
        String content = null;
        String image = null;
        Date startDate = null;
        Date endDate = null;

        title = carePost.getTitle();
        content = carePost.getContent();
        image = carePost.getImage();
        startDate = carePost.getStartDate();
        endDate = carePost.getEndDate();

        List<String> conditionTags = postTagDtoResponse(postTags);
        List<String> addressTags = null;

        CarePostDto.Response response = new CarePostDto.Response( title, content, image, startDate, endDate, conditionTags, addressTags );

        return response;
    }

    //Todo : null발생 (수동매핑 필요)
    List<CarePostDto.Response> carePostsToCarePostResponseDtos(List<CarePost> carePosts);

    default List<String> postTagDtoResponse (List<CarePost_ConditionTag> postTags) {
        List<String> tagName = postTags.stream().map(tag -> tag.getConditionTag().getBody())
                .collect(Collectors.toList());
        return tagName;
    }
}
