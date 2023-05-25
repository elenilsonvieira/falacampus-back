package br.edu.ifpb.dac.falacampus.business.service.impl.Unit;
import br.edu.ifpb.dac.falacampus.business.service.impl.AnswerConverterServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Answer;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.entity.Comment;
import br.edu.ifpb.dac.falacampus.model.enums.CommentType;
import br.edu.ifpb.dac.falacampus.model.enums.StatusComment;
import br.edu.ifpb.dac.falacampus.presentation.dto.AnswerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnswerConverterUnitTest implements ConfigInterfaceUnitTest {
    @Autowired
    static AnswerConverterServiceImpl answerConverter;
    private ModelMapper mapper;


    @BeforeEach
    void setUp(){
        mapper = new ModelMapper();
        answerConverter = new AnswerConverterServiceImpl();
        answerConverter.setMapper(mapper);
    }

    @Test
    @Order(1)
    void answerToDTOTest(){
        CommentType commentType = CommentType.SUGGESTION;
        StatusComment statusComment = StatusComment.SOLVED;
        User author = new User();
        author.setId(1L);
        author.setName("John Doe");
        Departament departament = new Departament(1L, "IT Department");
        Comment comment = new Comment(1L, "My Comment", "This is my comment message", commentType, statusComment, author, departament, null, null);
        Answer answer1 = new Answer(1L, "Hello, everyone!", comment, author);

        AnswerDto answerDto = answerConverter.answerToDTO(answer1);

        assertNotNull(answerDto);
        assertEquals(answer1.getId(), answerDto.getId());
        assertEquals(answer1.getMessage(), answerDto.getMessage());
        assertEquals(answer1.getAuthor().getId(), author.getId());
    }

    @Test
    @Order(2)
    void dtoToAnswerTest(){
        AnswerDto answerDto = new AnswerDto();

        answerDto.setId(1L);
        answerDto.setMessage("This is the answer message.");
        answerDto.setCommentId(10L);
        answerDto.setCreationDate(LocalDateTime.now());
        answerDto.setAuthorId(100L);


        Answer answer = answerConverter.dtoToAnswer(answerDto);

        assertNotNull(answer);
        assertEquals(answer.getId(), answerDto.getId());
        assertEquals(answer.getMessage(), answerDto.getMessage());
        assertEquals(answer.getAuthor().getId(), answerDto.getAuthorId());
    }
    @Test
    @Order(3)
    void answerToDTOListTest(){
        List<Answer> answerList = new ArrayList<>();

        CommentType commentType = CommentType.SUGGESTION;
        StatusComment statusComment = StatusComment.SOLVED;
        User author = new User();
        author.setId(1L);
        author.setName("John Doe");
        Departament departament = new Departament(1L, "IT Department");
        Comment comment = new Comment(1L, "My Comment", "This is my comment message", commentType, statusComment, author, departament, null, null);
        Answer answer1 = new Answer(1L, "Hello, everyone!", comment, author);
        answerList.add(answer1);

        CommentType commentType2 = CommentType.REVIEW;
        StatusComment statusComment2 = StatusComment.NOT_SOLVED;
        User author2 = new User();
        author2.setId(2L);
        author2.setName("Jane Smith");
        Departament departament2 = new Departament(2L, "HR Department");
        Comment comment2 = new Comment(2L, "Another Comment", "This is another comment message", commentType2, statusComment2, author2, departament2, null, null);
        Answer answer2 = new Answer(2L, "Greetings!", comment2, author2);
        answerList.add(answer2);

        List<AnswerDto> answerDto = answerConverter.answerToDTOList(answerList);

        assertNotNull(answerDto);
        assertEquals(answerDto.get(0).getId(), answer1.getId());
        assertEquals(answerDto.get(0).getMessage(), answer1.getMessage());
        assertEquals(answerDto.get(0).getAuthorId(), answer1.getAuthor().getId());

        assertEquals(answerDto.get(1).getId(), answer2.getId());
        assertEquals(answerDto.get(1).getMessage(), answer2.getMessage());
        assertEquals(answerDto.get(1).getAuthorId(), answer2.getAuthor().getId());
    }
}
