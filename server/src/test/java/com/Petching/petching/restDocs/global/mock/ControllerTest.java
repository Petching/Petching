package com.Petching.petching.restDocs.global.mock;


import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.login.jwt.service.JwtService;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs // REST Doc 자동설정
@AutoConfigureMockMvc(addFilters = false) // CSRF 필터를 비활성화
@MockBean(JpaMetamodelMappingContext.class)
public class ControllerTest {
    @MockBean
    private JwtToken jwtToken;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private AuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @MockBean
    private AuthenticationFailureHandler authenticationFailureHandler;

    @MockBean
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public MockMvc mockMvc;

}
