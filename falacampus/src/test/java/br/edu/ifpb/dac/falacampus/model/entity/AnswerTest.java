package br.edu.ifpb.dac.falacampus.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.AssertionErrors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.DateTimeException;
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
        answer.setMessage("Obrigado a todos");
        answer.setComment(new Comment());
        answer.setAuthor(new User());

    }
    
    @Nested
    @DisplayName("Mensage")
	@TestMethodOrder(OrderAnnotation.class)
	class MessageTest{
		
		@Order(1)
		@Test
		void nullMessage() {
			answer.setMessage(null);
			violations = validator.validateProperty(answer, "message");
			assertFalse(violations.isEmpty());
			
		}
		
		@Order(2)
		@ValueSource(strings= {"","    ", "        "})
		void invalidMessageEmpty(String message) {
			answer.setMessage(message);
;			violations = validator.validateProperty(answer, "message");
			assertFalse(violations.isEmpty());
		
		}
		
		@Order(3)
		@DisplayName("Name Character Limits")
		@ParameterizedTest(name="Invalid Test {index} -> {0}.")
		@ValueSource(strings= {"abcdegh","abcdefghw", "Et excepturi sunt est ipsa officiis hic dolores doloremque eos officiis nihil eum "
				+ "laudantium nulla rem neque repudiandae. Sed perferendis ipsam eos nihil accusamus aut architecto omnis et voluptas voluptatum."
				+ " Est omnis obcaecati in autem"
				+ " animi et odio maxime qui odit deserunt est ratione deleniti. Aut magni doloremque sed fugiat perspiciatis ut dignissimos libero."})
		void invalidMessageMinimumAndMaximumCharacters(String message) {
			answer.setMessage(message);
			violations = validator.validateProperty(answer, "message");
			assertFalse(violations.isEmpty());
		}
		
		@Order(4)
		@ParameterizedTest(name="Valid Test {index} -> {0}.")
		@ValueSource(strings= {
				"Et excepturi sunt est ipsa officiis hic dolores doloremque eos officiis nihil eum ",
				"Testando classes",
				"Passando passado passou",
				"testando testado testou"})
		void validMessage(String message) {
			answer.setMessage(message);
			violations = validator.validateProperty(answer, "message");
			assertTrue(violations.isEmpty());
		}
    }

    @Nested
    @DisplayName("Author")
   	@TestMethodOrder(OrderAnnotation.class)
   	class AuthorTest{
   		
   		@Order(1)
   		@Test
   		void nullAuthor() {
   			answer.setAuthor(null);
   			AssertionErrors.assertNull("Author nulo" , answer.getAuthor());	
   		}
   		
   		@Order(2)
   		@Test
   		void validAuthor() {
   			answer.setAuthor(new User());
   		    AssertionErrors.assertNotNull("Author nulo" , answer.getAuthor());
   		
   		}
    }

    @Nested
    @DisplayName("Date")
   	@TestMethodOrder(OrderAnnotation.class)
   	class DateTest{
   		
   		@Order(1)
   		@Test
   		void nullDate() {
   			answer.setCreationDate(null);
   			assertNull(answer.getCreationDate());	
   		}
   		
   		@Order(2)
   		@Test
   		void validDate() {
   		   LocalDateTime creationDate = LocalDateTime.of(2022, 12, 31, 23, 59, 59);
           answer.setCreationDate(creationDate);
           Assertions.assertEquals(creationDate, answer.getCreationDate());
   		
   		}
   		@Order(3)
   		@Test
   		void invalidDate() {
   			assertThrows(DateTimeException.class, () ->  answer.setCreationDate(LocalDateTime.of(2022, 2, 30, 0, 0)));
   		}
    }
}
