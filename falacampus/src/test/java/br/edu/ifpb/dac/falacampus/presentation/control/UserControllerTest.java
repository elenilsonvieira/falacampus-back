package br.edu.ifpb.dac.falacampus.presentation.control;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.TestSecurityContextHolder;

import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.dto.LoginDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

	@Autowired
	private AuthenticationController authenticationController;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private static User user;

	
	@BeforeEach
	void setUp() throws Exception {
		user = new User();
		
	}


	@Test
	void testNull() {
	      assertNotNull(userController);
		
	}
	@Test
	void getTest() throws Exception {
		user = userController.findById(1L);
		
		assertEquals("Thallyta Maria Medeiros Silva Pereira", user.getName());
		
	}
	@Test
	void saveHttpTest() {
		user.setDepartament(new Departament("Dep 1"));
		UserDto userDto = new UserDto(user);

		ResponseEntity<String> response = userController.save(userDto);
		System.out.println(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());	
		
//		UserDto userDto = new UserDto(user);
//		
//		assertDoesNotThrow(() -> userController.save(userDto));

	}
	@Test
	void saveTest() {
		user.setDepartament(new Departament("Dep 1"));
		UserDto userDto = new UserDto(user);

		assertDoesNotThrow(() -> userController.save(userDto));
	}
	@Test
	void updateTest() throws Exception {
		user = userController.findById(1L);
		
		assertEquals("Thallyta Maria Medeiros Silva Pereira", user.getName());
		
	}
	
	

}
