package com.Petching.petching.restDocs.global.helper;

import com.Petching.petching.restDocs.global.helper.ControllerTestHelper;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

public interface MyPetControllerTestHelper extends ControllerTestHelper {

    String MY_PET_URL = "/users/pets";
    String PET_RESOURCE_URI = "/{petId}";

    String USER_RESOURCE_URI = "/{user-id}";


    default String getUrl() {
        return MY_PET_URL;
    }

    default String getPetURI() {
        return MY_PET_URL + PET_RESOURCE_URI;
    }

    default String getUserURI() {
        return MY_PET_URL + USER_RESOURCE_URI;
    }


    default RequestBuilder postRequestBuilder(String url,
                                              String content) {
        return  post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "{AccessToken}")
                .with(csrf())
                .content(content);
    }


    default List<FieldDescriptor> getMyPetPostRequestDescriptors() {

        return List.of(
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("species").type(JsonFieldType.STRING).description("종(세부종)"),
                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("제목"),
                fieldWithPath("significant").type(JsonFieldType.STRING).description("특이").optional(),
                fieldWithPath("petImgUrl").type(JsonFieldType.STRING).description("Pet Image").optional()
        );
    }

    default List<FieldDescriptor> getMyPetPatchRequestDescriptors() {

        return List.of(
                fieldWithPath("myPetId").type(JsonFieldType.NUMBER).description("마이펫 식별자 ID"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("species").type(JsonFieldType.STRING).description("종(세부종)"),
                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("제목"),
                fieldWithPath("significant").type(JsonFieldType.STRING).description("특이").optional(),
                fieldWithPath("petImgUrl").type(JsonFieldType.STRING).description("Pet Image").optional()
        );
    }


    default List<FieldDescriptor> getMyPetResponseDescriptors(DataResponseType dataResponseType) {
        String parentPath = getDataParentPath(dataResponseType);

        return List.of(
                fieldWithPath(parentPath.concat("myPetId")).type(JsonFieldType.NUMBER).description("마이펫 식별자 ID"),
                fieldWithPath(parentPath.concat("name")).type(JsonFieldType.STRING).description("이름"),
                fieldWithPath(parentPath.concat("species")).type(JsonFieldType.STRING).description("종(세부종)"),
                fieldWithPath(parentPath.concat("gender")).type(JsonFieldType.STRING).description("성별"),
                fieldWithPath(parentPath.concat("age")).type(JsonFieldType.NUMBER).description("제목"),
                fieldWithPath(parentPath.concat("significant")).type(JsonFieldType.STRING).description("특이"),
                fieldWithPath(parentPath.concat("petImgUrl")).type(JsonFieldType.STRING).description("Pet Image")
        );
    }

    default List<FieldDescriptor> getMyPetResponseListDescriptors() {

        return List.of(
                fieldWithPath("[].myPetId").type(JsonFieldType.NUMBER).description("마이펫 식별자 ID"),
                fieldWithPath("[].name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("[].species").type(JsonFieldType.STRING).description("종(세부종)"),
                fieldWithPath("[].gender").type(JsonFieldType.STRING).description("성별"),
                fieldWithPath("[].age").type(JsonFieldType.NUMBER).description("제목"),
                fieldWithPath("[].significant").type(JsonFieldType.STRING).description("특이"),
                fieldWithPath("[].petImgUrl").type(JsonFieldType.STRING).description("Pet Image")
        );
    }


    default List<ParameterDescriptor> getMyPetGetAllRequestPathParameterDescriptor() {
        return Arrays.asList(
                parameterWithName("user-id").description("User 식별자 ID")
        );
    }


    default List<ParameterDescriptor> getMyPetDeleteRequestPathParameterDescriptor() {
        return Arrays.asList(
                parameterWithName("petId").description("마이펫 식별자 ID")
        );
    }


}
