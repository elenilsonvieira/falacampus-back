package br.edu.ifpb.dac.falacampus.model.entity;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("Departament")
class DepartamentTest {
	@Autowired
	private static Validator validator;
    private Departament departament;
    private Set<ConstraintViolation<Departament>> violations;
    
    @BeforeEach
    void setUp() throws Exception {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);    
        user1.setName("joao testador");
        users.add(user1);
        
        departament = new Departament(1L, "Department Name", users);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    validator = factory.getValidator();
    }

    @Test
    void constructor() {
        assertNotNull(departament);
        assertEquals(1L, departament.getId());
        assertEquals("Department Name", departament.getName());
        assertEquals(1, departament.getUsers().size());
        assertEquals("joao testador", departament.getUsers().get(0).getName());
        assertThrows(IndexOutOfBoundsException.class, () -> departament.getUsers().get(1));
    }
    
    
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	class NameTest{
		
		@Order(1)
		@Test
		void nullName() {
			departament.setName(null);
			violations = validator.validateProperty(departament, "name");
			assertFalse(violations.isEmpty());
			
		}
		
		@Order(2)
		@ValueSource(strings= {"","    ", "        "})
		void invalidNameEmpty(String departamentName) {
			departament.setName(departamentName);
;			violations = validator.validateProperty(departament, "name");
			assertFalse(violations.isEmpty());
		
		}
		
		@Order(3)
		@DisplayName("Name Character Limits")
		@ParameterizedTest(name="Invalid Test {index} -> {0}.")
		@ValueSource(strings= {"", "a", "ABCDFGHIJKMNOPQRSTUVXWYZABCDEFGHIJKLMNOPQRSTUVXWYZ001abcdefg"
		+"ABCDFGHIJKMNOPQRSTUVXWYZABCDEFGHIJKLMNOPQRSTUVXWYZ001abcdefg"})
		void invalidNameMinimumAndMaximumCharacters(String departamentName) {
			departament.setName(departamentName);
			violations = validator.validateProperty(departament, "name");
			assertFalse(violations.isEmpty());
		}
		
		@Order(4)
		@ParameterizedTest(name="Valid Test {index} -> {0}.")
		@ValueSource(strings= {
				"\t\nabcdefghijlm@nodsfgvdasgrdfgdafgLMNOPQRSTUVXYZ\t\n",
				"Lucas ramos silva",
				"dfghcvb",
				"gsdfgsd"})
		void validName(String departamentName) {
			departament.setName(departamentName);
			violations = validator.validateProperty(departament, "name");
			assertTrue(violations.isEmpty());
		}
		
	}
	

}
