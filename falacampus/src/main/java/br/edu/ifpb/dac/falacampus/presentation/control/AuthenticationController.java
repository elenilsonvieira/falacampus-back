package br.edu.ifpb.dac.falacampus.presentation.control;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import br.edu.ifpb.dac.falacampus.business.service.AuthenticationService;
import br.edu.ifpb.dac.falacampus.business.service.TokenService;
import br.edu.ifpb.dac.falacampus.business.service.UserConverterService;
import br.edu.ifpb.dac.falacampus.business.service.UserService;

import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.dto.LoginDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.TokenDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserResponseDto;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private UserConverterService userConverterService;
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;

	// @PostMapping("/login")
	// public ResponseEntity login(@RequestBody LoginDto dto) {
	// 	try {

	// 		String token = authenticationService.login(dto.getUsername(), dto.getPassword());
	// 		User entity = userService.findByUserName(dto.getUsername());
	// 		UserDto user = userConverterService.userToDTO(entity); 
	// 		TokenDto tokenDto = new TokenDto(token, user);
			
	// 		return new ResponseEntity(tokenDto, HttpStatus.OK);
			
	// 	} catch (Exception e) {
	// 		return ResponseEntity.badRequest().body(e.getMessage());
	// 	}
	// }

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginDto dto) {
		try {

			String token = authenticationService.login(dto.getUsername(), dto.getPassword());
			User entity = userService.findByUserName(dto.getUsername());
			UserResponseDto user = userConverterService.userToResponseDto(entity); 
			TokenDto tokenDto = new TokenDto(token, user);
			
			return new ResponseEntity(tokenDto, HttpStatus.OK);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// @PostMapping("/isValidToken")
	// public ResponseEntity isValidToken(@RequestBody TokenDto tokenDto) {

	// 	try {
	// 		boolean isValidToken = tokenService.isValid(tokenDto.getToken());

	// 		return new ResponseEntity(isValidToken, HttpStatus.OK);

	// 	} catch (Exception e) {
	// 		return ResponseEntity.badRequest().body(e.getMessage());
	// 	}
	// }

	@PostMapping("/isValidToken")
	public ResponseEntity isValidToken(@RequestBody TokenDto tokenDto) {

		try {
			boolean isValidToken = tokenService.isValid(tokenDto.getToken());
			if(isValidToken){
				String userName = tokenService.getUsername(tokenDto.getToken());
				User entity = userService.findByUserName(userName);
				UserResponseDto user = userConverterService.userToResponseDto(entity); 

				TokenDto response = new TokenDto(tokenDto.getToken(), user);

				return ResponseEntity.ok(response);
			}
			
			return new ResponseEntity("Token inválido", HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
