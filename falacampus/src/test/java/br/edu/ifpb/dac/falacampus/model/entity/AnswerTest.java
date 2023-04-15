package br.edu.ifpb.dac.falacampus.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.AssertionErrors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Testable
@DisplayName("Answer")
public class AnswerTest {

    private Set<ConstraintViolation<Answer>> violations;

    @Autowired
    private static Validator validator;

    @Autowired
    private static Answer answer;


    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) factory.getValidator();
        answer = new Answer();
        answer.setMessage("aaaaaaa");
        answer.setComment(new Comment());
        answer.setAuthor(new User());

    }

    @ParameterizedTest
    @ValueSource( strings = {"123456789", "12345678901",
            "123456789012345678901234567900123445678999192030400404400440409494939123456789012345678901234567900123445678999192030400404400440409494939123456789012345678901234567900123445678999192030400404400440409494939123456789012345678901234567900123445678999192035"
            ,"12345678901234567890123456790012344567899919203040040440044040949493912345678901234567890123456790012344567899919203040040440044040949493912345678901234567890123456790012344567899919203040040440044040949493912345678901234567890123456790012344567899919203512"})
    void messageSize(String message){
        answer.setMessage(message);

        violations = validator.validateProperty(answer, "message");
        assertEquals(0, violations.size());
    }

    @Test
    void commentSize(){
        answer.setComment(new Comment());

        // violations = validator.validateProperty(answer, "comment");
        // Assertions.assertNotEquals(0, violations.size());
        AssertionErrors.assertNotNull("Comentario nulo" , answer.getComment());

    }

    @Test
    void authorSize(){
        answer.setAuthor(new User());

        // violations = validator.validateProperty(answer, "comment");
        // Assertions.assertNotEquals(0, violations.size());
        AssertionErrors.assertNotNull("Author nulo" , answer.getAuthor());

    }

    @Test
    void dateFormat(){
        LocalDateTime creationDate = LocalDateTime.of(2022, 12, 31, 23, 59, 59);
       answer.setCreationDate(creationDate);

        // then
        Assertions.assertEquals(creationDate, answer.getCreationDate());
    }


}
