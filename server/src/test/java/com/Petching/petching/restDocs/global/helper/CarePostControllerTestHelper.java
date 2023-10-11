package com.Petching.petching.restDocs.global.helper;

import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

public interface CarePostControllerTestHelper extends ControllerTestHelper{

    String BOARD_URL = "/careposts";
    String RESOURCE_URI = "/{post-id}";

    String USER_URI = "/my-page/{user-id}";

    default String getUrl() {
        return BOARD_URL;
    }

    default String getURI() {
        return BOARD_URL + RESOURCE_URI;
    }

    default String getUserURI() {
        return BOARD_URL + USER_URI;
    }


    default List<ParameterDescriptor> getCarePostGetRequestPathParameterDescriptor() {
        return Arrays.asList(
                parameterWithName("post-id").description("CarePost 식별자 ID")
        );
    }
    default List<ParameterDescriptor> getCarePostDeleteRequestPathParameterDescriptor() {
        return Arrays.asList(
                parameterWithName("post-id").description("CarePost 식별자 ID")
        );
    }

    default List<ParameterDescriptor> getCarePostMyPageRequestPathParameterDescriptor() {
        return Arrays.asList(
                parameterWithName("user-id").description("User 식별자 ID")
        );
    }


    default RequestBuilder getRequestBuilder(String url, String locationTag, Map<String, Integer> startDate, Map<String, Integer> endDate ) {

        String startDay = String.valueOf(startDate.get("day"));
        String startMonth = String.valueOf(startDate.get("month"));
        String startYear = String.valueOf(startDate.get("year"));

        String endDay = String.valueOf(endDate.get("day"));
        String endMonth = String.valueOf(endDate.get("month"));
        String endYear = String.valueOf(endDate.get("year"));

        return get(url)
                .param("locationTag", locationTag)
                .param("startDate.day", startDay)
                .param("startDate.month",startMonth)
                .param("startDate.year", startYear)
                .param("endDate.day", endDay)
                .param("endDate.month", endMonth)
                .param("endDate.year", endYear)
                .header("Authorization", "{AccessToken}")
                .accept(MediaType.APPLICATION_JSON).with(csrf());
    }

    default RequestBuilder deleteRequestBuilder(String url, long resourceId, long userId) {

        return delete(url, resourceId, userId)
                .header("Authorization", "{AccessToken}")
                .with(csrf());
    }

    default List<FieldDescriptor> getDefaultCarePostPostRequestDescriptors() {

        return List.of(
                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("imgUrls[]").type(JsonFieldType.ARRAY).description("올릴 사진").optional(),
                fieldWithPath("startDate").type(JsonFieldType.OBJECT).description("시작 날짜"),
                fieldWithPath("startDate.day").type(JsonFieldType.NUMBER).description("시작 날짜/일"),
                fieldWithPath("startDate.month").type(JsonFieldType.NUMBER).description("시작 날짜/월"),
                fieldWithPath("startDate.year").type(JsonFieldType.NUMBER).description("시작 날짜/년"),
                fieldWithPath("endDate").type(JsonFieldType.OBJECT).description("종료 날짜"),
                fieldWithPath("endDate.day").type(JsonFieldType.NUMBER).description("종료 날짜/일"),
                fieldWithPath("endDate.month").type(JsonFieldType.NUMBER).description("종료 날짜/월"),
                fieldWithPath("endDate.year").type(JsonFieldType.NUMBER).description("종료 날짜/년"),
                fieldWithPath("conditionTag").type(JsonFieldType.STRING).description("상태 태그"),
                fieldWithPath("locationTag").type(JsonFieldType.STRING).description("장소 태그"),
                fieldWithPath("petSizes[]").type(JsonFieldType.ARRAY).description("반려동물 크기"),
                fieldWithPath("memo").type(JsonFieldType.STRING).description("특이사항")
        );
    }


    default List<FieldDescriptor> getDefaultCarePostPatchRequestDescriptors() {

        return List.of(
                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("imgUrls[]").type(JsonFieldType.ARRAY).description("올릴 사진").optional(),
                fieldWithPath("startDate").type(JsonFieldType.OBJECT).description("시작 날짜"),
                fieldWithPath("startDate.day").type(JsonFieldType.NUMBER).description("시작 날짜/일"),
                fieldWithPath("startDate.month").type(JsonFieldType.NUMBER).description("시작 날짜/월"),
                fieldWithPath("startDate.year").type(JsonFieldType.NUMBER).description("시작 날짜/년"),
                fieldWithPath("endDate").type(JsonFieldType.OBJECT).description("종료 날짜"),
                fieldWithPath("endDate.day").type(JsonFieldType.NUMBER).description("종료 날짜/일"),
                fieldWithPath("endDate.month").type(JsonFieldType.NUMBER).description("종료 날짜/월"),
                fieldWithPath("endDate.year").type(JsonFieldType.NUMBER).description("종료 날짜/년"),
                fieldWithPath("conditionTag").type(JsonFieldType.STRING).description("상태 태그"),
                fieldWithPath("locationTag").type(JsonFieldType.STRING).description("장소 태그"),
                fieldWithPath("petSizes[]").type(JsonFieldType.ARRAY).description("반려동물 크기"),
                fieldWithPath("memo").type(JsonFieldType.STRING).description("특이사항")
        );
    }


    default List<FieldDescriptor> getDefaultCarePostResponseDescriptors(DataResponseType dataResponseType) {

        String parentPath = getDataParentPath(dataResponseType);

        return List.of(
                fieldWithPath(parentPath.concat("postId")).type(JsonFieldType.NUMBER).description("게시글 식별자 ID"),
                fieldWithPath(parentPath.concat("userId")).type(JsonFieldType.NUMBER).description("User 식별자 ID"),
                fieldWithPath(parentPath.concat("title")).type(JsonFieldType.STRING).description("제목"),
                fieldWithPath(parentPath.concat("content")).type(JsonFieldType.STRING).description("내용"),
                fieldWithPath(parentPath.concat("imgUrls[]")).type(JsonFieldType.ARRAY).description("올릴 사진").optional(),
                fieldWithPath(parentPath.concat("profileImgUrl")).type(JsonFieldType.STRING).description("작성자의 프로필 사진"),
                fieldWithPath(parentPath.concat("nickName")).type(JsonFieldType.STRING).description("작성자의 닉네임"),
                fieldWithPath(parentPath.concat("startDate")).type(JsonFieldType.OBJECT).description("시작 날짜"),
                fieldWithPath(parentPath.concat("startDate.day")).type(JsonFieldType.NUMBER).description("시작 날짜/일"),
                fieldWithPath(parentPath.concat("startDate.month")).type(JsonFieldType.NUMBER).description("시작 날짜/월"),
                fieldWithPath(parentPath.concat("startDate.year")).type(JsonFieldType.NUMBER).description("시작 날짜/년"),
                fieldWithPath(parentPath.concat("endDate")).type(JsonFieldType.OBJECT).description("종료 날짜"),
                fieldWithPath(parentPath.concat("endDate.day")).type(JsonFieldType.NUMBER).description("종료 날짜/일"),
                fieldWithPath(parentPath.concat("endDate.month")).type(JsonFieldType.NUMBER).description("종료 날짜/월"),
                fieldWithPath(parentPath.concat("endDate.year")).type(JsonFieldType.NUMBER).description("종료 날짜/년"),
                fieldWithPath(parentPath.concat("conditionTag")).type(JsonFieldType.STRING).description("상태 태그"),
                fieldWithPath(parentPath.concat("locationTag")).type(JsonFieldType.STRING).description("장소 태그"),
                fieldWithPath(parentPath.concat("petSizes[]")).type(JsonFieldType.ARRAY).description("반려동물 크기"),
                fieldWithPath(parentPath.concat("memo")).type(JsonFieldType.STRING).description("특이사항")
        );
    }

    default List<FieldDescriptor> getDefaultCarePostSearchResponseDescriptors(DataResponseType dataResponseType) {

        String parentPath = getDataParentPath(dataResponseType);

        return List.of(
                fieldWithPath(parentPath.concat("postId")).type(JsonFieldType.NUMBER).description("게시글 식별자 ID"),
                fieldWithPath(parentPath.concat("userId")).type(JsonFieldType.NUMBER).description("User 식별자 ID"),
                fieldWithPath(parentPath.concat("title")).type(JsonFieldType.STRING).description("제목"),
                fieldWithPath(parentPath.concat("content")).type(JsonFieldType.STRING).description("내용"),
                fieldWithPath(parentPath.concat("imgUrls[]")).type(JsonFieldType.ARRAY).description("올릴 사진").optional(),
                fieldWithPath(parentPath.concat("profileImgUrl")).type(JsonFieldType.STRING).description("작성자의 프로필 사진"),
                fieldWithPath(parentPath.concat("nickName")).type(JsonFieldType.STRING).description("작성자의 닉네임"),
                fieldWithPath(parentPath.concat("startDate")).type(JsonFieldType.OBJECT).description("시작 날짜"),
                fieldWithPath(parentPath.concat("startDate.day")).type(JsonFieldType.NUMBER).description("시작 날짜/일"),
                fieldWithPath(parentPath.concat("startDate.month")).type(JsonFieldType.NUMBER).description("시작 날짜/월"),
                fieldWithPath(parentPath.concat("startDate.year")).type(JsonFieldType.NUMBER).description("시작 날짜/년"),
                fieldWithPath(parentPath.concat("endDate")).type(JsonFieldType.OBJECT).description("종료 날짜"),
                fieldWithPath(parentPath.concat("endDate.day")).type(JsonFieldType.NUMBER).description("종료 날짜/일"),
                fieldWithPath(parentPath.concat("endDate.month")).type(JsonFieldType.NUMBER).description("종료 날짜/월"),
                fieldWithPath(parentPath.concat("endDate.year")).type(JsonFieldType.NUMBER).description("종료 날짜/년"),
                fieldWithPath(parentPath.concat("conditionTag")).type(JsonFieldType.STRING).description("상태 태그"),
                fieldWithPath(parentPath.concat("locationTag")).type(JsonFieldType.STRING).description("장소 태그"),
                fieldWithPath(parentPath.concat("petSizes[]")).type(JsonFieldType.ARRAY).description("반려동물 크기"),
                fieldWithPath(parentPath.concat("memo")).type(JsonFieldType.STRING).description("특이사항")

        );
    }


    default List<ParameterDescriptor> getCarePostGetSearchRequestParamsDescriptors() {


        return List.of(
                parameterWithName("_csrf").ignored(),
                parameterWithName("locationTag").description("장소 태그"),
                parameterWithName("startDate.day").description("시작 날짜/일"),
                parameterWithName("startDate.month").description("시작 날짜/월"),
                parameterWithName("startDate.year").description("시작 날짜/년"),
                parameterWithName("endDate.day").description("시작 날짜/일"),
                parameterWithName("endDate.month").description("시작 날짜/월"),
                parameterWithName("endDate.year").description("시작 날짜/년")
        );
    }


    default List<FieldDescriptor> getCarePostMyPageResponseDescriptors(DataResponseType dataResponseType) {

        String parentPath = getDataParentPath(dataResponseType);

        return List.of(
                fieldWithPath(parentPath.concat("title")).type(JsonFieldType.STRING).description("제목"),
                fieldWithPath(parentPath.concat("imgUrls[]")).type(JsonFieldType.ARRAY).description("올릴 사진"),
                fieldWithPath(parentPath.concat("petSizes[]")).type(JsonFieldType.ARRAY).description("반려동물 크기"),
                fieldWithPath(parentPath.concat("profileImgUrl")).type(JsonFieldType.STRING).description("작성자의 프로필 사진"),
                fieldWithPath(parentPath.concat("nickName")).type(JsonFieldType.STRING).description("작성자의 닉네임"),
                fieldWithPath(parentPath.concat("locationTag")).type(JsonFieldType.STRING).description("장소 태그")

        );
    }

    default List<FieldDescriptor> getFullResponseDescriptors(List dataResponseFieldDescriptors) {
        Stream<FieldDescriptor> defaultResponseDescriptors = getDefaultResponseDescriptors(JsonFieldType.ARRAY).stream();
        Stream<FieldDescriptor> dataResponseDescriptors = dataResponseFieldDescriptors.stream();

        Stream<FieldDescriptor> mergedStream =
                Stream.of(defaultResponseDescriptors, dataResponseDescriptors)
                        .flatMap(descriptorStream -> descriptorStream);

        return mergedStream.collect(Collectors.toList());
    }
}
