package br.edu.ifpb.dac.falacampus.business.service.impl.Unit;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import br.edu.ifpb.dac.falacampus.business.service.impl.AnswerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.entity.Answer;
import br.edu.ifpb.dac.falacampus.model.entity.Comment;
import br.edu.ifpb.dac.falacampus.model.repository.AnswerRepository;
import br.edu.ifpb.dac.falacampus.presentation.dto.AnswerDto;

@ExtendWith(MockitoExtension.class)
public class AnswerServiceUnitTest {

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private AnswerService answerService;

    private Answer answer;

    private AnswerDto answerDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        answer = new Answer(1L, "Test answer", new Comment(), new User());
        answerDto = new AnswerDto(answer);
    }

    @Test
    @DisplayName("Test save with success")
    public void testSave() {
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        Answer savedAnswer = answerService.save(answer);

        assertEquals(answer, savedAnswer);
        verify(answerRepository, times(1)).save(answer);
    }

    @Test
    @DisplayName("Test save with id and success")
    public void testSaveWithId() {
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        Answer savedAnswer = answerService.save(answer, 1L);

        assertEquals(answer, savedAnswer);
        verify(answerRepository, times(1)).save(answer);
    }

    @Test
    @DisplayName("Test saveAnswerDto with success")
    public void testSaveAnswerDto() {
        when(mapper.map(any(AnswerDto.class), any())).thenReturn(answer);
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        Answer savedAnswer = answerService.saveAnswerDto(answerDto);

        assertEquals(answer, savedAnswer);
        verify(answerRepository, times(1)).save(answer);
        verify(mapper, times(1)).map(answerDto, Answer.class);
    }

    @Test
    @DisplayName("Test deleteById with existing id")
    public void testDeleteByIdWithExistingId() {
        when(answerRepository.findById(anyLong())).thenReturn(Optional.of(answer));

        assertDoesNotThrow(() -> answerService.deleteById(1L));
        verify(answerRepository, times(1)).deleteById(1L);
    }
    
    @Test
    @DisplayName("Test deleteById with non-existing id")
    public void testDeleteByIdWithNonExistingId() {
        when(answerRepository.findById(anyLong())).thenThrow(new NoSuchElementException());

        assertThrows(NoSuchElementException.class, () -> answerService.deleteById(1L));
        verify(answerRepository, times(0)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Test update with success")
    public void testUpdate() {
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        Answer updatedAnswer = answerService.update(answer);

        assertEquals(answer, updatedAnswer);
        verify(answerRepository, times(1)).save(answer);
    }
    
    @Test
    @DisplayName("Test findAll method")
    public void testFindAll() {
        List<Answer> answers = new ArrayList<>();
        Answer answer1 = new Answer(1L, "Test answer 1",new Comment() , new User());
        Answer answer2 = new Answer(2L,"Test answer 2",new Comment() , new User());
        Answer answer3 = new Answer(3L,"Test answer 3",new Comment() , new User());
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        when(answerRepository.findAll()).thenReturn(answers);

        List<Answer> allAnswers = answerService.findAll();
        assertEquals(3, allAnswers.size());
        assertEquals("Test answer 1", allAnswers.get(0).getMessage());
        assertEquals("Test answer 2", allAnswers.get(1).getMessage());
        assertEquals("Test answer 3", allAnswers.get(2).getMessage());

        verify(answerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test find method")
    public void testFind() {
        List<Answer> answers = new ArrayList<>();
        Answer answer1 = new Answer(1L, "Test answer 1",new Comment() , new User());
        Answer answer2 = new Answer(2L,"Test answer 2",new Comment() , new User());
        Answer answer3 = new Answer(3L,"Test answer 3",new Comment() , new User());
        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);

        Answer filter = new Answer();
        filter.setMessage("answer");

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
        Example<Answer> example = Example.of(filter, matcher);
        when(answerRepository.findAll(example)).thenReturn(answers);

        List<Answer> allAnswers = answerService.find(filter);
        assertEquals(3, allAnswers.size());
        assertEquals("Test answer 1", allAnswers.get(0).getMessage());
        assertEquals("Test answer 2", allAnswers.get(1).getMessage());
        assertEquals("Test answer 3", allAnswers.get(2).getMessage());

        verify(answerRepository, times(1)).findAll(example);
    }


    
}