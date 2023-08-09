package com.Petching.petching.restDocs.v1.user;

import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.global.aws.s3.config.S3Configuration;
import com.Petching.petching.restDocs.global.helper.UserControllerTestHelper;
import com.Petching.petching.user.dto.UserPatchDto;
import com.Petching.petching.user.dto.UserResponseDto;
import com.Petching.petching.user.mapper.UserMapper;
import com.Petching.petching.restDocs.global.helper.StubData;
import com.google.gson.Gson;
import com.Petching.petching.user.controller.UserController;
import com.Petching.petching.user.dto.UserPostDto;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;



import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({
        SecurityConfiguration.class,
        S3Configuration.class
})
class UserControllerTest implements UserControllerTestHelper {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private SecurityConfiguration securityConfiguration;

    @DisplayName("Test - UserController - POST")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void postUserTest() throws Exception {
        // given
        UserPostDto post = (UserPostDto) StubData.MockUser.getRequestBody(HttpMethod.POST);
        String content = toJsonContent(post);

        User user = User.builder()
                .email(post.getEmail())
                .nickName(post.getNickName())
                .password(post.getPassword())
                .build();
        user.setUserId(1L);

        given(userService.savedUser(Mockito.any(UserPostDto.class))).willReturn(user);

        // when
        ResultActions actions = mockMvc.perform(
                postRequestBuilder(getUrl()+"sign-up", content)
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("sign-up/"))))
                .andDo(document("post-user",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                getDefaultMemberPostRequestDescriptors()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 User의 URI")
                        )
                ));
    }

    @DisplayName("Test - UserController - GET")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getUserTest() throws Exception {

        // given
        long userId = 1L;
        UserResponseDto.UserGetResponseDto response = StubData.MockUser.getSingleGetResponseBody();
        User user = StubData.MockUser.getSingleResultUser(userId);

        given(userService.findUser(Mockito.anyLong())).willReturn(user);
        given(userMapper.EntityToGetResponseDto(Mockito.any(User.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                getRequestBuilder(getURI(), userId)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(response.getEmail()))
                .andExpect(jsonPath("$.data.nickName").value(response.getNickName()))
                .andExpect(jsonPath("$.data.address").value(response.getAddress()))
                .andDo(
                        document("get-user",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        getMemberRequestPathParameterDescriptor()
                                ),
                                responseFields(
                                        getFullResponseDescriptors(
                                                getMemberGetResponseDescriptors(DataResponseType.SINGLE))
                                )
                        ));
    }

    @DisplayName("Test - UserController - PATCH")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void patchUserTest() throws Exception {
        // given
        long userId = 1L;
        UserPatchDto patch = (UserPatchDto) StubData.MockUser.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patch);

        UserResponseDto responseDto = StubData.MockUser.getSingleResponseBody();

        given(userMapper.userPatchDtoToUser(Mockito.any(UserPatchDto.class))).willReturn(User.builder().build());
        given(userService.updatedUser(Mockito.any(UserPatchDto.class))).willReturn(User.builder().build());
        given(userMapper.EntityToResponseDto(Mockito.any(User.class))).willReturn(responseDto);


        // when
        ResultActions actions = mockMvc.perform(
                patchRequestBuilder(getUrl(), content)
        );


        // then
        actions
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.userId").value(patch.getUserId()))
                .andExpect(jsonPath("$.data.nickName").value(patch.getNickName()))
                .andExpect(jsonPath("$.data.email").value(patch.getEmail()))
                .andExpect(jsonPath("$.data.address").value(patch.getAddress()))
                .andDo(document("patch-user",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                getDefaultMemberPatchRequestDescriptors()
                        ),
                        responseFields(
                                getFullResponseDescriptors(
                                        getDefaultMemberResponseDescriptors(DataResponseType.SINGLE))
                        )
                ));
    }

    @DisplayName("Test - UserController - DELETE")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void deleteUserTest() throws Exception {

        // given
        UserPostDto post = (UserPostDto) StubData.MockUser.getRequestBody(HttpMethod.POST);

        User user = User.builder()
                .email(post.getEmail())
                .nickName(post.getNickName())
                .password(post.getPassword())
                .build();
        user.setUserId(1L);

        given(userService.findSecurityContextHolderUserId()).willReturn(user.getUserId());
        given(userService.verifiedUser(Mockito.anyLong())).willReturn(user);


        // when
        ResultActions actions = mockMvc.perform(
                deleteRequestBuilder(getUrl())
        );


        // then
        actions
                .andExpect(status().is2xxSuccessful())
                .andDo(document("delete-user",
                        getRequestPreProcessor(),
                        getResponsePreProcessor()
                ));
    }




    /*
    * User 전체 조회 테스트 현재는 구현되지 않았기 때문에 주석처리
    *
    * */

    /*
    @DisplayName("Test - UserController - GET ALL")
    @Test
    public void getUsersTest() throws Exception {
        // given
        String page = "1";
        String size = "10";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);

        Page<User> users = StubData.MockUser.getMultiResultUser();
        List<UserResponseDto> responses = StubData.MockUser.getMultiResponseBody();

        given(userService.findUsers(Mockito.anyInt(), Mockito.anyInt())).willReturn(users);
        given(userMapper.usersToUserResponses(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions = mockMvc.perform(getRequestBuilder(getUrl(), queryParams));

        // then
        MvcResult result =
                actions
                        .andExpect(status().isOk())
                        .andDo(
                                document(
                                        "get-users",
                                        getRequestPreProcessor(),
                                        getResponsePreProcessor(),
                                        requestParameters(
                                                getDefaultRequestParameterDescriptors()
                                        ),
                                        responseFields(
                                                getFullPageResponseDescriptors(
                                                        getDefaultMemberResponseDescriptors(DataResponseType.LIST))

                                        )
                                )
                        )
                        .andReturn();

        List list = JsonPath.parse(result.getResponse().getContentAsString()).read("$.data");
        assertThat(list.size(), is(2));
    }
    */




}
