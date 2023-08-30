package com.Petching.petching.restDocs.v1.comment;

import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.service.BoardService;
import com.Petching.petching.comment.controller.CommentController;
import com.Petching.petching.comment.dto.CommentDto;
import com.Petching.petching.comment.entity.Comment;
import com.Petching.petching.comment.mapper.CommentMapper;
import com.Petching.petching.comment.service.CommentService;
import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.global.aws.s3.config.S3Configuration;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.restDocs.global.helper.CommentControllerTestHelper;
import com.Petching.petching.restDocs.global.mock.StubData;
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


@WebMvcTest(CommentController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import({
        SecurityConfiguration.class,
        S3Configuration.class
})
public class CommentControllerTest implements CommentControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private SecurityConfiguration securityConfiguration;


    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentMapper mapper;
    @MockBean
    private BoardService boardService;
    @MockBean
    private UserService userService;

    @MockBean
    private JwtToken jwtToken;

    @DisplayName("Test - CommentController - POST")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void postCommentTest() throws Exception {

        // given
        CommentDto.Post postDto = (CommentDto.Post) StubData.MockComment.getRequestBody(HttpMethod.POST);
        given(mapper.commentPostDtoToComment(Mockito.any(CommentDto.Post.class))).willReturn(Comment.builder().build());

        Comment comment = Comment.builder()
                .content(postDto.getContent())
                .commentId(1L)
                .build();

        Board board = StubData.MockBoard.getSingleResultBoard();
        User user = StubData.MockUser.getSingleResultUser();
        comment.setUser(user);
        comment.setBoard(board);
        board.addComment(comment);
        board.setLikes(board.getLikes());

        given(userService.findUser(Mockito.anyLong())).willReturn(user);
        given(boardService.findBoardByMK(Mockito.anyLong())).willReturn(board);

        given(commentService.createComment(Mockito.any(Comment.class))).willReturn(comment);
        String content = toJsonContent(postDto);



        // when
        ResultActions actions = mockMvc.perform(
                postCommentRequestBuilder(getUrl(), content, board.getBoardId())
        );



        // then
        actions.andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/"+ board.getBoardId()+"/"))))
                .andDo(document( "post-comment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getDefaultRequestHeaderDescriptors()
                        ),
                        pathParameters(
                                getCommentPostRequestPathParameterDescriptor()
                        ),
                        requestFields(
                                getCommentPostRequestDescriptors()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 Comment의 URI")
                        )
                ));

    }


    @DisplayName("Test - CommentController - PATCH")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void patchCommentTest() throws Exception {

        // given
        CommentDto.Patch patchDto = (CommentDto.Patch) StubData.MockComment.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patchDto);

        CommentDto.Response responseDto = StubData.MockComment.getSingleResponseBody();
        Comment comment = StubData.MockComment.getSingleResultComment();
        User user = StubData.MockUser.getSingleResultUser();
        comment.setUser(user);

        given(commentService.findComment(Mockito.anyLong())).willReturn(comment);
        given(userService.findUser(Mockito.anyLong())).willReturn(user);
        given(mapper.commentPatchDtoToComment(Mockito.any(CommentDto.Patch.class))).willReturn(comment);
        given(commentService.updateComment(Mockito.any(Comment.class))).willReturn(comment);
        given(mapper.commentToCommentResponseDto(Mockito.any(Comment.class))).willReturn(responseDto);


        // when
        ResultActions actions = mockMvc.perform(
                patchCommentRequestBuilder(getURI(), responseDto.getBoardId(),responseDto.getCommentId(), content)
        );



        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value(patchDto.getContent()))
                .andDo(document("patch-comment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getDefaultRequestHeaderDescriptors()
                        ),
                        pathParameters(
                                getCommentRequestPathParameterDescriptor()
                        ),
                        requestFields(
                                getCommentPatchRequestDescriptors()
                        ),
                        responseFields(
                                getFullResponseDescriptors(
                                        getCommentResponseDescriptors(DataResponseType.SINGLE))
                        )
                ));

    }

    @DisplayName("Test - CommentController - DELETE")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void deleteCommentTest() throws Exception {

        // given
        Comment comment = StubData.MockComment.getSingleResultComment(1L);
        Board board = StubData.MockBoard.getSingleResultBoard(1L);
        User user = StubData.MockUser.getSingleResultUser();
        board.setCommentCount(1);
        comment.setBoard(board);
        board.setUser(user);
        comment.setUser(user);

        given(commentService.findComment(Mockito.anyLong())).willReturn(comment);
        given(userService.findUser(Mockito.anyLong())).willReturn(user);
        given(boardService.findBoardByMK(Mockito.anyLong())).willReturn(board);
        given(commentService.createComment(Mockito.any(Comment.class))).willReturn(comment);


        // when
        ResultActions actions = mockMvc.perform(
                deleteCommentRequestBuilder(getURI(), comment.getBoardId(), comment.getCommentId())
        );


        // then
        actions.andExpect(status().is2xxSuccessful())
                .andDo(document("delete-comment",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getDefaultRequestHeaderDescriptors()
                        ),
                        pathParameters(
                                getCommentRequestPathParameterDescriptor()
                        )

                ));



    }


    @DisplayName("Test - CommentController - GET")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getCommentTest() throws Exception {

        // given
        Comment comment = StubData.MockComment.getSingleResultComment(1L);
        Board board = StubData.MockBoard.getSingleResultBoard(1L);
        comment.setBoard(board);

        CommentDto.Response responseDto = StubData.MockComment.getSingleResponseBody();
        responseDto.setBoardId(comment.getBoardId());

        given(commentService.findComment(Mockito.anyLong())).willReturn(comment);
        given(mapper.commentToCommentResponseDto(Mockito.any(Comment.class))).willReturn(responseDto);

        // when
        ResultActions actions = mockMvc.perform(
                getCommentRequestBuilder(getURI(), responseDto.getBoardId(), responseDto.getCommentId())
        );



        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                        document("get-comment",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        getCommentRequestPathParameterDescriptor()
                                ),
                                responseFields(
                                        getFullResponseDescriptors(
                                                getCommentResponseDescriptors(DataResponseType.SINGLE))
                                )
                        ));

    }



    @DisplayName("Test - CommentController - GET ALL")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getCommentsTest() throws Exception {

        // given
        Page<Comment> commentPage = StubData.MockComment.getMultiResultComment();
        List<CommentDto.Response> responseList = StubData.MockComment.getMultiResponseBody();

        given(commentService.findComments(Mockito.any(Pageable.class))).willReturn(commentPage);
        given(mapper.commentPageToCommentResponseListDto(Mockito.any(Page.class))).willReturn(responseList);


        // when
        ResultActions actions = mockMvc.perform(
                getCommentsRequestBuilder(getUrl(), 1L)
        );



        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                        document("get-all-comment",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                pathParameters(
                                        getCommentsRequestPathParameterDescriptor()
                                ),
                                requestParameters(
                                        getDefaultRequestParameterDescriptors()
                                ),
                                responseFields(
                                        getFullPageResponseDescriptors(
                                                getCommentsResponseDescriptors(DataResponseType.LIST))
                                )
                        )).andReturn();

    }
}
