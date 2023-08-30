package com.Petching.petching.restDocs.v1.question;

import com.Petching.petching.board.controller.BoardController;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.global.api.jwt.service.ExtractService;
import com.Petching.petching.global.aws.s3.config.S3Configuration;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.question.controller.QuestionController;
import com.Petching.petching.question.dto.QuestionDto;
import com.Petching.petching.question.entity.Question;
import com.Petching.petching.question.mapper.QuestionMapper;
import com.Petching.petching.question.service.QuestionService;
import com.Petching.petching.response.PageInfo;
import com.Petching.petching.restDocs.global.helper.QuestionControllerTestHelper;
import com.Petching.petching.restDocs.global.mock.ControllerTest;
import com.Petching.petching.restDocs.global.mock.StubData;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(QuestionController.class)
@Import({
        S3Configuration.class
})
public class QuestionControllerTest extends ControllerTest implements QuestionControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private QuestionMapper questionMapper;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private ExtractService extractService;


    @DisplayName("Test - QuestionController - POST")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void postQuestionTest() throws Exception {

        // given
        QuestionDto.Post postDto = (QuestionDto.Post) StubData.MockQuestion.getRequestBody(HttpMethod.POST);
        Question question = StubData.MockQuestion.getSingleResultQuestion();
        String content = toJsonContent(postDto);
        User user = StubData.MockUser.getSingleResultUser();

        given(extractService.getUserFromToken(Mockito.anyString())).willReturn(user);
        given(questionMapper.postDtoToEntity(Mockito.any(QuestionDto.Post.class))).willReturn(question);
        given(questionService.create(Mockito.any(Question.class))).willReturn(question);


        // when
        ResultActions actions = mockMvc.perform(
                postRequestBuilder(getUrl(), content)
        );


        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/questions/"))))
                .andDo(document("post-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getDefaultRequestHeaderDescriptors()
                        ),
                        requestFields(
                                getQuestionPostRequestDescriptors()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 Question 의 URI")
                        )
                ));
    }


    @DisplayName("Test - QuestionController - PATCH")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void patchQuestionTest() throws Exception {


        // given
        QuestionDto.Patch patchDto = (QuestionDto.Patch) StubData.MockQuestion.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patchDto);
        Question question = StubData.MockQuestion.getSingleResultQuestion(1L);
        User user = StubData.MockUser.getSingleResultUser();
        question.setUser(user);
        QuestionDto.Detail detail = StubData.MockQuestion.getSingleDetailBody();

        given(extractService.getUserFromToken(Mockito.anyString())).willReturn(user);
        given(questionMapper.patchDtoToEntity(Mockito.any(QuestionDto.Patch.class))).willReturn(question);
        given(questionService.update(Mockito.any(Question.class))).willReturn(question);
        given(questionMapper.entityToDetail(Mockito.any(Question.class))).willReturn(detail);



        // when
        ResultActions actions = mockMvc.perform(
                patchRequestBuilder(getURI(), 1L, content)
        );



        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("patch-question",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getDefaultRequestHeaderDescriptors()
                        ),
                        requestFields(
                                getQuestionPatchRequestDescriptors()
                        ),
                        responseFields(
                                getFullResponseDescriptors(
                                        getQuestionResponseDescriptors(DataResponseType.SINGLE)
                                )
                        )
                ));

    }



    @DisplayName("Test - QuestionController - DELETE")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void deleteQuestionTest() throws Exception {

        // given
        User user = StubData.MockUser.getSingleResultUser();
        Question question = StubData.MockQuestion.getSingleResultQuestion(1L);
        question.setUser(user);

        given(extractService.getUserFromToken(Mockito.anyString())).willReturn(user);


        // when
        ResultActions actions = mockMvc.perform(
                deleteRequestBuilder(getURI(), 1L)
        );


        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(
                        document("delete-question",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        getDefaultRequestHeaderDescriptors()
                                ),
                                pathParameters(
                                        getQuestionDeleteRequestPathParameterDescriptor()
                                )
                        )
                )
                .andReturn();
    }

    @DisplayName("Test - QuestionController - GET")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getQuestionTest() throws Exception {

        // given
        Question question = StubData.MockQuestion.getSingleResultQuestion();
        QuestionDto.Detail detail = StubData.MockQuestion.getSingleDetailBody();

        given(questionService.findQuestion(Mockito.anyLong())).willReturn(question);
        given(questionMapper.entityToDetail(Mockito.any(Question.class))).willReturn(detail);

        // when
        ResultActions actions = mockMvc.perform(
                getRequestBuilder(getURI(), question.getQuestionId())
        );


        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                        document("get-question",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        getQuestionRequestPathParameterDescriptor()
                                ),
                                responseFields(
                                        getFullResponseDescriptors(
                                                getDefaultQuestionDetailDescriptors(DataResponseType.SINGLE))
                                )
                        ));


    }


    @DisplayName("Test - QuestionController - GET - ALL")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getsQuestionTest() throws Exception {

        // given
        String page = "1";
        String size = "10";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);

        Page<Question> questionPage = StubData.MockQuestion.getMultiResultQuestion();
        List<QuestionDto.Response> responses = StubData.MockQuestion.getMultiResponseBody();

        given(questionService.findQuestions(Mockito.any(Pageable.class))).willReturn(questionPage);
        given(questionMapper.questionPageToQuestionResponseListDto(Mockito.any(Page.class))).willReturn(responses);

        // when
        ResultActions actions = mockMvc.perform(
                getRequestBuilder(getUrl(), queryParams)
        );


        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                        document("get-all-question",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestParameters(
                                        getDefaultRequestParameterDescriptors()
                                ),
                                responseFields(
                                        getFullPageResponseDescriptors(
                                                getQuestionResponseListDescriptors(DataResponseType.LIST))
                                )
                        )).andReturn();
    }


    @DisplayName("Test - QuestionController - GET -  User written")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getUserWrittenQuestionsTest() throws Exception {

        // given


        // when


        // then
    }


}
