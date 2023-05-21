package br.edu.ifpb.dac.falacampus.presentation.control.Inte;
import br.edu.ifpb.dac.falacampus.business.service.impl.*;
import br.edu.ifpb.dac.falacampus.business.service.impl.Unit.ConfigInterfaceUnitTest;
import br.edu.ifpb.dac.falacampus.model.entity.*;
import br.edu.ifpb.dac.falacampus.model.enums.CommentType;
import br.edu.ifpb.dac.falacampus.model.enums.StatusComment;
import br.edu.ifpb.dac.falacampus.presentation.dto.AnswerDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc(addFilters = false)
public class AnswerControllTeste implements ConfigInterfaceUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private DepartamentService departamentService;
    @Autowired
    private SystemRoleServiceImpl systemRoleService;
    private static  ObjectMapper objectMapper;
    private static SystemRole systemRole;
    private static Departament dep;
    private static User user;
    private static Comment comment;
    private static AnswerDto answer;

    @BeforeEach
    void up(){
        Departament departament = new Departament(null, "IT Department");
        dep = departamentService.save(departament);
        systemRole = systemRoleService.findAdmin();
        User author = new User();
        author.setId(null);
        author.setName("John Doe");
        author.setPassword("password");
        author.setUsername("john");
        author.setEmail("john@example.com");
        author.setDepartament(dep);
        List<SystemRole> roles = new ArrayList<>();
        roles.add(systemRole);
        author.setRoles(roles);
        user = userService.save(author);

        Comment c = new Comment(null, " Comment", " message", CommentType.SUGGESTION, StatusComment.NOT_SOLVED, user, dep, null, null);
        comment = commentService.save(c);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    @Order(1)
    @DisplayName("AnswerSaveDepartTest")
    void answerSaveDepartControl(){
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(2L);
        answerDto.setMessage("Mensage Teste");
        answerDto.setCommentId(comment.getId());
        answerDto.setAuthorId(user.getId());

        try {
            String json = objectMapper.writeValueAsString(answerDto);
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/answer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();

            answer = objectMapper.readValue(jsonResponse, AnswerDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }

        Assertions.assertNotNull(answerDto);
        Assertions.assertEquals(answerDto.getMessage(), answer.getMessage());
        Assertions.assertEquals(answerDto.getAuthorId(), answer.getAuthorId());
        Assertions.assertEquals(answerDto.getCommentId(), answer.getCommentId());
    }

    @Test
    @Order(2)
    @DisplayName("AnswerDepartamentNullErroTest")
    void answerSaveErroDepartControl() {
        user.setId(null);
        user = userService.save(user);
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(8L);
        answerDto.setMessage("Mensage Teste");
        answerDto.setAuthorId(user.getId());


        try {
            String json = objectMapper.writeValueAsString(answerDto);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/answer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Order(3)
    @DisplayName("AnswerAuthorErroTest")
    void answerSaveErroAuthControl() {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(55L);
        answerDto.setMessage("Mensage Teste");
        answerDto.setCommentId(comment.getId());


        try {

            String json = objectMapper.writeValueAsString(answerDto);

            mockMvc.perform(MockMvcRequestBuilders.post("/api/answer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest()).andReturn();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
    }
    @Test
    @Order(4)
    @DisplayName("AnswerStatusErroTest")
    void answerSaveErroStatusControl() {
        Comment c = new Comment(null, " Comment", " message", CommentType.SUGGESTION, StatusComment.SOLVED, user, dep, null, null);
        c = commentService.save(c);
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(55L);
        answerDto.setMessage("Mensage Teste");
        answerDto.setCommentId(c.getId());
        answerDto.setAuthorId(user.getId());

        try {

            String json = objectMapper.writeValueAsString(answerDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/answer")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest()).andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();

            Assertions.assertEquals("Comment is solved.", jsonResponse);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
    }
    @Test
    @Order(5)
    @DisplayName("AnswerFindByIdTest")
    void answerFindByIdControl() {
        Answer answer1 = new Answer();
        answer1.setId(55L);
        answer1.setMessage("Mensage Teste");
        answer1.setAuthor(user);
        answer1.setComment(comment);
        answer1 = answerService.save(answer1);

        try {

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/answer/"+answer1.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            String jsonResponse = mvcResult.getResponse().getContentAsString();

            Assertions.assertNotNull(jsonResponse);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
    }

    @Test
    @Order(6)
    @DisplayName("AnswerFindByIdErroTest")
    void answerFindByIdErroControl(){
        try {
             mockMvc.perform(MockMvcRequestBuilders.get("/api/answer/55")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> {
                        String errorMessage = result.getResponse().getContentAsString();
                        Assertions.assertEquals("Id cannot be null", errorMessage);
                    }).andReturn();

        } catch (Exception e) {

        }
    }
    @Test
    @Order(7)
    @DisplayName("AnswerGetAllControlTest")
    void answerGetAllControl() {
        Answer answer1 = new Answer();
        answer1.setId(55L);
        answer1.setMessage("Mensage Teste");
        answer1.setAuthor(user);
        answer1.setComment(comment);
        answer1 = answerService.save(answer1);

        Answer answer2 = new Answer();
        answer2.setId(56L);
        answer2.setMessage("Mensage Teste 2");
        answer2.setAuthor(user);
        answer2.setComment(comment);
        answer2 = answerService.save(answer2);

        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/answer/all")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            String jsonResponse = mvcResult.getResponse().getContentAsString();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            List<AnswerDto> answerDtos = objectMapper.readValue(jsonResponse, new TypeReference<List<AnswerDto>>() {});
            Assertions.assertEquals(3, answerDtos.size());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    @Order(8)
    @DisplayName("AnswerDeleteTest")
    void answerDeleteControl() {
        Answer answer1 = new Answer();
        answer1.setId(55L);
        answer1.setMessage("Mensage Teste");
        answer1.setAuthor(user);
        answer1.setComment(comment);
        answer1 = answerService.save(answer1);

        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/answer/"+ answer1.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andReturn();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Order(9)
    @DisplayName("AnswerDeleteErrorTest")
    void answerDeleteErrorControl() {
        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/answer/55")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();
            Assertions.assertEquals("Id cannot be null", responseBody);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    @Order(10)
    @DisplayName("AnswerFindByFilterTest")
    void findByFilterTest() {
        Answer answer1 = new Answer();
        answer1.setId(55L);
        answer1.setMessage("Mensage Teste");
        answer1.setAuthor(user);
        answer1.setComment(comment);
        answer1 = answerService.save(answer1);

        try {

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/answer")
                            .param("id", answer1.getId().toString())
                            .param("message", answer1.getMessage())
                            .param("commentId", answer1.getComment().getId().toString())
                            .param("authorId",  answer1.getAuthor().getId().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            List<AnswerDto> answerDtos = objectMapper.readValue(jsonResponse, new TypeReference<List<AnswerDto>>() {});

            Assertions.assertNotNull(answerDtos);
            Assertions.assertEquals(1, answerDtos.size());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Test
    @Order(11)
    @DisplayName("AnswerFBFilterErrorTest")
    void findByFilterErrorControl() {

        try {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/answer")
                            .param("id", "55")
                            .param("message","Teste")
                            .param("commentId", "64")
                            .param("authorId",  "98")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            Assertions.assertEquals("Id cannot be null", jsonResponse);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
