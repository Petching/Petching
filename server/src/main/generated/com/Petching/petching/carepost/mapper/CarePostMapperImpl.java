package com.Petching.petching.carepost.mapper;

import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-19T14:08:17+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class CarePostMapperImpl implements CarePostMapper {

    @Override
    public CarePost carePostPostDtoToCarePost(CarePostDto.Post requestBody) {
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
        carePost.startDate( requestBody.getStartDate() );
        carePost.endDate( requestBody.getEndDate() );

        return carePost.build();
    }

    @Override
    public CarePost carePostPatchDtoToCarePost(CarePostDto.Patch requestBody) {
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
        carePost.startDate( requestBody.getStartDate() );
        carePost.endDate( requestBody.getEndDate() );

        return carePost.build();
    }

    @Override
    public List<CarePostDto.Response> carePostsToCarePostResponseDtos(List<CarePost> carePosts) {
        if ( carePosts == null ) {
            return null;
        }

        List<CarePostDto.Response> list = new ArrayList<CarePostDto.Response>( carePosts.size() );
        for ( CarePost carePost : carePosts ) {
            list.add( carePostToCarePostResponseDto( carePost ) );
        }

        return list;
    }
}
