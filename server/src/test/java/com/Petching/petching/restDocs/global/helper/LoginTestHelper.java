package com.Petching.petching.restDocs.global.helper;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

public interface LoginTestHelper extends ControllerTestHelper{
    String LOGIN_URL = "/users/login";

    default String getUrl() {
        return LOGIN_URL;
    }

    default String getSignUpUrl() {
        return "/users/sign-up";
    }

    default RequestBuilder postRequestBuilder(String url,
                                              String content) {
        return  post(url)
                .with(httpBasic("email@example.com","exPassword"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "{AccessToken}")
                .content(content).with(csrf());
    }

}
