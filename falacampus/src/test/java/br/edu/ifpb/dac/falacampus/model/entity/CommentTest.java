package br.edu.ifpb.dac.falacampus.model.entity;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.AssertionErrors;

@Testable
@DisplayName("Teste da Classe Comment")
public class CommentTest {

	@Autowired
	private static Comment comment;
	@Autowired
	private static Validator validator;
	private Set<ConstraintViolation<Comment>> violations;

	@BeforeAll
	static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = (Validator) factory.getValidator();

		comment = new Comment();
		comment.setAnswer(new Answer());
		comment.setAttachment(new File(""));
		comment.setAuthor(new User());
		comment.setCreationDate(LocalDateTime.now());
		comment.setDepartament(new Departament());
		comment.setId(1L);
		comment.setMessage("Testes de Unidade!");
		comment.setTitle("Testes");

	}

	@Nested
	@DisplayName("Testes de Title")
	@TestMethodOrder(OrderAnnotation.class)
	class TitleTest {

		@Order(1)
		@Test
		void nullTitle() {
			comment.setTitle(null);
			violations = validator.validateProperty(comment, "title");
			assertFalse(violations.isEmpty());

		}

		@Order(2)
		@ValueSource(strings = { "", "    ", "        " })
		void invalidTitleEmpty(String title) {
			comment.setTitle(title);
			violations = validator.validateProperty(comment, "title");
			assertFalse(violations.isEmpty());

		}

		@Order(3)
		@DisplayName("Testes tamanho maximo e minimo invalido")
		@ParameterizedTest(name = "Teste invalido {index} -> {0}.")
		@ValueSource(strings = { "ab", "um teste para saber se é possivel passar mais de 51" })
		void invalidTitleMinimumAndMaximumCharacters(String title) {
			comment.setTitle(title);
			violations = validator.validateProperty(comment, "title");
			assertFalse(violations.isEmpty());

		}

		@Order(4)
		@DisplayName("Testes tamanho maximo e minimo valido")
		@ParameterizedTest(name = "Teste Valido {index} -> {0}.")
		@ValueSource(strings = { "abcde", "Exemplo com caracteres entre 5 e 50",
				"Aqui temos uma frase com 50 caracteres para testar" })
		void validTitle(String title) {
			comment.setTitle(title);
			violations = validator.validateProperty(comment, "title");
			assertTrue(violations.isEmpty());
		}

	}

	@Nested
	@DisplayName("Testes de Message")
	@TestMethodOrder(OrderAnnotation.class)
	class MessageTest {
		@Order(1)
		@Test
		void nullMessage() {
			comment.setMessage(null);
			violations = validator.validateProperty(comment, "message");
			assertFalse(violations.isEmpty());

		}

		@Order(2)
		@ValueSource(strings = { "", "    ", "        " })
		void invalidMessageEmpty(String message) {
			comment.setMessage(message);
			;
			violations = validator.validateProperty(comment, "message");
			assertFalse(violations.isEmpty());

		}

		@Order(3)
		@DisplayName("Testes tamanho maximo e minimo invalido")
		@ParameterizedTest(name = "Teste invalido {index} -> {0}.")
		@ValueSource(strings = { "ab",
				"A Neo Química Arena recebeu, na tarde deste domingo(16), a estreia do Corinthians no Brasileirão 2023. "
						+ "O time comandado por Fernando Lázaro venceu o Cruzeiro por 2 a 1, com gols de Matheus Araújo e Róger Guedes. Lucas Oliveira descontou para os visitantes." })
		// teste 1 = 9 caracteres e teste 2 = 256 caracteres
		void invalidMessageMinimumAndMaximumCharacters(String message) {
			comment.setMessage(message);
			violations = validator.validateProperty(comment, "message");
			assertFalse(violations.isEmpty());
		}

		@Order(4)
		@ParameterizedTest(name = "Teste valido {index} -> {0}.")
		@ValueSource(strings = { "caracteres", "O Corinthians volta a campo na próxima quarta-feira, dia 19, "
				+ "para enfrentar o Argentinos Juniors, da Argentina, pela segunda rodada da fase de grupos da Libertadores. A Neo Química Arena será o palco do confronto.",
				"Todo caderno de receita conta com um Bolo de Cenoura com Cobertura de Chocolate. "
						+ "Mas aqui você aprende a melhor forma de fazê-lo e todas as suas variações, com um toque de Maizena, "
						+ "seu bolo vai ficar fofinho e delicioso. Siga passo a passo e tire a prova!" })
		// teste 1 = 10 caracteres, teste 2 = 213 e teste 3 = 255
		void validMessage(String message) {
			comment.setMessage(message);
			violations = validator.validateProperty(comment, "message");
			assertTrue(violations.isEmpty());
		}
	}

	@Nested
	@DisplayName("Testes de Date")
	@TestMethodOrder(OrderAnnotation.class)
	class DateTest {

		@Order(1)
		@Test
		void nullDate() {
			comment.setCreationDate(null);
			assertNull(comment.getCreationDate());
		}

		@Order(2)
		@Test
		void validDate() {
			LocalDateTime creationDate = LocalDateTime.now();
			comment.setCreationDate(creationDate);
			Assertions.assertEquals(creationDate, comment.getCreationDate());

		}

		@Order(3)
		@Test
		void invalidDate() {
			assertThrows(DateTimeException.class, () -> comment.setCreationDate(LocalDateTime.of(2022, 2, 30, 0, 0)));
		}
	}

	@Nested
	@DisplayName("Testes de Author")
	@TestMethodOrder(OrderAnnotation.class)
	class AuthorTest {

		@Order(1)
		@Test
		void nullAuthor() {
			comment.setAuthor(null);
			AssertionErrors.assertNull("Author nulo", comment.getAuthor());
		}

		@Order(2)
		@Test
		void validAuthor() {
			comment.setAuthor(new User());
			AssertionErrors.assertNotNull("Author valido", comment.getAuthor());

		}
	}

	@Nested
	@DisplayName("Testes de Departament")
	@TestMethodOrder(OrderAnnotation.class)
	class DepartamentTest {

		@Order(1)
		@Test
		void nullDepartament() {
			comment.setDepartament(null);
			violations = validator.validateProperty(comment, "departament");
			assertFalse(violations.isEmpty());
		}

		@Order(2)
		@Test
		void validDepartament() {
			comment.setDepartament(new Departament());
			violations = validator.validateProperty(comment, "departament");
			assertTrue(violations.isEmpty());
		}
	}

	@Nested
	@DisplayName("Testes de Answer")
	@TestMethodOrder(OrderAnnotation.class)
	class AnswerTest {

		@Order(1)
		@Test
		void nullAnswer() {
			comment.setAnswer(null);
			violations = validator.validateProperty(comment, "answer");
			AssertionErrors.assertNull("Answer nulo", comment.getAnswer());
		}

		@Order(2)
		@Test
		void validAnswer() {
			comment.setAnswer(new Answer());
			violations = validator.validateProperty(comment, "answer");
			AssertionErrors.assertNotNull("Answer valido", comment.getAnswer());
		}
	}
	
	@Nested
	@DisplayName("Testes de CommentAttachment")
	@TestMethodOrder(OrderAnnotation.class)
	class AttachmentTest {

		@Order(1)
		@Test
		void nullAttachment() {
			comment.setAttachment(null);
			violations = validator.validateProperty(comment, "attachment");
			AssertionErrors.assertNull("Attachment nulo", comment.getAttachment());
		}

		@Order(2)
		@Test
		void validAttachment() {
			comment.setAttachment(new File("arquivo.txt"));
			violations = validator.validateProperty(comment, "attachment");
			AssertionErrors.assertNotNull("Attachment valido", comment.getAttachment());
		}
	}
}
