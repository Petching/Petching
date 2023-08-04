package com.Petching.petching.restDocs.global.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

public interface BoardControllerTestHelper extends ControllerTestHelper{
    String BOARD_URL = "/boards";
    String RESOURCE_URI = "/{boardId}";

    default String getUrl() {
        return BOARD_URL;
    }

    default String getURI() {
        return BOARD_URL + RESOURCE_URI;
    }

    default List<ParameterDescriptor> getBoardRequestPathParameterDescriptor() {
        return Arrays.asList(
                parameterWithName("boardId").description("Board 식별자 ID")
        );
    }


    default List<FieldDescriptor> getDefaultBoardPostRequestDescriptors() {

        return List.of(
                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("imgUrls").type(JsonFieldType.ARRAY).description("첨부될 이미지").optional()
        );
    }

    default List<FieldDescriptor> getDefaultBoardDetailDescriptors(DataResponseType dataResponseType) {
        String parentPath = getDataParentPath(dataResponseType);
        return List.of(
                fieldWithPath(parentPath.concat("boardId")).type(JsonFieldType.NUMBER).description("Board 식별자 ID"),
                fieldWithPath(parentPath.concat("title")).type(JsonFieldType.STRING).description("제목"),
                fieldWithPath(parentPath.concat("content")).type(JsonFieldType.STRING).description("내용"),
                fieldWithPath(parentPath.concat("imgUrls")).type(JsonFieldType.ARRAY).description("첨부된 이미지"),
                fieldWithPath(parentPath.concat("likes")).type(JsonFieldType.NUMBER).description("좋아요 수"),
                fieldWithPath(parentPath.concat("profileImgUrl")).type(JsonFieldType.STRING).description("글쓴이의 프로필 사진 url"),
                fieldWithPath(parentPath.concat("nickName")).type(JsonFieldType.STRING).description("글쓴이의 닉네임"),
                fieldWithPath(parentPath.concat("createdAt")).type(JsonFieldType.STRING).description("글쓴 시간"),
                fieldWithPath(parentPath.concat("checkLike")).type(JsonFieldType.BOOLEAN).description("글을 보는 사람의 좋아요 여부"),
                fieldWithPath(parentPath.concat("comments")).type(JsonFieldType.ARRAY).description("댓글"),
                fieldWithPath(parentPath.concat("commentCount")).type(JsonFieldType.NUMBER).description("댓글 수")
        );
    }

    default RequestBuilder getRequestBuilder(String url, long resourceId, long userIdParameter) {
        return get(url, resourceId)
                .param("userId",String.valueOf(userIdParameter))
                .accept(MediaType.APPLICATION_JSON);
    }
    default RequestBuilder getRequestAllBuilder(String url, int page, int size, HttpHeaders httpHeader) {
        return get(url)
                .param("page",String.valueOf(page))
                .param("size",String.valueOf(size))
                .headers(httpHeader)
                .accept(MediaType.APPLICATION_JSON);
    }
    default RequestBuilder getBoardImgsRequestBuilder(String uri) {
        return get(uri)
                .accept(MediaType.APPLICATION_JSON);

    }

    default RequestBuilder postUpdateLikeRequestBuilder(String url,
                                              long boardId) {
        return  post(url+"/like",boardId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "{AccessToken}")
                .with(csrf());
    }

    default List<ParameterDescriptor> getBoardPostUpdateLikeRequestParameterDescriptors() {
        return List.of(
                parameterWithName("_csrf").description("csrf").ignored()

        );
    }

    default List<FieldDescriptor> getRandomImgUrlsFromBoardResponseDescriptors(DataResponseType dataResponseType) {

        String parentPath = getDataParentPath(dataResponseType);

        return List.of(
                fieldWithPath(parentPath.concat("ImgUrls")).type(JsonFieldType.ARRAY).description("Board에 있는 image를 random으로 최대 4개 가져옴")
        );
    }
    default List<ParameterDescriptor> getBoardRequestParameterDescriptors() {
        return List.of(
                parameterWithName("userId").description("USER 식별자 ID")
        );
    }

    default List<FieldDescriptor> getDefaultBoardPatchRequestDescriptors() {

        return List.of(
                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("imgUrls").type(JsonFieldType.ARRAY).description("첨부된 이미지").optional()
        );

    }

    default List<FieldDescriptor> getDefaultBoardResponseDescriptors(DataResponseType dataResponseType) {
        String parentPath = getDataParentPath(dataResponseType);
        return List.of(
                fieldWithPath(parentPath.concat("boardId")).type(JsonFieldType.NUMBER).description("Board 식별자 ID"),
                fieldWithPath(parentPath.concat("title")).type(JsonFieldType.STRING).description("제목"),
                fieldWithPath(parentPath.concat("imgUrls")).type(JsonFieldType.ARRAY).description("첨부된 이미지"),
                fieldWithPath(parentPath.concat("likes")).type(JsonFieldType.NUMBER).description("좋아요 수"),
                fieldWithPath(parentPath.concat("profileImgUrl")).type(JsonFieldType.STRING).description("글쓴이의 프로필 사진 url"),
                fieldWithPath(parentPath.concat("nickName")).type(JsonFieldType.STRING).description("글쓴이의 닉네임"),
                fieldWithPath(parentPath.concat("createdAt")).type(JsonFieldType.STRING).description("글쓴 시간"),
                fieldWithPath(parentPath.concat("checkLike")).type(JsonFieldType.BOOLEAN).description("글을 보는 사람의 좋아요 여부"),
                fieldWithPath(parentPath.concat("commentCount")).type(JsonFieldType.NUMBER).description("댓글 수")
        );
    }
}


