package com.Petching.petching.restDocs.global.helper;

import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDescriptor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

public interface JwtControllerTestHelper extends ControllerTestHelper{
    String DEFAULT_URL = "/api/jwt";

    default String getUrl() {
        return DEFAULT_URL;
    }

    default RequestBuilder postRequestBuilder(String url,
                                              String refreshToken) {
        return  post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Refresh", refreshToken)
                .with(csrf());
    }

    default List<HeaderDescriptor> getRequestRefreshHeaderDescriptors() {
        return List.of(
                headerWithName("Refresh").description("Request User의 refresh token")
        );
    }

    default List<FieldDescriptor> getPostResponseJwtDescriptors() {

        return List.of(
                fieldWithPath("Access Token").description("Access Token")
        );
    }
}
