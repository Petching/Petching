package com.Petching.petching.restDocs.v1.carepost;

import com.Petching.petching.carepost.controller.CarePostController;
import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.carepost.mapper.CarePostMapper;
import com.Petching.petching.carepost.repository.CarePostRepository;
import com.Petching.petching.carepost.service.CarePostService;
import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.global.aws.s3.config.S3Configuration;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.restDocs.global.helper.CarePostControllerTestHelper;
import com.Petching.petching.restDocs.global.helper.StubData;
import com.Petching.petching.tag.petSize.CarePost_PetSizeRepository;
import com.Petching.petching.tag.petSize.PetSizeRepository;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarePostController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import({
        SecurityConfiguration.class,
        S3Configuration.class
})
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

    @MockBean
    private PetSizeRepository petSizeRepository;

    @MockBean
    private CarePost_PetSizeRepository carePostPetSizeRepository;

    @MockBean
    private SecurityConfiguration securityConfiguration;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtToken jwtToken;

    @DisplayName("Test - CarePostController - POST")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void postCarePostTest() throws Exception {

        // given
        CarePostDto.Post postDto = (CarePostDto.Post) StubData.MockCarePost.getRequestBody(HttpMethod.POST);
        CarePost post = StubData.MockCarePost.getSingleResultCarePost();

        User user = StubData.MockUser.getSingleResultUser(1L);

        given(carePostMapper.carePostPostDtoToCarePost(Mockito.any(CarePostDto.Post.class))).willReturn(post);
        given(userService.findUser(Mockito.anyLong())).willReturn(user);
        given(carePostService.savePost(Mockito.any(CarePost.class))).willReturn(post);

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
                        requestHeaders(
                                getDefaultRequestHeaderDescriptors()
                        ),
                        requestFields(
                                getDefaultCarePostPostRequestDescriptors()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 User의 URI")
                        ))
                );
    }


    @DisplayName("Test - CarePostController - PATCH")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void patchCarePostTest() throws Exception{

        // given
        CarePost updatedPost = StubData.MockCarePost.getSingleResultCarePost();
        CarePostDto.Response responseDto = StubData.MockCarePost.getSingleResponseBody();
        CarePostDto.Patch patchDto = (CarePostDto.Patch) StubData.MockCarePost.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patchDto);
        User user = StubData.MockUser.getSingleResultUser(1L);

        given(userService.findUser(Mockito.anyLong())).willReturn(user);
        given(carePostMapper.carePostPatchDtoToCarePost(Mockito.any(CarePostDto.Patch.class))).willReturn(updatedPost);
        given(carePostService.updatePost(Mockito.any(CarePost.class), Mockito.anyLong())).willReturn(updatedPost);
        given(carePostMapper.carePostToCarePostResponseDto(Mockito.any(CarePost.class))).willReturn(responseDto);

        // when
        ResultActions actions = mockMvc.perform(
            patchRequestBuilder(getURI(), updatedPost.getPostId(),content)
        );


        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(patchDto.getTitle()))
                .andExpect(jsonPath("$.data.content").value(patchDto.getContent()))
                .andDo(document
                        ("patch-carePost",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        getCarePostGetRequestPathParameterDescriptor()
                                ),
                                requestHeaders(
                                        getDefaultRequestHeaderDescriptors()
                                ),
                                requestFields(
                                        getDefaultCarePostPatchRequestDescriptors()
                                ),
                                responseFields(
                                        getFullResponseDescriptors(
                                                getDefaultCarePostResponseDescriptors(DataResponseType.SINGLE))
                                )
                        )
                );

    }

    @DisplayName("Test - CarePostController - GET")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getCarePostTest() throws Exception{

        // given
        CarePost carePost = StubData.MockCarePost.getSingleResultCarePost();
        CarePostDto.Response responseDto = StubData.MockCarePost.getSingleResponseBody();

        given(carePostService.findPost(Mockito.anyLong())).willReturn(carePost);
        given(carePostMapper.carePostToCarePostResponseDto(Mockito.any(CarePost.class))).willReturn(responseDto);


        // when
        ResultActions actions = mockMvc.perform(
          getRequestBuilder(getURI(), carePost.getPostId())
        );


        // then
        actions.andExpect(status().isOk())
                .andDo(
                        document(
                                "get-carePost",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        getCarePostGetRequestPathParameterDescriptor()
                                ),
                                responseFields(
                                        getFullResponseDescriptors(
                                                getDefaultCarePostResponseDescriptors(DataResponseType.SINGLE))
                                )
                        )
                );

    }

    @DisplayName("Test - CarePostController - GET ALL")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getAllCarePostTest() throws Exception{

        // given
        String page = "1";
        String size = "10";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);

        Page<CarePost> carePostPage = StubData.MockCarePost.getMultiResultBoard();
        CarePostDto.Response responseDto1 = StubData.MockCarePost.getSingleResponseBody();

        given(carePostService.findAllPost(Mockito.any(Pageable.class))).willReturn(carePostPage);
        given(carePostMapper.carePostToCarePostResponseDto(Mockito.any(CarePost.class))).willReturn(responseDto1);


        // when
        ResultActions actions = mockMvc.perform(
                getRequestBuilder(getUrl(), queryParams)
        );



        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                        document("get-all-carePost",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                        requestParameters(
                                getDefaultRequestParameterDescriptors()
                        ),
                        responseFields(
                                getFullPageResponseDescriptors(
                                        getDefaultCarePostResponseDescriptors(DataResponseType.LIST))
                        )
                        ))
                .andReturn();

    }


    @DisplayName("Test - CarePostController - DELETE")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void deleteCarePostTest() throws Exception{

        // given
        Long userId = 1L;
        Long postId = 1L;

        CarePost carePost = StubData.MockCarePost.getSingleResultCarePost();
        User user = StubData.MockUser.getSingleResultUser(1L);

        given(userService.findUser(Mockito.anyLong())).willReturn(user);
        given(carePostService.existsPost(Mockito.anyLong())).willReturn(carePost);

        // when
        ResultActions actions = mockMvc.perform(
                deleteRequestBuilder(getURI(), postId)
        );



        // then
        actions.andExpect(status().is2xxSuccessful())
                .andDo(document("delete-carePost",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getDefaultRequestHeaderDescriptors()
                        ),
                        pathParameters(
                                getCarePostDeleteRequestPathParameterDescriptor()
                        )

                ));

    }

    @DisplayName("Test - CarePostController - GET /search")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void searchCarePostTest() throws Exception{

        // given
        Map<String,Integer> startDate = StubData.MockCarePost.getStubStartDate();
        Map<String,Integer> endDate = StubData.MockCarePost.getStubEndDate();
        String locationTag = "전남 광양시";

        List<CarePost> searchPostList = StubData.MockCarePost.getMultiResultBoard().toList();
        List<CarePostDto.Response> searchPostResponse = StubData.MockCarePost.getMultiResponseBody();

        given(carePostService.searchPost(Mockito.any(String.class), Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyInt())).willReturn(searchPostList);
        given(carePostMapper.carePostsToCarePostResponseDtos(Mockito.anyList())).willReturn(searchPostResponse);


        // when
        ResultActions actions = mockMvc.perform(
                getRequestBuilder(getUrl().concat("/search"), locationTag, startDate, endDate)
        );


        // then
        actions.andExpect(status().isOk())
                .andDo(document(
                        "get-carePost-search",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                getCarePostGetSearchRequestParamsDescriptors()
                        ),
                        responseFields(
                                getFullResponseDescriptors(
                                        getDefaultCarePostSearchResponseDescriptors(DataResponseType.LIST))

                        )
                        )
                );
    }


    @DisplayName("Test - CarePostController - GET - MyPage")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getMyPageTest() throws Exception {


        // given
        List<CarePost> carePostList = StubData.MockCarePost.getMultiResultBoard().toList();
        Page<CarePost> carePostPage = StubData.MockCarePost.getMultiResultBoard();
        List<CarePostDto.MyPage> carePostDtoList = StubData.MockCarePost.getCarePostMyPageListDto();

        given(carePostService.findUserCarePost(Mockito.anyLong())).willReturn(carePostList);
        given(carePostService.findUserCarePost(Mockito.any(Pageable.class), Mockito.anyList())).willReturn(carePostPage);
        given(carePostMapper.carePostsToCarePostMyPageDto(Mockito.anyList())).willReturn(carePostDtoList);

        Long userId = 1L;

        // when
        ResultActions actions = mockMvc.perform(
                getRequestBuilder(getUserURI(), userId)
        );



        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                        document("get-carePost-myPage",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        getCarePostMyPageRequestPathParameterDescriptor()
                                ),
                                requestParameters(
                                        getDefaultRequestParameterDescriptors()
                                ),
                                responseFields(
                                        getFullPageResponseDescriptors(
                                                getCarePostMyPageResponseDescriptors(DataResponseType.LIST)
                                        )
                                )

                        )
                ).andReturn();
    }




}
