package com.Petching.petching.restDocs.v1.board;

import com.Petching.petching.board.controller.BoardController;
import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.board.mapper.BoardMapper;
import com.Petching.petching.board.service.BoardService;
import com.Petching.petching.config.SecurityConfiguration;
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
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.Petching.petching.restDocs.global.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Import(SecurityConfiguration.class)
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



    @DisplayName("Test - BoardController - POST")
    @Test
    public void postBoardTest() throws Exception {

        // given
        BoardDto.Post postDto = (BoardDto.Post) StubData.MockBoard.getRequestBody(HttpMethod.POST);
        given(boardMapper.boardPostDtoToBoard(Mockito.any(BoardDto.Post.class))).willReturn(new Board());


        Board board = Board.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .imgUrls(postDto.getImgUrls())
                .build();
        board.setBoardId(1L);

        User user = StubData.MockUser.getSingleResultUser(postDto.getUserId());

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
                        requestFields(
                                getDefaultBoardPostRequestDescriptors()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 User의 URI")
                        )
                ));

    }




}
