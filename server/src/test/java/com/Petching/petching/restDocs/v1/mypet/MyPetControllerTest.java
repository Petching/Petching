package com.Petching.petching.restDocs.v1.mypet;


import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.global.aws.s3.config.S3Configuration;
import com.Petching.petching.myPet.controller.MyPetController;
import com.Petching.petching.myPet.dto.MyPetDto;
import com.Petching.petching.myPet.entity.MyPet;
import com.Petching.petching.myPet.mapper.MyPetMapper;
import com.Petching.petching.myPet.service.MyPetService;
import com.Petching.petching.restDocs.global.helper.MyPetControllerTestHelper;
import com.Petching.petching.restDocs.global.helper.StubData;
import com.Petching.petching.restDocs.global.mock.ControllerTest;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = MyPetController.class
)
@Import({
        S3Configuration.class
})
public class MyPetControllerTest extends ControllerTest implements MyPetControllerTestHelper {

    @Autowired
    private Gson gson;

    @MockBean
    private MyPetService petService;

    @MockBean
    private MyPetMapper petMapper;

    @MockBean
    private UserService userService;


    @DisplayName("Test - MyPetController - POST")
    @Test
    @WithMockUser(username = "securityUsername", password = "securityPassword", roles = "USER")
    public void postPetTest() throws Exception {

        // given

        MyPetDto.Post postDto = (MyPetDto.Post) StubData.MockMyPet.getRequestBody(HttpMethod.POST);
        MyPet myPet = StubData.MockMyPet.getSingleResultComment();
        String content = toJsonContent(postDto);

        given(petMapper.PostToEntity(Mockito.any(MyPetDto.Post.class))).willReturn(myPet);
        given(petService.savedPet(Mockito.any(MyPet.class))).willReturn(myPet);


        // when
        ResultActions actions = mockMvc.perform(
                postRequestBuilder(getUrl(), content)
        );


        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is(startsWith("/users/pets/"))))
                .andDo(document("post-myPet",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getOptionalRequestHeaderDescriptors()
                        ),
                        requestFields(
                                getMyPetPostRequestDescriptors()
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 Board의 URI")
                        )
                ));

        actions.andDo(print());
    }


    @DisplayName("Test - MyPetController - POST")
    @Test
    @WithMockUser(username = "securityUsername", password = "securityPassword", roles = "USER")
    public void patchPetTest() throws Exception {


        // given
        MyPetDto.Patch patchDto = (MyPetDto.Patch) StubData.MockMyPet.getRequestBody(HttpMethod.PATCH);
        String content = toJsonContent(patchDto);

        MyPetDto.Response responseDto = StubData.MockMyPet.getSingleResponseBody();
        MyPet myPet = StubData.MockMyPet.getSingleResultComment();

        given(petMapper.PatchToEntity(Mockito.any(MyPetDto.Patch.class))).willReturn(myPet);
        given(petService.updatePet(Mockito.any(MyPet.class))).willReturn(myPet);
        given(petMapper.EntityToResponse(Mockito.any(MyPet.class))).willReturn(responseDto);


        // when
        ResultActions actions = mockMvc.perform(
                patchRequestBuilder(getUrl(), content)
        );


        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("patch-myPet",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                getOptionalRequestHeaderDescriptors()
                        ),
                        requestFields(
                                getMyPetPatchRequestDescriptors()
                        ),
                        responseFields(
                                getFullResponseDescriptors(
                                        getMyPetResponseDescriptors(DataResponseType.SINGLE))
                        )
                ));
    }


    @DisplayName("Test - MyPetController - GET")
    @Test
    @WithMockUser(username = "securityUsername", password = "securityPassword", roles = "USER")
    public void getPetTest() throws Exception {

        // given
        Long userId = 1L;
        List<MyPet> myPetList = StubData.MockMyPet.getMultiResultComment().toList();
        List<MyPetDto.Response> responseList = StubData.MockMyPet.getMultiResponseBody();

        given(petService.findAllPet(Mockito.anyLong())).willReturn(myPetList);
        given(petMapper.ListEntityToListDto(Mockito.anyList())).willReturn(responseList);



        // when
        ResultActions actions = mockMvc.perform(
            getRequestBuilder(getUserURI(), userId)
        );


        // then
        actions
                .andExpect(status().isOk())
                .andDo(
                        document("get-all-myPet",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        getOptionalRequestHeaderDescriptors()
                                ),
                                pathParameters(
                                        getMyPetGetAllRequestPathParameterDescriptor()
                                ),
                                responseFields(
                                        getFullResponseDescriptors(
                                                getMyPetResponseListDescriptors())
                                )
                        )
                )
                .andReturn();


    }




    @DisplayName("Test - MyPetController - GET")
    @Test
    @WithMockUser(username = "securityUsername", password = "securityPassword", roles = "USER")
    public void deletePetTest() throws Exception {

        // given
        Long petId = 1L;
        Long userId = 1L;
        User user = StubData.MockUser.getSingleResultUser(userId);
        MyPet myPet = StubData.MockMyPet.getSingleResultComment(petId);


        given(userService.findSecurityContextHolderUserId()).willReturn(userId);
        given(userService.verifiedUser(Mockito.anyLong())).willReturn(user);
        given(petService.verifiedPet(Mockito.any(User.class), Mockito.anyLong())).willReturn(myPet);


        // when
        ResultActions actions = mockMvc.perform(
                deleteRequestBuilder(getPetURI(), petId)
        );



        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(
                        document("delete-myPet",
                                getRequestPreProcessor(),
                                getResponsePreProcessor(),
                                requestHeaders(
                                        getOptionalRequestHeaderDescriptors()
                                ),
                                pathParameters(
                                        getMyPetDeleteRequestPathParameterDescriptor()
                                )
                        )
                )
                .andReturn();

    }



}