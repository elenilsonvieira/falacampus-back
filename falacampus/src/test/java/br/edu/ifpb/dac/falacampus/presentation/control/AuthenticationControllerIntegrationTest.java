package br.edu.ifpb.dac.falacampus.presentation.control;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import br.edu.ifpb.dac.falacampus.presentation.dto.LoginDto;

public class AuthenticationControllerIntegrationTest {
	
	@Value("${password}")
	private String password;
	
	@Autowired
	private AuthenticationController authenticationController = new AuthenticationController()
	;

	@Test
	void loginTest() {
		
		LoginDto loginDto = new LoginDto();
		loginDto.setPassword(password);
		loginDto.setUsername("202025020004");
		
		ResponseEntity<String> response = authenticationController.login(loginDto);
		
		System.out.println(response);
}
}
