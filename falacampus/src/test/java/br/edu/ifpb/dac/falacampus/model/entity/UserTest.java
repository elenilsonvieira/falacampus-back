package br.edu.ifpb.dac.falacampus.model.entity;
import static org.junit.jupiter.api.Assertions.*
;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("User")
public class UserTest {
	
	@Autowired
	private static Validator validator;
	
	@Autowired
	private static User user;
	
	private Set<ConstraintViolation<User>> violations;
	
	@BeforeAll
	static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	    
	    user = new User();
	    user.setName("IUHdu");
		user.setEmail("email@email.com");
		user.setUsername("1");
		user.setDepartament(new Departament());
		user.setPassword("12345678");
	}
	
	@Order(1)
	@Nested
	@DisplayName("Name")
	@TestMethodOrder(OrderAnnotation.class)
	class NameTest{
		
		@Order(1)
		@Test
		void nullName() {
			user.setName(null);
			violations = validator.validateProperty(user, "name");
			assertFalse(violations.isEmpty());
			
		}
		
		@Order(2)
		@ValueSource(strings= {"","    ", "        "})
		void invalidNameEmpty(String name) {
			user.setName(name);
			violations = validator.validateProperty(user, "name");
			assertFalse(violations.isEmpty());
		
		}
		
		@Order(3)
		@DisplayName("Name Character Limits")
		@ParameterizedTest(name="Invalid Test {index} -> {0}.")
		@ValueSource(strings= {"", "a", "ABCDFGHIJKMNOPQRSTUVXWYZABCDEFGHIJKLMNOPQRSTUVXWYZ001abcdefg"})
		void invalidNameMinimumAndMaximumCharacters(String name) {
			user.setName(name);
			violations = validator.validateProperty(user, "name");
			assertFalse(violations.isEmpty());
		}
		
		@Order(4)
		@ParameterizedTest(name="Valid Test {index} -> {0}.")
		@ValueSource(strings= {
				"\t\nabcdefghijlm@nodsfgvdasgrdfgdafgLMNOPQRSTUVXYZ\t\n",
				"Lucas ramos silva",
				"dfghcvb",
				"gsdfgsd"})
		void validName(String name) {
			user.setName(name);
			violations = validator.validateProperty(user, "name");
			assertTrue(violations.isEmpty());
		}
		
	}
	
		
	@Order(2)
	@Nested
	@DisplayName("Email")
	@TestMethodOrder(OrderAnnotation.class)
	class EmailTest{
		@Order(1)
		@Test
		void nullEmail() {
			user.setEmail(null);
			violations = validator.validateProperty(user, "email");
			assertFalse(violations.isEmpty());
		}
		
		@Order(2)
		@ValueSource(strings= {"" ,"    ", "        "})
		void emptyEmail(String email) {
			user.setEmail(null);
			violations = validator.validateProperty(user, "email");
			assertFalse(violations.isEmpty());
		
		}
		
		@Order(3)
		@ParameterizedTest(name="invalid email {index} -> {0} ")
		@ValueSource(strings= {"lucas @gmail.com", " @gmail.com", "joao@.", "pedro.gmail@"})
		void invalidEmail(String email) {
			user.setEmail(email);
			violations = validator.validateProperty(user, "email");
			assertFalse(violations.isEmpty());
		
		}
		@Order(4)
		@ParameterizedTest(name="valid email {index} -> {0} ")
		@ValueSource(strings= {"gabriel@email.com", "lusca@g.com", "eu@eu.com"})
		void validEmail(String email) {
			user.setEmail(email);
			violations = validator.validateProperty(user, "email");
			assertTrue(violations.isEmpty());
		}
		
	}
	
	@Order(3)
	@Nested
	@DisplayName("User Name")
	@TestMethodOrder(OrderAnnotation.class)
	class UserNameTest{
		@Order(1)
		@Test
		void nullUserName() {
			user.setUsername(null);
			violations = validator.validateProperty(user, "username");
			assertFalse(violations.isEmpty());
		}
		
		@Order(2)
		@ValueSource(strings= {"" ,"    ", "        "})
		void emptyUserName(String username) {
			user.setUsername(username);
			violations = validator.validateProperty(user, "username");
			assertFalse(violations.isEmpty());
		
		}
		@Order(4)
		@ParameterizedTest(name="Teste válido {index} -> {0} ")
		@ValueSource(strings= {"gabriel", "lusca", "eu1234"})
		void validEmail(String username) {
			user.setUsername(username);
			violations = validator.validateProperty(user, "username");
			assertTrue(violations.isEmpty());
		}
	}
	

	@Order(4)
	@Nested
	@DisplayName("User Name")
	@TestMethodOrder(OrderAnnotation.class)
	class PasswordTest{
		
		@Order(1)
		@Test
		void nullPassword() {
			user.setPassword(null);
			violations = validator.validateProperty(user, "password");
			assertFalse(violations.isEmpty());
		}
		
		@Order(2)
		@ValueSource(strings= {"" ,"    ", "        "})
		void emptyPassword(String password) {
			user.setPassword(password);
			violations = validator.validateProperty(user, "password");
			assertFalse(violations.isEmpty());
		
		}

		@Order(3)
		@DisplayName("Password Character Limits")
		@ParameterizedTest(name="Invalid Test {index} -> {0}.")
		@ValueSource(strings= {"joao202","0","dfghcvb","gsdfgsd", "ABCDFGHIJKMNOPQRSTUVXWYZABCDEFGHIJKLMNOPQRSTUVXWYZ001abcdefg"})
		void invalidPasswordMinimumAndMaximumCharacters(String password) {
			user.setPassword(password);
			violations = validator.validateProperty(user, "password");
			assertFalse(violations.isEmpty());
		}
		
		@Order(4)
		@ParameterizedTest(name="Valid Test {index} -> {0}.")
		@ValueSource(strings= {
				"lm@nodsfgvdasgrdfgdafgLMNOPQRSTUVXYZ","joao2020","testedoteste","testedenovo"})
		void validPassword(String password) {
			user.setPassword(password);
			violations = validator.validateProperty(user, "password");
			assertTrue(violations.isEmpty());
		}
	}
	
	@Order(5)
	@Nested
	@DisplayName("Departament")
	@TestMethodOrder(OrderAnnotation.class)
	class DepartamentTest{
		
		@Order(1)
		@Test
		void nullDepartament() {
			user.setDepartament(null);
			violations = validator.validateProperty(user, "departament");
			assertFalse(violations.isEmpty());
		}
		
		@Order(2)
		@Test
		void validDepartament() {
			user.setDepartament(new Departament());
			violations = validator.validateProperty(user, "departament");
			assertTrue(violations.isEmpty());
		}
	}
	
}
