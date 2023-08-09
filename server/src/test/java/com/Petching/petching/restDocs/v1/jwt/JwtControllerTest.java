package com.Petching.petching.restDocs.v1.jwt;

import com.Petching.petching.board.controller.BoardController;
import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.global.api.jwt.controller.JwtController;
import com.Petching.petching.global.aws.s3.config.S3Configuration;
import com.Petching.petching.login.jwt.service.JwtService;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.restDocs.global.helper.JwtControllerTestHelper;
import com.Petching.petching.restDocs.global.helper.StubData;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.assertj.core.internal.bytebuddy.implementation.FixedValue.value;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(JwtController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import({
        SecurityConfiguration.class,
        S3Configuration.class
})
public class JwtControllerTest implements JwtControllerTestHelper {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private JwtToken jwtToken;

    @MockBean
    private UserService userService;

    @MockBean
    private SecurityConfiguration securityConfiguration;

    @DisplayName("Test - JwtController - POST")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void postRefreshTest() throws Exception {

        // given
        User user = StubData.MockUser.getSingleResultUser(1L);
        String accessToken = "{AccessToken}";
        String refreshToken = "{RefreshToken}";

        given(jwtToken.verifyTokenExpiration(Mockito.anyString())).willReturn(true);
        given(jwtToken.extractUserEmailFromToken(Mockito.anyString())).willReturn(user.getEmail());
        given(userService.findUserByEmail(Mockito.anyString())).willReturn(user);
        given(jwtToken.delegateAccessToken(Mockito.any(User.class))).willReturn(accessToken);


        // when
        ResultActions actions = mockMvc.perform(
            postRequestBuilder(getUrl(), refreshToken)
        );


        // then
        actions.andExpect(status().isCreated())
                .andDo(document(
                        "post-jwt",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getRequestRefreshHeaderDescriptors()
                        ),
                        responseBody()
                ));

    }


}
