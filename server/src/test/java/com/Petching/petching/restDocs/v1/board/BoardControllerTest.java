package com.Petching.petching.restDocs.v1.board;

import com.Petching.petching.board.controller.BoardController;
import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.mapper.BoardMapper;
import com.Petching.petching.board.service.BoardService;
import com.Petching.petching.comment.mapper.CommentMapper;
import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.global.aws.s3.config.S3Configuration;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.response.PageInfo;
import com.Petching.petching.restDocs.global.helper.BoardControllerTestHelper;
import com.Petching.petching.restDocs.global.helper.StubData;
import com.Petching.petching.user.controller.UserController;
import com.Petching.petching.user.dto.UserPostDto;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.mapper.UserMapper;
import com.Petching.petching.user.service.UserService;
import com.google.gson.Gson;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BoardController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import({
        SecurityConfiguration.class,
        S3Configuration.class
})
public class BoardControllerTest implements BoardControllerTestHelper {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private BoardService boardService;

    @MockBean
    private BoardMapper boardMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtToken jwtToken;

    @MockBean
    private CommentMapper commentMapper;

    @MockBean
    private SecurityConfiguration securityConfiguration;


    @DisplayName("Test - BoardController - POST")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void postBoardTest() throws Exception {

        // given
        BoardDto.Post postDto = (BoardDto.Post) StubData.MockBoard.getRequestBody(HttpMethod.POST);
        given(boardMapper.boardPostDtoToBoard(Mockito.any(BoardDto.Post.class))).willReturn(Board.builder().build());


        Board board = Board.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .imgUrls(postDto.getImgUrls())
                .build();
        board.setBoardId(1L);

        User user = StubData.MockUser.getSingleResultUser(1L);

        given(userService.findUser(Mockito.anyLong())).willReturn(user);
        given(boardService.createBoard(Mockito.any(Board.class))).willReturn(board);
        String content = toJsonContent(postDto);


        // when
        ResultActions actions = mockMvc.perform(
                postRequestBuilder(getUrl(), content)
        );

        // then
        actions.andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/boards/"))))
                .andDo(document( "post-board",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getDefaultRequestHeaderDescriptors()
                        ),
                        requestFields(
                                getDefaultBoardPostRequestDescriptors()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 Board의 URI")
                        )
                ));

    }

    @DisplayName("Test - BoardController - GET")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getBoardTest() throws Exception {

        // given
        long boardId = 1L;
        long userId = 1L;

        BoardDto.Detail detail = StubData.MockBoard.getSingleDetailResponseBody();

        User user = StubData.MockUser.getSingleResultUser();

        given(boardService.findBoardByMK(Mockito.anyLong())).willReturn(Board.builder().build());
        given(boardMapper.boardToBoardDetailDto(Mockito.any(Board.class))).willReturn(detail);
        given(userService.findUser(Mockito.anyLong())).willReturn(user);


        // when
        ResultActions actions = mockMvc.perform(
                getRequestBuilder(getURI(), boardId)
        );


        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nickName").value(detail.getNickName()))
                .andExpect(jsonPath("$.data.title").value(detail.getTitle()))
                .andExpect(jsonPath("$.data.content").value(detail.getContent()))
                .andDo(
                        document("get-board",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        getOptionalRequestHeaderDescriptors()
                                ),
                                pathParameters(
                                        getBoardRequestPathParameterDescriptor()
                                ),
                                responseFields(
                                        getFullResponseDescriptors(
                                                getDefaultBoardDetailDescriptors(DataResponseType.SINGLE))
                                )
                        ));

    }

    @DisplayName("Test - BoardController - GET ALL")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getAllBoardTest() throws Exception {

        // given
        String page = "1";
        String size = "10";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);

        Page<Board> boardPage = StubData.MockBoard.getMultiResultBoard();
        PageInfo pageInfo = new PageInfo(
                boardPage.getNumber(),
                boardPage.getSize(),
                3,
                1
        );
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Info", new Gson().toJson(pageInfo));

        List<BoardDto.Response> responseList = StubData.MockBoard.getMultiResponseBody();
        User user = StubData.MockUser.getSingleResultUser();


        given(boardService.findBoards(Mockito.any(Pageable.class))).willReturn(boardPage);
        given(boardMapper.boardPageToBoardResponseListDto(Mockito.any(Page.class))).willReturn(responseList);
        given(userService.findUser(Mockito.anyLong())).willReturn(user);

        // when
        ResultActions actions = mockMvc.perform(
                getRequestBuilder(getUrl(), queryParams)
        );



        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                        document("get-all-board",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        getOptionalRequestHeaderDescriptors()
                                ),
                                requestParameters(
                                        getDefaultRequestParameterDescriptors()
                                ),
                                responseFields(
                                        getFullPageResponseDescriptors(
                                                getDefaultBoardResponseDescriptors(DataResponseType.LIST))
                                )
                        )).andReturn();

    }

    @DisplayName("Test - BoardController - PATCH")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void patchBoardTest() throws Exception {

        // given
        BoardDto.Patch patchDto = (BoardDto.Patch) StubData.MockBoard.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patchDto);

        BoardDto.Detail responseDto = StubData.MockBoard.getSingleDetailResponseBody();
        Board board = StubData.MockBoard.getSingleResultBoard();
        User user = StubData.MockUser.getSingleResultUser();
        board.setUser(user);

        given(boardMapper.boardPatchDtoToBoard(Mockito.any(BoardDto.Patch.class))).willReturn(Board.builder().build());
        given(boardService.updateBoard(Mockito.any(Board.class))).willReturn(Board.builder().build());
        given(boardMapper.boardToBoardDetailDto(Mockito.any(Board.class))).willReturn(responseDto);
        given(boardService.findBoardByMK(Mockito.anyLong())).willReturn(board);
        given(userService.findUser(Mockito.anyLong())).willReturn(user);



        // when
        ResultActions actions = mockMvc.perform(
                patchRequestBuilder(getURI(), responseDto.getBoardId(), content)
        );



        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(patchDto.getTitle()))
                .andExpect(jsonPath("$.data.content").value(patchDto.getContent()))
                .andDo(document("patch-board",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getDefaultRequestHeaderDescriptors()
                        ),
                        requestFields(
                                getDefaultBoardPatchRequestDescriptors()
                        ),
                        responseFields(
                                getFullResponseDescriptors(
                                        getDefaultBoardDetailDescriptors(DataResponseType.SINGLE))
                        )
                ));
    }


    @DisplayName("Test - BoardController - DELETE")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void deleteBoardTest() throws Exception {

        // given
        BoardDto.Post postDto = (BoardDto.Post) StubData.MockBoard.getRequestBody(HttpMethod.POST);

        User user = StubData.MockUser.getSingleResultUser();
        Board board = Board.builder()
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .imgUrls(postDto.getImgUrls())
                .user(user)
                .build();


        given(boardService.createBoard(Mockito.any(Board.class))).willReturn(board);
        given(boardService.findBoardByMK(Mockito.anyLong())).willReturn(board);
        given(userService.findUser(Mockito.anyLong())).willReturn(user);

        // when
        ResultActions actions = mockMvc.perform(
                deleteRequestBuilder(getURI(), user.getUserId())
        );


        // then
        actions.andExpect(status().is2xxSuccessful())
                .andDo(document("delete-board",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getDefaultRequestHeaderDescriptors()
                        ),
                        pathParameters(
                                getBoardRequestPathParameterDescriptor()
                        )

                ));

    }

    @DisplayName("Test - BoardController - GET - RecentlyBoardImgs")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void getRecentlyBoardImg() throws Exception{

        // given
        List<String> imgUrls = StubData.MockBoard.getRandomImageUrls();

        given(boardService.findBoardRandomImg()).willReturn(imgUrls);


        // when
        ResultActions actions = mockMvc.perform(
                getBoardImgsRequestBuilder(getUrl()+"/recently-created")
        );


        // then
        actions
                .andExpect(status().is2xxSuccessful())
                .andDo(document("get-board-random-imgUrls",
                        getRequestPreProcessor(),
                          getResponsePreProcessor(),
                        responseFields(
                                getFullResponseDescriptors(
                                        getRandomImgUrlsFromBoardResponseDescriptors(DataResponseType.SINGLE)
                                )
                        )
                ));
    }


    @DisplayName("Test - BoardController - POST - updateLike")
    @Test
    @WithMockUser(username = "TestAdmin", roles = "admin")
    public void updateList() throws Exception{

        // given
        long boardId = 1L;
        long userId = 1L;

        Board board = StubData.MockBoard.getSingleResultBoard(boardId);
        User user = StubData.MockUser.getSingleResultUser();
        board.setUser(user);
        board.addLikedUserId(user.getUserId());
        board.setLikes(board.getLikes()+1);

        given(userService.findUser(Mockito.anyLong())).willReturn(user);
        given(boardService.updateBoardLike(Mockito.anyLong())).willReturn(board);


        // when
        ResultActions actions = mockMvc.perform(
                postUpdateLikeRequestBuilder(getURI(),boardId)
        );



        // then
        actions
                .andExpect(status().is2xxSuccessful())
                .andDo(document("post-board-update-like",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getDefaultRequestHeaderDescriptors()
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("Board 식별자 ID")
                        ),
                        requestParameters(
                                getBoardPostUpdateLikeRequestParameterDescriptors()
                        )
                ));

    }

}
