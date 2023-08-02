package com.Petching.petching.restDocs.global.helper;

import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

public interface CommentControllerTestHelper extends ControllerTestHelper{

    String COMMENT_BOARD_URL = "/boards/{boardId}";
    String RESOURCE_URI = "/{commentId}";

    default String getUrl() {
        return COMMENT_BOARD_URL;
    }

    default String getURI() {
        return COMMENT_BOARD_URL + RESOURCE_URI;
    }


    default List<ParameterDescriptor> getCommentPostRequestPathParameterDescriptor() {
        return Arrays.asList(
                parameterWithName("boardId").description("Board 식별자 ID")
        );
    }

    default List<ParameterDescriptor> getCommentRequestPathParameterDescriptor() {
        return Arrays.asList(
                parameterWithName("boardId").description("Board 식별자 ID"),
                parameterWithName("commentId").description("Comment 식별자 ID")
        );
    }

    default List<ParameterDescriptor> getCommentsRequestPathParameterDescriptor() {
        return Arrays.asList(
                parameterWithName("boardId").description("Board 식별자 ID")
        );
    }

    default RequestBuilder postCommentRequestBuilder(String url,
                                              String content, long boardId) {
        return  post(url, boardId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "{AccessToken}")
                .content(content).with(csrf());
    }


    default RequestBuilder getCommentRequestBuilder(String url, long boardId, long commentId) {
        return get(url, boardId, commentId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf());

    }

    default RequestBuilder getCommentsRequestBuilder(String url, long boardId) {

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", String.valueOf(1));
        queryParams.add("size", String.valueOf(10));

        return get(url+"/comments", boardId)
                .params(queryParams)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf());

    }

    default RequestBuilder patchCommentRequestBuilder(String url, long boardId, long commentId,  String content) {
        return patch(url, boardId, commentId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content).with(csrf());

    }

    default RequestBuilder deleteCommentRequestBuilder(String url, long boardId, long commentId) {
        return delete(url, boardId, commentId)
                .with(csrf());
    }

    default List<FieldDescriptor> getCommentPostRequestDescriptors() {

        return List.of(
                fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User 고유 식별자")
        );

    }


    default List<FieldDescriptor> getCommentPatchRequestDescriptors() {

        return List.of(
                fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
        );
    }

    default List<FieldDescriptor> getCommentResponseDescriptors(DataResponseType dataResponseType) {

        String parentPath = getDataParentPath(dataResponseType);

        return List.of(
                fieldWithPath(parentPath.concat("commentId")).type(JsonFieldType.NUMBER).description("댓글 식별자 ID"),
                fieldWithPath(parentPath.concat("content")).type(JsonFieldType.STRING).description("댓글 내용"),
                fieldWithPath(parentPath.concat("boardId")).type(JsonFieldType.NUMBER).description("본문 식별자 ID"),
                fieldWithPath(parentPath.concat("createdAt")).type(JsonFieldType.STRING).description("글쓴 시간"),
                fieldWithPath(parentPath.concat("nickName")).type(JsonFieldType.STRING).description("글쓴이의 닉네임"),
                fieldWithPath(parentPath.concat("profileImgUrl")).type(JsonFieldType.STRING).description("글쓴이의 프로필 사진 url")
        );
    }


    default List<FieldDescriptor> getCommentsResponseDescriptors(DataResponseType dataResponseType) {
        String parentPath = getDataParentPath(dataResponseType);

        return List.of(
                fieldWithPath(parentPath.concat("commentId")).type(JsonFieldType.NUMBER).description("Comment 식별자 ID"),
                fieldWithPath(parentPath.concat("content")).type(JsonFieldType.STRING).description("댓글 내용"),
                fieldWithPath(parentPath.concat("boardId")).type(JsonFieldType.NUMBER).description("본문 식별자 ID"),
                fieldWithPath(parentPath.concat("profileImgUrl")).type(JsonFieldType.STRING).description("글쓴이의 프로필 사진 url"),
                fieldWithPath(parentPath.concat("nickName")).type(JsonFieldType.STRING).description("글쓴이의 닉네임"),
                fieldWithPath(parentPath.concat("createdAt")).type(JsonFieldType.STRING).description("글쓴 시간")
        );
    }
}
