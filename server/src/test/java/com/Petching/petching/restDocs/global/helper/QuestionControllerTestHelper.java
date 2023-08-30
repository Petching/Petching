package com.Petching.petching.restDocs.global.helper;

import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

public interface QuestionControllerTestHelper extends ControllerTestHelper{

    String QUESTION_URL = "/questions";
    String RESOURCE_URI = "/{questionId}";

    String USER_URI = "/{userId}/written";

    default String getUrl() {
        return QUESTION_URL;
    }

    default String getURI() {
        return QUESTION_URL + RESOURCE_URI;
    }

    default String getUserURI() {
        return QUESTION_URL + USER_URI;
    }

    default RequestBuilder patchRequestBuilder(String uri, long resourceId, String content) {
        return patch(uri, resourceId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "{AccessToken}")
                .content(content).with(csrf());

    }

    default List<FieldDescriptor> getQuestionPostRequestDescriptors() {

        return List.of(
                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("imgUrls").type(JsonFieldType.ARRAY).description("이미지(생략가능)").optional(),
                fieldWithPath("questionType").type(JsonFieldType.STRING).description("질문 유형")
        );
    }

    default List<FieldDescriptor> getQuestionPatchRequestDescriptors() {

        return List.of(
                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("imgUrls").type(JsonFieldType.ARRAY).description("이미지(생략가능)").optional(),
                fieldWithPath("questionType").type(JsonFieldType.STRING).description("질문 유형")
        );
    }


    default List<FieldDescriptor> getQuestionResponseDescriptors(DataResponseType dataResponseType) {

        String parentPath = getDataParentPath(dataResponseType);

        return List.of(
                fieldWithPath(parentPath.concat("questionId")).type(JsonFieldType.NUMBER).description("질문 식별자 ID"),
                fieldWithPath(parentPath.concat("title")).type(JsonFieldType.STRING).description("제목"),
                fieldWithPath(parentPath.concat("content")).type(JsonFieldType.STRING).description("내용"),
                fieldWithPath(parentPath.concat("imgUrls")).type(JsonFieldType.ARRAY).description("이미지(생략가능)"),
                fieldWithPath(parentPath.concat("questionType")).type(JsonFieldType.STRING).description("질문 유형")
        );

    }

    default List<FieldDescriptor> getDefaultQuestionDetailDescriptors(DataResponseType dataResponseType) {

        String parentPath = getDataParentPath(dataResponseType);

        return List.of(
                fieldWithPath(parentPath.concat("questionId")).type(JsonFieldType.NUMBER).description("질문 식별자 ID"),
                fieldWithPath(parentPath.concat("title")).type(JsonFieldType.STRING).description("제목"),
                fieldWithPath(parentPath.concat("content")).type(JsonFieldType.STRING).description("내용"),
                fieldWithPath(parentPath.concat("imgUrls")).type(JsonFieldType.ARRAY).description("이미지(생략가능)"),
                fieldWithPath(parentPath.concat("questionType")).type(JsonFieldType.STRING).description("질문 유형")
        );

    }

    default List<FieldDescriptor> getQuestionResponseListDescriptors(DataResponseType dataResponseType) {
        String parentPath = getDataParentPath(dataResponseType);
        return List.of(
                fieldWithPath(parentPath.concat("questionId")).type(JsonFieldType.NUMBER).description("질문 식별자 ID"),
                fieldWithPath(parentPath.concat("title")).type(JsonFieldType.STRING).description("제목"),
                fieldWithPath(parentPath.concat("content")).type(JsonFieldType.STRING).description("내용"),
                fieldWithPath(parentPath.concat("imgUrls")).type(JsonFieldType.ARRAY).description("이미지(생략가능)"),
                fieldWithPath(parentPath.concat("questionType")).type(JsonFieldType.STRING).description("질문 유형")
        );
    }


    default List<ParameterDescriptor> getQuestionDeleteRequestPathParameterDescriptor() {
        return Arrays.asList(
                parameterWithName("questionId").description("질문 식별자 ID")
        );
    }

    default List<ParameterDescriptor> getQuestionRequestPathParameterDescriptor() {
        return Arrays.asList(
                parameterWithName("questionId").description("질문 식별자 ID")
        );
    }
}
