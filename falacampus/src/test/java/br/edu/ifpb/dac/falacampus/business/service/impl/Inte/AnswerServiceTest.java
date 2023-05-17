package br.edu.ifpb.dac.falacampus.business.service.impl.Inte;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import java.util.*;
import br.edu.ifpb.dac.falacampus.business.service.impl.*;
import br.edu.ifpb.dac.falacampus.model.entity.*;
import br.edu.ifpb.dac.falacampus.model.enums.CommentType;
import br.edu.ifpb.dac.falacampus.model.enums.StatusComment;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

public class AnswerServiceTest implements ConfigTestIntegrate{
   @Autowired
   private AnswerService answerService;
   @Autowired
   private UserServiceImpl userService;
   @Autowired
   private DepartamentService departementService;
   @Autowired
   private CommentService commentService;
   @Mock
   private List<Answer> answers;

    private Departament dep;
    private User auth;
    private Comment comment;
    private static Answer answer;

    @BeforeEach
    public void up() {
        Departament departament = new Departament(null, "IT Department");
        dep = departementService.save(departament);
        User author = new User(null, "John Doe", "john@example.com", "john", "password", departament);
        List<SystemRole> roles = new ArrayList<>();
        SystemRole role = new SystemRole();
        roles.add(role);
        author.setRoles(roles);

        auth = userService.save(author);
        Comment comment = new Comment(null, "My Comment", "This is my comment message", CommentType.SUGGESTION, StatusComment.SOLVED, author, departament, null, null);
        this.comment = commentService.save(comment);

        Answer ans = new Answer(5L, "Hello, Guys!", comment, auth);
        answer = answerService.save(ans);
    }

    @Test
    @Order(1)
    void saveAnswerTest() {
       Answer ans = new Answer(1L, "Hello, everyone!", comment, auth);
       answer = answerService.save(ans);

       assertNotNull(answer);
       assertEquals(ans.getMessage(), answer.getMessage());
       assertEquals(ans.getAuthor().getId(), answer.getAuthor().getId());
       assertEquals(ans.getComment().getId(), ans.getComment().getId());
    }

    @Test
    @Order(2)
    void saveAnswerErrorTest() {
        Answer ans = null;
        assertThrows(IllegalStateException.class, () -> answerService.save(ans));

    }

    @Test
    @Order(3)
    void findByIdAnswerTest() {
       Answer ans = answerService.findById(answer.getId());

        assertNotNull(answer);
        assertEquals(ans.getMessage(), answer.getMessage());
        assertEquals(ans.getAuthor().getId(), answer.getAuthor().getId());
        assertEquals(ans.getComment().getId(), ans.getComment().getId());
    }

    @Test
    @Order(4)
    void findByIdAnswerErrorTest() {
        assertThrows(IllegalStateException.class, () -> {
            answerService.findById(100L);
        });

    }

    @Test
    @Order(5)
    public void answerUpdateTest() {
        answer.setMessage("Actualization");
        answer =  answerService.update(answer);
        assertNotNull(answer);
        assertEquals("Actualization",answer.getMessage());

    }
    @Test
    @Order(6)
    public void answerUpdateErroTest() {
        answer.setId(null);
        assertThrows(IllegalStateException.class, () -> {
            answerService.update(answer);
        });
    }

    @Test
    @Order(7)
    public void answerDeleteTest() {
        answerService.deleteById(answer.getId());
        assertThrows(IllegalStateException.class, () -> {
            answerService.findById(answer.getId());
        });
    }
    @Test
    @Order(8)
    public void answerDeleteErroTest() {
        assertThrows(IllegalStateException.class, () -> {
            answerService.deleteById(55L);
        });
    }

}