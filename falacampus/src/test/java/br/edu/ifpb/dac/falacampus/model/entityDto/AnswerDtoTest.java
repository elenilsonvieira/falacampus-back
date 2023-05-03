package br.edu.ifpb.dac.falacampus.model.entityDto;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import br.edu.ifpb.dac.falacampus.model.entity.Answer;
import br.edu.ifpb.dac.falacampus.model.entity.Comment;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.dto.AnswerDto;

public class AnswerDtoTest {
	
	private static AnswerDto answerDto;

	@BeforeAll
	public static void setUp() {
		answerDto = new AnswerDto();
		answerDto.setId(1L);
		answerDto.setMessage("Test answer message");
		answerDto.setCommentId(2L);
		answerDto.setAuthorId(3L);	
	}
	
	@Test
	public void gets() {
		assertEquals(1L, answerDto.getId());
		assertEquals("Test answer message", answerDto.getMessage());
		assertEquals(2L, answerDto.getCommentId());
		assertEquals(3L, answerDto.getAuthorId());
	}
	
	@Test
	public void converter() {
		Answer answer = new Answer();
		answer.setId(1L);
		answer.setMessage("Test answer message");
		answer.setComment(new Comment());
		answer.setCreationDate(LocalDateTime.now());
		answer.setAuthor(new User());

		List<Answer> answers = new ArrayList<>();
		answers.add(answer);

		List<AnswerDto> answerDtos = AnswerDto.converter(answers);

		assertEquals(1, answerDtos.size());
		assertEquals(answer.getId(), answerDtos.get(0).getId());
		assertEquals(answer.getMessage(), answerDtos.get(0).getMessage());
		assertEquals(answer.getCreationDate(), answerDtos.get(0).getCreationDate());
		assertNotNull(answer.getComment());
		assertNotNull(answer.getAuthor());
	}

}

