package br.edu.ifpb.dac.falacampus.model.entity;
import org.junit.Test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@DisplayName("Role")
public class SystemRoleTest {
	 @Autowired
	 private static SystemRole role;
	 private static Validator validator;
	 private Set<ConstraintViolation<SystemRole>> violations;	 
	
	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
	    
		 role = new SystemRole();
		 role.setName("ROLE_ADMIN");
		 role.setId(1L);
	 }


    @Test
    public void getAuthority() {
        assertEquals("ROLE_USER", role.getAuthority());
    }
    
    @Nested
	@TestMethodOrder(OrderAnnotation.class)
	class NameTest{
		
		@Order(1)
		@Test
		void nullName() {
			role.setName(null);
			violations = validator.validateProperty(role, "name");
			assertFalse(violations.isEmpty());
			
		}
		
		@Order(2)
		@ValueSource(strings= {"","    ", "        "})
		void invalidNameEmpty(String Name) {
			role.setName(Name);
;			violations = validator.validateProperty(role, "name");
			assertFalse(violations.isEmpty());
		
		}
		
		@Order(3)
		@DisplayName("Name Character Limits")
		@ParameterizedTest(name="Invalid Test {index} -> {0}.")
		@ValueSource(strings= {"", "a", "ABCDFGHIJKMNOPQRSTUVXWYZABCDEFGHIJKLMNOPQRSTUVXWYZ001abcdefg"})
		void invalidNameMinimumAndMaximumCharacters(String Name) {
			role.setName(Name);
			violations = validator.validateProperty(role, "name");
			assertFalse(violations.isEmpty());
		}
		
		@Order(4)
		@ParameterizedTest(name="Valid Test {index} -> {0}.")
		@ValueSource(strings= {
				"\t\nabcdefghijlm@nodsfgvdasgrdfgdafgLMNOPQRSTUVXYZ\t\n",
				"Lucas ramos silva",
				"dfghcvb",
				"gsdfgsd"})
		void validName(String Name) {
			role.setName(Name);
			violations = validator.validateProperty(role, "name");
			assertTrue(violations.isEmpty());
		}
	}
}
