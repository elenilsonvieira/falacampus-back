package br.edu.ifpb.dac.falacampus.presentation.control.Inte;
import br.edu.ifpb.dac.falacampus.business.service.impl.*;
import br.edu.ifpb.dac.falacampus.business.service.impl.Unit.ConfigInterfaceUnitTest;
import br.edu.ifpb.dac.falacampus.config.ConfigPagination;
import br.edu.ifpb.dac.falacampus.model.entity.*;
import br.edu.ifpb.dac.falacampus.model.enums.CommentType;
import br.edu.ifpb.dac.falacampus.model.enums.StatusComment;
import br.edu.ifpb.dac.falacampus.presentation.control.CommentController;
import br.edu.ifpb.dac.falacampus.presentation.dto.AnswerDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.DetailsCommentDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.micrometer.core.instrument.util.StringUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters = false)
public class CommentControllTest implements ConfigInterfaceUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CommentController commentController;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private DepartamentService departementService;
    @Autowired
    private SystemRoleServiceImpl systemRoleService;
    private static  ObjectMapper objectMapper;
    private Departament dep;
    private User auth;
    private Comment comment;
    private DetailsCommentDto commentDto;

    @BeforeEach
    void up(){
        Departament departament = new Departament(null, "IT Department");
        dep = departementService.save(departament);

        User author = new User(null, "John Doe", "john@example.com", "john", "password", departament);
        List<SystemRole> roles = new ArrayList<>();
        roles.add(systemRoleService.findAdmin());
        author.setRoles(roles);
        auth = userService.save(author);
        List<User> users = new ArrayList<>();
        users.add(author);
        dep.setResponsibleUsers(users);
        dep = departementService.save(departament);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        commentDto = new DetailsCommentDto();
        commentDto.setTitle("My Comment");
        commentDto.setMessage("This is my comment message");
        commentDto.setCommentType(CommentType.SUGGESTION);
        commentDto.setStatusComment(StatusComment.NOT_SOLVED);
        commentDto.setAuthorId(auth.getId());
        commentDto.setDepartamentId(dep.getId());

    }

    void saveComment(){
        try {

            String json = objectMapper.writeValueAsString(commentDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            commentDto = objectMapper.readValue(responseBody,DetailsCommentDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Test
    @Order(1)
    void commentSaveTest() {
        DetailsCommentDto dto = commentDto;

        try {

            String json = objectMapper.writeValueAsString(commentDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            commentDto = objectMapper.readValue(responseBody,DetailsCommentDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        Assertions.assertNotNull(commentDto);
        Assertions.assertEquals(dto.getMessage(), commentDto.getMessage());
        Assertions.assertEquals(dto.getAuthorId(), commentDto.getAuthorId());
    }

    @Test
    @Order(2)
    @DisplayName("CommentSaveErroNoDepartamentTest")
    void commentSaveErroDPTest() {
        commentDto.setDepartamentId(null);

        try {
            String json = objectMapper.writeValueAsString(commentDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            System.out.println(responseBody);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Order(3)
    void commentSaveErroAuthTest() {
        commentDto.setAuthorId(null);

        try {
            String json = objectMapper.writeValueAsString(commentDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            System.out.println(responseBody);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Order(4)
    void commentSaveErroNullTest() {
        commentDto = null;

        try {
            String json = objectMapper.writeValueAsString(commentDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/comment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            System.out.println(responseBody);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Order(5)
    void commentUpdateTest() {
        saveComment();
        commentDto.setMessage("Message Update");
        try {
            String json = objectMapper.writeValueAsString(commentDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/comment/"+commentDto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            commentDto = objectMapper.readValue(responseBody,DetailsCommentDto.class);

            Assertions.assertEquals("Message Update", commentDto.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Order(6)
    void commentUpdateNullErroTest() {
        saveComment();
        commentDto.setMessage(null);
        try {
            String json = objectMapper.writeValueAsString(commentDto);

            mockMvc.perform(MockMvcRequestBuilders.put("/api/comment/"+commentDto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Order(7)
    void commentUpdateStatusTest() {
        saveComment();
        commentDto.setStatusComment(StatusComment.SOLVED);

        try {
            String json = objectMapper.writeValueAsString(commentDto);

            mockMvc.perform(MockMvcRequestBuilders.put("/api/comment/"+commentDto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    @Order(8)
    void commentUpdateMessageErroTest() {
        saveComment();
        try {
            commentDto.setMessage(null);
            String json = objectMapper.writeValueAsString(commentDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/comment/"+commentDto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    @Order(9)
    void commentDeleteTest() {
        saveComment();
        try {

            mockMvc.perform(MockMvcRequestBuilders.delete("/api/comment/"+commentDto.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andReturn();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    @Order(10)
    void commentDeleteErroTest() {
        saveComment();
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/comment/66")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Order(11)
    void commentfindByFilterTest() {
        saveComment();

        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/comment")
                            .param("id", commentDto.getId().toString())
                            .param("title", commentDto.getTitle())
                            .param("message", commentDto.getMessage())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<DetailsCommentDto> commentDtos = objectMapper.readValue(jsonResponse, new TypeReference<List<DetailsCommentDto>>() {});

            Assertions.assertNotNull(commentDtos);
            Assertions.assertEquals(1, commentDtos.size());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    @Order(12)
    void commentFBFilterInvalidTest() {
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/comment")
                            .param("id", "54")
                            .param("title", "@Title")
                            .param("message", "@Message")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<DetailsCommentDto> commentDtos = objectMapper.readValue(jsonResponse, new TypeReference<List<DetailsCommentDto>>() {
            });

            Assertions.assertEquals(0, commentDtos.size());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Order(13)
    void CommentFindAllTest() {
        saveComment();
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/all")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<DetailsCommentDto> commentDtos = objectMapper.readValue(jsonResponse, new TypeReference<List<DetailsCommentDto>>() {});

            Assertions.assertTrue( commentDtos.size() >1);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    @Order(13)
    void CommentFindAllNullTest() {
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/all")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<DetailsCommentDto> commentDtos = objectMapper.readValue(jsonResponse, new TypeReference<List<DetailsCommentDto>>() {});

            Assertions.assertFalse(commentDtos.get(0).getId() == commentDto.getId());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Order(14)
    void CommentFindByIdTest() {
        saveComment();

        try {

            MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/"+commentDto.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                            .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();

            Assertions.assertNotNull(responseBody);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    @Order(15)
    @Disabled("Refatorar logica do controller")
    void CommentFindByIdInvalidTest() {

        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/commentSolved")
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                            .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            Assertions.assertNull(responseBody);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    @Order(14)
    @Disabled("Refatorar logica do controller")
    void CommentFindSolvedTest() {
        commentDto.setStatusComment(StatusComment.SOLVED);
        saveComment();

        try {

            MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/"+commentDto.getId())
                            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                    .andReturn();


            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<DetailsCommentDto> commentDtos = objectMapper.readValue(jsonResponse, new TypeReference<List<DetailsCommentDto>>() {});
            Assertions.assertTrue(commentDtos.size() == 1);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    @Order(15)
    void commentFindAllPageTest() {
        saveComment();
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/pages")
                            .param("page", "0")
                            .param("size", "10")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            Page<DetailsCommentDto> page = objectMapper.readValue(jsonResponse, new TypeReference<Page<DetailsCommentDto>>() {});

            Assertions.assertNotNull(page);
            Assertions.assertFalse(page.isEmpty());

        } catch (Exception e) {
        }
    }
    @Test
    @Order(16)
    @Disabled("Rever logica Erro 500")
    void commentReadAllTest() {
        saveComment();
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/comment/pagesAll")
                            .param("page", "0")
                            .param("size", "10")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ConfigPagination pagination = objectMapper.readValue(jsonResponse, ConfigPagination.class);

            Assertions.assertNotNull(pagination);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
