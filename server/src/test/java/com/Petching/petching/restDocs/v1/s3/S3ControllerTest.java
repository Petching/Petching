package com.Petching.petching.restDocs.v1.s3;


import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.global.api.s3.controller.S3Controller;
import com.Petching.petching.global.aws.s3.dto.S3FileDto;
import com.Petching.petching.global.aws.s3.service.S3Service;
import com.Petching.petching.restDocs.global.helper.S3ControllerTestHelper;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.FileInputStream;
import java.util.List;

import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(S3Controller.class)
@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfiguration.class)
public class S3ControllerTest implements S3ControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private S3Service s3Service;

    @DisplayName("Test - S3Controller - POST_UPLOAD")
    @Test
    public void s3UploadTest() throws Exception {

        // given
        String name = "files";
        String uploadTo = "boards";

        String contentType = "multipart/form-data";
        String path = "src/main/resources/static/temp";
        String originalFileName01 = "1.png";
        String originalFileName02 = "2.png";
        String originalFileName03 = "3.jpg";

        MockMultipartFile multipartFile01 = new MockMultipartFile(
                name,
                originalFileName01,
                contentType,
                (path + "/" + originalFileName01).getBytes()
        );

        MockMultipartFile multipartFile02 = new MockMultipartFile(
                name,
                originalFileName02,
                contentType,
                (path + "/" + originalFileName02).getBytes()
        );

        MockMultipartFile multipartFile03 = new MockMultipartFile(
                name,
                originalFileName03,
                contentType,
                (path + "/" + originalFileName03).getBytes()
        );

        S3FileDto s3FileDto1 =
        S3FileDto.builder()
                .originalFileName(originalFileName01)
                .uploadFileName("randomUUID_01.png")
                .uploadFilePath(uploadTo)
                .uploadFileUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/{upload-to}/{yyyy-mm-dd}-randomUUID_01.png")
                .build();
        S3FileDto s3FileDto2 =
                S3FileDto.builder()
                        .originalFileName(originalFileName02)
                        .uploadFileName("randomUUID_02.png")
                        .uploadFilePath(uploadTo)
                        .uploadFileUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/{upload-to}/{yyyy-mm-dd}-randomUUID_02.png")
                        .build();
        S3FileDto s3FileDto3 =
                S3FileDto.builder()
                        .originalFileName(originalFileName03)
                        .uploadFileName("randomUUID_03.jpg")
                        .uploadFilePath(uploadTo)
                        .uploadFileUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/{upload-to}/{yyyy-mm-dd}-randomUUID_03.jpg")
                        .build();

        List<S3FileDto> expectedResponse = List.of(
                s3FileDto1,s3FileDto2,s3FileDto3
        );


        given(s3Service.uploadFiles(Mockito.any(String.class), Mockito.any(List.class))).willReturn(expectedResponse);


        // when

        ResultActions resultActions =
        mockMvc.perform(
                RestDocumentationRequestBuilders.multipart("/api/s3/uploads")
                        .file(multipartFile01)
                        .file(multipartFile02)
                        .file(multipartFile03)
                        .param("uploadTo", uploadTo)
                        .accept(MediaType.APPLICATION_JSON)
        );


        // Then
        System.out.println( resultActions.andReturn().getResponse().getContentAsString());
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON+";charset=UTF-8"))
                .andExpect(jsonPath("$[0].originalFileName").value(originalFileName01))
                .andExpect(jsonPath("$[0].uploadFileName").isNotEmpty())
                .andExpect(jsonPath("$[0].uploadFilePath").isNotEmpty())
                .andExpect(jsonPath("$[0].uploadFileUrl").isNotEmpty())
                .andExpect(jsonPath("$[1].originalFileName").value(originalFileName02))
                .andExpect(jsonPath("$[1].uploadFileName").isNotEmpty())
                .andExpect(jsonPath("$[1].uploadFilePath").isNotEmpty())
                .andExpect(jsonPath("$[1].uploadFileUrl").isNotEmpty())
                .andExpect(jsonPath("$[2].originalFileName").value(originalFileName03))
                .andExpect(jsonPath("$[2].uploadFileName").isNotEmpty())
                .andExpect(jsonPath("$[2].uploadFilePath").isNotEmpty())
                .andExpect(jsonPath("$[2].uploadFileUrl").isNotEmpty())
                .andDo(document("post-s3-upload",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Content-Type").description("multipart/form-data")
                        ),
                        requestParameters(
                                parameterWithName("uploadTo").description("올릴 경로. e.g: boards")
                        ),
                        requestParts(
                                partWithName("files").description("올릴 파일(리스트)")
                        ),
                        responseFields(
                                fieldWithPath("[].originalFileName").type(JsonFieldType.STRING).description("업로드될 파일의 원래 이름"),
                                fieldWithPath("[].uploadFileName").type(JsonFieldType.STRING).description("업로드된 파일의 바뀐 이름"),
                                fieldWithPath("[].uploadFilePath").type(JsonFieldType.STRING).description("업로드된 파일의 경로"),
                                fieldWithPath("[].uploadFileUrl").type(JsonFieldType.STRING).description("업로드된 파일의 Url")
                )));



    }
}
