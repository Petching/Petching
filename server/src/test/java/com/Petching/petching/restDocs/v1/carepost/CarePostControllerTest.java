/*
package com.Petching.petching.restDocs.v1.carepost;

import com.Petching.petching.carepost.controller.CarePostController;
import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.carepost.mapper.CarePostMapper;
import com.Petching.petching.carepost.repository.CarePostRepository;
import com.Petching.petching.carepost.service.CarePostService;
import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.restDocs.global.helper.CarePostControllerTestHelper;
import com.Petching.petching.restDocs.global.helper.StubData;
import com.Petching.petching.tag.conditionTag.CarePost_ConditionTag;
import com.Petching.petching.tag.conditionTag.ConditionTag;
import com.Petching.petching.tag.locationTag.CarePost_LocationTag;
import com.Petching.petching.user.entity.User;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarePostController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(SecurityConfiguration.class)
public class CarePostControllerTest implements CarePostControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private CarePostService carePostService;

    @MockBean
    private CarePostMapper carePostMapper;

    @MockBean
    private CarePostRepository carePostRepository;


    @DisplayName("Test - CarePostController - POST")
    @Test
    public void postCarePostTest() throws Exception {

        // given
        CarePostDto.Post postDto = (CarePostDto.Post) StubData.MockCarePost.getRequestBody(HttpMethod.POST);

        CarePost carePost = CarePost.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .imgUrls(postDto.getImgUrls())
                .build();
        carePost.setPostId(1L);

        User user = StubData.MockUser.getSingleResultUser(postDto.getUserId());
        carePost.setUser(user);

        given(carePostService.savePost(Mockito.any(CarePostDto.Post.class))).willReturn(carePost);

        String content = toJsonContent(postDto);

        // when
        ResultActions actions = mockMvc.perform(
                postRequestBuilder(getUrl(), content)
        );


        // then
        actions.andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/post/"))))
                .andDo(document("post-carePost",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                getDefaultCarePostPostRequestDescriptors()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 User의 URI")
                        ))
                );
    }
}
*/
