package com.Petching.petching.restDocs.v1.s3;


import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.global.api.s3.controller.S3Controller;
import com.Petching.petching.global.aws.s3.dto.S3FileDto;
import com.Petching.petching.global.aws.s3.service.S3Service;
import com.Petching.petching.restDocs.global.helper.S3ControllerTestHelper;
import com.Petching.petching.restDocs.global.helper.StubData;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
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
    public void postS3Test() throws Exception {

        // given
        // mock data setup
        String originalFileName01 = "1.png";
        String originalFileName02 = "2.png";
        String originalFileName03 = "3.jpg";
        List<S3FileDto> expectedResponse = StubData.MockS3.getMultiResponseBody();

        MockMultipartHttpServletRequestBuilder mmhsrb = (MockMultipartHttpServletRequestBuilder) StubData.MockS3.getRequestBody(HttpMethod.POST);


        given(s3Service.uploadFiles(Mockito.any(String.class), Mockito.any(List.class))).willReturn(expectedResponse);


        // when
        ResultActions resultActions =
        mockMvc.perform(
                mmhsrb
        );


        // Then
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
    @DisplayName("Test - S3Controller - DELETE_DELETE")
    @Test
    public void deleteS3Test() throws Exception {

        // given
        // mock data setup
        String result = "Success";

        List<S3FileDto> expectedResponse =
                StubData.MockS3.getMultiResponseBody();
        MultiValueMap<String, String> params =
                (MultiValueMap<String, String>) StubData.MockS3.getRequestBody(HttpMethod.DELETE);
        MockMultipartHttpServletRequestBuilder mmhsrb = (MockMultipartHttpServletRequestBuilder) StubData.MockS3.getRequestBody(HttpMethod.POST);


        given(s3Service.uploadFiles(Mockito.any(String.class), Mockito.any(List.class))).willReturn(expectedResponse);
        given(s3Service.deleteFile(Mockito.any(String.class), Mockito.any(String.class))).willReturn(result);


        // when
        ResultActions actions = mockMvc.perform(
                deleteRequestBuilder(getDeleteUri(),params)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value(result))
                .andDo(document("delete-s3-delete",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                List.of(parameterWithName("url").description("삭제할 파일 객체의 URL"),parameterWithName("from").description("삭제할 파일의 공간. e.g. boards"))
                        )
                ));


    }
}
