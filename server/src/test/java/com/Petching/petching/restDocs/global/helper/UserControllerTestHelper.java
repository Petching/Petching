package com.Petching.petching.restDocs.global.helper;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public interface UserControllerTestHelper extends ControllerTestHelper {
    String MEMBER_URL = "/users/";
    String RESOURCE_URI = "{user-id}";

    default String getUrl() {
        return MEMBER_URL;
    }

    default String getURI() {
        return MEMBER_URL + RESOURCE_URI;
    }

    default List<ParameterDescriptor> getMemberRequestPathParameterDescriptor() {
        return Arrays.asList(parameterWithName("user-id").description("User 식별자 ID"));
    }

    default List<FieldDescriptor> getDefaultMemberPostRequestDescriptors() {

        return List.of(
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
        );
    }

    default List<FieldDescriptor> getDefaultMemberPatchRequestDescriptors() {

        return List.of(
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                fieldWithPath("address").type(JsonFieldType.STRING).description("주소")
        );
    }
    default List<FieldDescriptor> getDefaultMemberDeleteRequestDescriptors() {

        return List.of(
                fieldWithPath("user-id").type(JsonFieldType.NUMBER).description("회원 식별자").ignored()
        );
    }

    default List<FieldDescriptor> getDefaultMemberResponseDescriptors(DataResponseType dataResponseType) {
        String parentPath = getDataParentPath(dataResponseType);
        return List.of(
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("address").type(JsonFieldType.STRING).description("주소")
        );
    }
}
