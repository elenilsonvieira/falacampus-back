package br.edu.ifpb.dac.falacampus.presentation.control;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.edu.ifpb.dac.falacampus.business.service.AuthenticationService;
import br.edu.ifpb.dac.falacampus.business.service.impl.AuthenticationServiceImpl;
import br.edu.ifpb.dac.falacampus.presentation.dto.LoginDto;
@AutoConfigureMockMvc
@SpringBootTest
class AuthenticationControllerIntegrationTest {
	
	
	@Value("${password}")
	private String password;
	
	
	@Autowired
	private static AuthenticationController authenticationController;
	
	@Autowired
	private static LoginDto loginDto;
	
    private static MockMvc mockMvc;

    //Instância do mock repository
    @Mock
    private AuthenticationService authenticationService;
	
	 @BeforeAll
	 static void iniciar() {
		 authenticationController = new AuthenticationController();
//		 mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
				
		 
	   }
//	
//	@Test
//	void loginTest() {
//		
//		loginDto = new LoginDto();
//		loginDto.setUsername("202025020004");
//		loginDto.setPassword("Thata1991#");
//		
//		ResponseEntity<String> response = authenticationController.login(loginDto);
//		System.out.println(response);
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		
//		
//}
	 

		@Test
		void loginTest() {
			
			loginDto = new LoginDto();
			loginDto.setUsername("202025020004");
			loginDto.setPassword(password);
			ResponseEntity<String> response = authenticationController.login(loginDto);
			System.out.println(response);
			assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());		
			
	}
}
