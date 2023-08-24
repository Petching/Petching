package com.Petching.petching.restDocs.global.helper;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.security.oauth2.jwt.JwsHeader.with;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.springframework.util.MultiValueMap;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface ControllerTestHelper<T> {
    default RequestBuilder postRequestBuilder(String url,
                                              String content) {
        return  post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "{AccessToken}")
                .content(content).with(csrf());
    }

    default RequestBuilder patchRequestBuilder(String url, long resourceId, String content) {
        return patch(url, resourceId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "{AccessToken}")
                .content(content).with(csrf());

    }

    default RequestBuilder patchRequestBuilder(String uri, String content) {
        return patch(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "{AccessToken}")
                .content(content).with(csrf());

    }

    default RequestBuilder getRequestBuilder(String url, long resourceId) {
        return get(url, resourceId)
                .header("Authorization", "{AccessToken}")
                .accept(MediaType.APPLICATION_JSON).with(csrf());
    }

    default RequestBuilder getRequestBuilder(String uri) {
        return get(uri)
                .header("Authorization", "{AccessToken}")
                .accept(MediaType.APPLICATION_JSON).with(csrf());
    }

    default RequestBuilder getRequestBuilder(String url, MultiValueMap<String, String> queryParams) {
        return get(url)
                .params(
                        queryParams
                )
                .header("Authorization", "{AccessToken}")
                .accept(MediaType.APPLICATION_JSON).with(csrf());
    }

    default RequestBuilder deleteRequestBuilder(String url, long resourceId) {
        return delete(url, resourceId)
                .header("Authorization", "{AccessToken}")
                .with(csrf());
    }

    default RequestBuilder deleteRequestBuilder(String uri) {
        return delete(uri)
                .header("Authorization", "{AccessToken}")
                .with(csrf());
    }


    default RequestBuilder deleteRequestBuilder(String url, MultiValueMap<String, String> queryParams) {
        return delete(url)
                .params(
                        queryParams
                )
                .header("Authorization", "{AccessToken}")
                .with(csrf());
    }
    default String toJsonContent(T t) {
        Gson gson = new Gson();
        String content = gson.toJson(t);
        return content;
    }
    default List<HeaderDescriptor> getDefaultRequestHeaderDescriptors() {
        return List.of(
                headerWithName("Authorization").description("Request User의 access token")
        );
    }
    default List<HeaderDescriptor> getOptionalRequestHeaderDescriptors() {
        return List.of(
                headerWithName("Authorization").description("Request User의 access token").optional()
        );
    }


    default String getDataParentPath(DataResponseType dataResponseType) {
        return dataResponseType == DataResponseType.SINGLE ? "data." : "data[].";
    }

    default List<FieldDescriptor> getFullResponseDescriptors(List<FieldDescriptor> dataResponseFieldDescriptors) {
        Stream<FieldDescriptor> defaultResponseDescriptors = getDefaultResponseDescriptors(JsonFieldType.OBJECT).stream();
        Stream<FieldDescriptor> dataResponseDescriptors = dataResponseFieldDescriptors.stream();
        return Stream.concat(defaultResponseDescriptors, dataResponseDescriptors)
                .collect(Collectors.toList());
    }

    default List<FieldDescriptor> getFullPageResponseDescriptors(List<FieldDescriptor> dataResponseFieldDescriptors) {
        Stream<FieldDescriptor> defaultResponseDescriptors = getDefaultResponseDescriptors(JsonFieldType.ARRAY).stream();
        Stream<FieldDescriptor> dataResponseDescriptors = dataResponseFieldDescriptors.stream();
        Stream<FieldDescriptor> pageResponseDescriptors = getPageResponseDescriptors().stream();

        Stream<FieldDescriptor> mergedStream =
                Stream.of(defaultResponseDescriptors, dataResponseDescriptors, pageResponseDescriptors)
                        .flatMap(descriptorStream -> descriptorStream);
        return mergedStream.collect(Collectors.toList());
    }

    default List<FieldDescriptor> getDefaultResponseDescriptors(JsonFieldType jsonFieldTypeForData) {
        return Arrays.asList(

        );
    }

    default List<FieldDescriptor> getPageResponseDescriptors() {
        return Arrays.asList(
                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보").optional(),
                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호").optional(),
                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈").optional(),
                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 건 수").optional(),
                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수").optional()
        );
    }

    default List<ParameterDescriptor> getDefaultRequestParameterDescriptors() {
        return List.of(
                parameterWithName("page").description("Page 번호").optional(),
                parameterWithName("size").description("Page Size").optional(),
                parameterWithName("_csrf").description("csrf").optional().ignored()
        );
    }
    enum DataResponseType {
        SINGLE, LIST
    }
}
