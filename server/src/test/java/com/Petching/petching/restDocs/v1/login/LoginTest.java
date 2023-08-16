package com.Petching.petching.restDocs.v1.login;


import com.Petching.petching.board.controller.BoardController;
import com.Petching.petching.board.mapper.BoardMapper;
import com.Petching.petching.board.service.BoardService;
import com.Petching.petching.comment.mapper.CommentMapper;
import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.global.aws.s3.config.S3Configuration;
import com.Petching.petching.login.dto.LoginDto;
import com.Petching.petching.login.jwt.service.JwtService;
import com.Petching.petching.login.jwt.util.CustomAuthorityUtils;
import com.Petching.petching.login.oauth.OAuth2UserSuccessHandler;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.restDocs.global.helper.ControllerTestHelper;
import com.Petching.petching.restDocs.global.helper.LoginTestHelper;
import com.Petching.petching.restDocs.global.helper.StubData;
import com.Petching.petching.user.controller.UserController;
import com.Petching.petching.user.dto.UserPostDto;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.mapper.UserMapper;
import com.Petching.petching.user.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({
        SecurityConfiguration.class
})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import({
        S3Configuration.class,
        SecurityConfiguration.class
})
public class LoginTest implements LoginTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private JwtToken jwtToken;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CustomAuthorityUtils authorityUtils;

    @MockBean
    private OAuth2UserSuccessHandler oAuth2UserSuccessHandler;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityConfiguration securityConfiguration;


    /**
     * login test 구현 필요
     *
     */
    @DisplayName("Test - Login - POST")
    @Test
    @WithMockUser(username = "email@example.com", password = "exPassword",roles = "admin")
    public void loginTest() throws Exception {

        this.createUser();

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("email@example.com");
        loginDto.setPassword("exPassword");

        String content = toJsonContent(loginDto);

        ResultActions actions = mockMvc.perform(
                    postRequestBuilder(getUrl(), content)
        ).andDo(print());

        System.out.println("actions : " + actions.andReturn().getResponse());

    }

    private User createUser() throws Exception {
        UserPostDto postDto = (UserPostDto) StubData.MockUser.getRequestBody(HttpMethod.POST);
        return userService.savedUser(postDto);
    }
}
