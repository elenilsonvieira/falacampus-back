package br.edu.ifpb.dac.falacampus.business.service.impl.Unit;
import br.edu.ifpb.dac.falacampus.business.service.impl.DetailsCommentConverterServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Answer;
import br.edu.ifpb.dac.falacampus.model.entity.Comment;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.enums.CommentType;
import br.edu.ifpb.dac.falacampus.model.enums.StatusComment;
import br.edu.ifpb.dac.falacampus.presentation.dto.DetailsCommentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DetailsCommentUnitTest implements ConfigInterfaceUnitTest {
    @Autowired
    static DetailsCommentConverterServiceImpl detailsConverter;
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new ModelMapper();
        detailsConverter = new DetailsCommentConverterServiceImpl();
        detailsConverter.setMapper(mapper);
    }

    @Test
    @Order(1)
    void detailsCommentToDTOTest(){
        List<Comment> commentList = new ArrayList<>();

        CommentType commentType = CommentType.SUGGESTION;
        StatusComment statusComment = StatusComment.SOLVED;
        User author = new User();
        author.setId(1L);
        author.setName("John Doe");
        Departament departament = new Departament(1L, "IT Department");
        Answer answer = new Answer(1L, "Hello, everyone!", null, author);
        Comment comment1 = new Comment(1L, "My Comment", "This is my comment message", commentType, statusComment, author, departament, answer, null);
        commentList.add(comment1);

        CommentType commentType2 = CommentType.REVIEW;
        StatusComment statusComment2 = StatusComment.NOT_SOLVED;
        User author2 = new User();
        author2.setId(2L);
        author2.setName("Jane Smith");
        Departament departament2 = new Departament(2L, "HR Department");
        Answer answer2 = new Answer(2L, "Greetings!", null, author2);
        Comment comment2 = new Comment(2L, "Another Comment", "This is another comment message", commentType2, statusComment2, author2, departament2, answer2, null);
        commentList.add(comment2);

        List<DetailsCommentDto> commentDtos = detailsConverter.detailsCommentToDTO(commentList);
        assertNotNull(commentDtos);
        assertEquals(commentDtos.get(0).getId(), comment1.getId());
        assertEquals(commentDtos.get(0).getMessage(), comment1.getMessage());
        assertEquals(commentDtos.get(0).getAuthorId(), comment1.getAuthor().getId());

        assertEquals(commentDtos.get(1).getId(), comment2.getId());
        assertEquals(commentDtos.get(1).getMessage(), comment2.getMessage());
        assertEquals(commentDtos.get(1).getAuthorId(), comment2.getAuthor().getId());

    }

    @Test
    @Order(2)
    void dtoToDetailsComment(){
        DetailsCommentDto detailsCommentDto = new DetailsCommentDto();
        detailsCommentDto.setId(1L);
        detailsCommentDto.setTitle("My Comment");
        detailsCommentDto.setMessage("This is my comment message");
        detailsCommentDto.setCommentType(CommentType.SUGGESTION);
        detailsCommentDto.setStatusComment(StatusComment.SOLVED);
        detailsCommentDto.setAuthorId(1L);
        detailsCommentDto.setDepartamentId(1L);
        detailsCommentDto.setAnswerId(1L);

        Comment comment = detailsConverter.dtoToDetailsComment(detailsCommentDto);

        assertNotNull(comment);
        assertEquals(detailsCommentDto.getId(), comment.getId());
        assertEquals(detailsCommentDto.getTitle(), comment.getTitle());
        assertEquals(detailsCommentDto.getAuthorId(), comment.getAuthor().getId());

    }

    @Test
    @Order(3)
    void detailsCommentToDTO(){
        CommentType commentType = CommentType.SUGGESTION;
        StatusComment statusComment = StatusComment.SOLVED;
        User author = new User();
        author.setId(1L);
        author.setName("John Doe");
        Departament departament = new Departament(1L, "IT Department");
        Answer answer = new Answer(1L, "Hello, everyone!", null, author);
        Comment comment1 = new Comment(1L, "My Comment", "This is my comment message", commentType, statusComment, author, departament, answer, null);

        DetailsCommentDto commentDto  = detailsConverter.detailsCommentToDTO(comment1);

        assertNotNull(commentDto);
        assertEquals(commentDto.getId(), comment1.getId());
        assertEquals(commentDto.getMessage(), comment1.getMessage());
        assertEquals(commentDto.getAuthorId(), comment1.getAuthor().getId());
    }
}
