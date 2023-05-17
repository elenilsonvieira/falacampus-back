package br.edu.ifpb.dac.falacampus.business.service.impl.Inte;
import br.edu.ifpb.dac.falacampus.business.service.impl.AnswerService;
import br.edu.ifpb.dac.falacampus.business.service.impl.CommentService;
import br.edu.ifpb.dac.falacampus.business.service.impl.DepartamentService;
import br.edu.ifpb.dac.falacampus.business.service.impl.UserServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.*;
import br.edu.ifpb.dac.falacampus.model.enums.CommentType;
import br.edu.ifpb.dac.falacampus.model.enums.StatusComment;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class CommentServiceTest implements ConfigTestIntegrate{
    @Autowired
    private CommentService commentService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private DepartamentService departementService;

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

        Comment c = new Comment(null, " Comment", " message", CommentType.SUGGESTION, StatusComment.SOLVED, auth, dep, null, null);
        comment = commentService.save(c);
    }

    @Test
    @Order(1)
    void commentSaveTest() {
        Comment c = new Comment(null, "My Comment", "This is my comment message", CommentType.SUGGESTION, StatusComment.SOLVED, auth, dep, null, null);
        comment = commentService.save(c);
        assertNotNull(comment);
        assertEquals(c.getMessage(), comment.getMessage());
        assertEquals(c.getAuthor().getId(), comment.getAuthor().getId());
        assertEquals(c.getDepartament().getId(), comment.getDepartament().getId());
    }

    @Test
    @Order(2)
    void commentSaveErroTest() {
        Comment c = null;
        assertThrows(IllegalStateException.class, () -> {
            commentService.save(c);
        });
    }

    @Test
    @Order(3)
    void commentUpdateTest() {
        Comment c = comment;
        comment.setMessage("UPDATE MENSSAGE");
        comment = commentService.update(c);
        assertNotNull(comment);
        assertEquals("UPDATE MENSSAGE", comment.getMessage());
        assertEquals(c.getAuthor().getId(), comment.getAuthor().getId());
        assertEquals(c.getDepartament().getId(), comment.getDepartament().getId());
    }
    @Test
    @Order(4)
    void commentUpdateErroTest() {
        comment.setId(null);
        assertThrows(IllegalStateException.class, () -> {
            commentService.update(comment);
        });
    }

    @Test
    @Order(5)
    void commentFindByIdTest() {
        Comment c = commentService.findById(comment.getId());
        assertNotNull(c);
        assertEquals(c.getId(),comment.getId());
        assertEquals(c.getMessage(), comment.getMessage());
        assertEquals(c.getDepartament().getId(),comment.getDepartament().getId());
    }

    @Test
    @Order(6)
    void commentFindByIdErroTest() {
        assertThrows(IllegalStateException.class, () -> {
            commentService.findById(null);
        });
    }
    @Test
    @Order(7)
    void  commentFindAllTest() {
        List<Comment> c = commentService.findAll();
        assertNotNull(c);
        assertTrue(c.size() >= 1);
    }

    @Test
    @Order(8)
    void commentDeleteByIdTest() {
        commentService.deleteById(comment.getId());
        assertThrows(IllegalStateException.class, () -> {
            commentService.findById(comment.getId());
        });
    }

    @Test
    @Order(9)
    void commentDeleteByIdErroTest() {
        assertThrows(IllegalStateException.class, () -> {
            commentService.deleteById(65L);
        });
    }
}
