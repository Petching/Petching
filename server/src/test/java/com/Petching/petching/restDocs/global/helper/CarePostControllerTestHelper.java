package com.Petching.petching.restDocs.global.helper;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public interface CarePostControllerTestHelper extends ControllerTestHelper{

    String BOARD_URL = "/carepost";
    String RESOURCE_URI = "/{post-id}";

    default String getUrl() {
        return BOARD_URL;
    }

    default String getURI() {
        return BOARD_URL + RESOURCE_URI;
    }

    default List<FieldDescriptor> getDefaultCarePostPostRequestDescriptors() {

        return List.of(
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User 고유 식별자"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("imgUrls").type(JsonFieldType.STRING).description("올릴 사진").optional().type("배열"),
                fieldWithPath("conditionTags").type(JsonFieldType.STRING).description("상태 태그").optional().type("배열"),
                fieldWithPath("locationTags").type(JsonFieldType.STRING).description("장소 태그").optional().type("배열")
        );
    }
}
