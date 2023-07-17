package com.Petching.petching.restDocs.v1.board;

import com.Petching.petching.board.controller.BoardController;
import com.Petching.petching.config.SecurityConfiguration;
import com.Petching.petching.user.controller.UserController;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@WebMvcTest(BoardController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import(SecurityConfiguration.class)
public class BoardControllerTest {


}
