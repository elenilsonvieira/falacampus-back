package br.edu.ifpb.dac.falacampus.model.entityDto;

import br.edu.ifpb.dac.falacampus.model.entity.Answer;
import br.edu.ifpb.dac.falacampus.model.entity.Comment;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.enums.CommentType;
import br.edu.ifpb.dac.falacampus.model.enums.StatusComment;
import br.edu.ifpb.dac.falacampus.presentation.dto.DetailsCommentDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetailsCommentDtoTest {

    @Autowired
    private static DetailsCommentDto detailsCommentDto;

    @BeforeAll
    public static void setUp(){
        detailsCommentDto = new DetailsCommentDto();
        detailsCommentDto.setId(1L);
        detailsCommentDto.setCommentType(CommentType.REVIEW);
        detailsCommentDto.setTitle("Title");
        detailsCommentDto.setMessage("Message1234");
        detailsCommentDto.setStatusComment(StatusComment.SOLVED);
        //detailsCommentDto.setAuthorId(2L);
        //detailsCommentDto.setDepartamentId(3L);
        //detailsCommentDto.setAnswerId(4L);
    }

    @Test
    public void gets(){
        assertEquals(StatusComment.SOLVED,detailsCommentDto.getStatusComment());
        assertEquals("Title",detailsCommentDto.getTitle());
        assertEquals("Message1234",detailsCommentDto.getMessage());
        assertEquals(CommentType.REVIEW,detailsCommentDto.getCommentType());
        assertEquals(1L,detailsCommentDto.getId());
       // assertEquals(2L, detailsCommentDto.getAuthorId());
       // assertEquals(3L,detailsCommentDto.getDepartamentId());
       // assertEquals(4L,detailsCommentDto.getAnswerId());
    }

    @Test
    public void converter(){

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setTitle("Titulo");
        comment.setMessage("Message1234");
        LocalDateTime creationDate = LocalDateTime.of(2022, 12, 31, 23, 59, 59);
        comment.setCreationDate(creationDate);
        comment.setCommentType(CommentType.COMPLIMENT);
        comment.setStatusComment(StatusComment.SOLVED);
        comment.setAnswer(new Answer());
        comment.setAuthor(new User());
        Departament dep = new Departament();
        comment.setDepartament(dep);
        Answer ans = new Answer();
        comment.setAnswer(ans);
        User us = new User();
        comment.setAuthor(us);

        DetailsCommentDto details = new DetailsCommentDto(comment);

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);



        List<DetailsCommentDto> detailsDto = DetailsCommentDto.converter(comments);


        assertEquals(1, detailsDto.size());
        assertEquals(comment.getId(), detailsDto.get(0).getId());
        assertEquals(comment.getTitle(), detailsDto.get(0).getTitle());
        assertEquals(comment.getStatusComment(), detailsDto.get(0).getStatusComment());
        assertEquals(comment.getCommentType(), detailsDto.get(0).getCommentType());
        assertEquals(comment.getMessage(), detailsDto.get(0).getMessage());



    }



}
