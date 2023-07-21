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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = S3Controller.class)
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

    @MockBean
    private SecurityConfiguration securityConfiguration;

    @DisplayName("Test - S3Controller - POST_UPLOAD")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void postS3Test() throws Exception {

        // given
        // mock data setup
        List<S3FileDto> expectedResponse = StubData.MockS3.getMultiResponseBody();

        MockMultipartHttpServletRequestBuilder mmhsrb = (MockMultipartHttpServletRequestBuilder) StubData.MockS3.getRequestBody(HttpMethod.POST);


        given(s3Service.uploadFiles(Mockito.any(String.class), Mockito.any(List.class))).willReturn(expectedResponse);


        // when
        ResultActions resultActions =
        mockMvc.perform(
                mmhsrb.with(csrf())
        );


        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON+";charset=UTF-8"))
                .andDo(document("post-s3-upload",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Content-Type").description("multipart/form-data")
                        ),
                        requestParameters(
                                parameterWithName("uploadTo").description("올릴 경로. e.g: boards"),
                                parameterWithName("_csrf").description("csrf")
                        ),
                        requestParts(
                                partWithName("files").description("올릴 파일(리스트)")
                        ),
                        responseFields(
                                getFullResponseDescriptors(
                                        getDefaultS3UploadResponseDescriptors(DataResponseType.SINGLE)
                                )
                )));



    }
    @DisplayName("Test - S3Controller - DELETE_DELETE")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void deleteS3Test() throws Exception {

        // given
        // mock data setup
        String result = "Success";

        List<S3FileDto> expectedResponse =
                StubData.MockS3.getMultiResponseBody();
        MultiValueMap<String, String> params =
                (MultiValueMap<String, String>) StubData.MockS3.getRequestBody(HttpMethod.DELETE);


        given(s3Service.uploadFiles(Mockito.any(String.class), Mockito.any(List.class))).willReturn(expectedResponse);
        given(s3Service.deleteFile(Mockito.any(String.class), Mockito.any(String.class))).willReturn(result);


        // when
        ResultActions actions = mockMvc.perform(
                deleteRequestBuilder(getDeleteUri(),params)
        );


        // then
        actions
                .andExpect(status().is2xxSuccessful())
                .andDo(document("delete-s3-delete",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                List.of(parameterWithName("url").description("삭제할 파일 객체의 URL"),parameterWithName("from").description("삭제할 파일의 공간. e.g. boards"),
                                parameterWithName("_csrf").description("csrf"))
                        )
                ));


    }
}
