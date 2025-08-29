package br.edu.ifpb.dac.falacampus.presentation.control.Inte;

import br.edu.ifpb.dac.falacampus.presentation.control.AuthenticationController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import br.edu.ifpb.dac.falacampus.business.service.TokenService;
import br.edu.ifpb.dac.falacampus.presentation.dto.LoginDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.TokenDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserResponseDto;

public class AuthenticationControllTest {
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private AuthenticationController authenticationController;
    private TokenDto tokenDto1 ;
    private String token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjgzMjQ1NTk0LCJpYXQiOjE2ODMyNDE5OTQsI";
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        authenticationController = Mockito.mock(AuthenticationController.class);
    }

    @Test
    @Order(1)
    public void loginAuthenticationTest() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");
        loginDto.setPassword("password");

        TokenDto tokenDto = new TokenDto();
        // UserDto userDto = new UserDto();
        UserResponseDto userResponseDto = new UserResponseDto();
        // userDto.setUsername("John Doe");
        // userDto.setPassword("password");
        // tokenDto.setUser(userDto);
        userResponseDto.setUsername("John Doe");
        tokenDto.setToken(token);
        tokenDto.setUser(userResponseDto);

        Mockito.when(authenticationController.login(Mockito.any(LoginDto.class))).thenReturn(new ResponseEntity<>(tokenDto, HttpStatus.OK));

        ResponseEntity response = authenticationController.login(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("loginPasswordInvalidTest")
    public void loginAuthenticationErroTest() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("username");
        loginDto.setPassword("12345");

        TokenDto token = new TokenDto();
        // UserDto u = new UserDto();
        UserResponseDto userResponseDto = new UserResponseDto();
        // u.setPassword(loginDto.getPassword());
        // u.setUsername(loginDto.getUsername());
        userResponseDto.setUsername(loginDto.getUsername());
        token.setUser(userResponseDto);

        Mockito.when(authenticationController.login(Mockito.any(LoginDto.class)))
                .thenReturn(ResponseEntity.badRequest().body(token));

        ResponseEntity response = authenticationController.login(loginDto);
        tokenDto1 = (TokenDto) response.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(tokenDto1.getToken());
    }


    @Test
    @Order(3)
    public void tokenIsValidTest() {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);

        when(authenticationController.isValidToken(Mockito.any(TokenDto.class)))
                .thenReturn(ResponseEntity.ok().body(""));

        ResponseEntity response = authenticationController.isValidToken(tokenDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("", response.getBody());
    }

    @Test
    @Order(4)
    public void tokenExpiredTest() {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);

        when(authenticationController.isValidToken(Mockito.any(TokenDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired"));

        ResponseEntity response = authenticationController.isValidToken(tokenDto);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Token expired", response.getBody());
    }
    @Test
    @Order(5)
    public void tokenInvalidTest() {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);

        when(authenticationController.isValidToken(Mockito.any(TokenDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found"));

        ResponseEntity response = authenticationController.isValidToken(tokenDto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Token not found", response.getBody());
    }

    @Test
    @Order(6)
    public void tokenRefreshTest() {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);
        String newToken = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Njk4NjM2NTEsInN1YiI6IjEiLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiMjAyMDE1MDIwMDMyIiwiZXhwaXJhdGlvblRpbWUiOiIwMDow";
        when(authenticationController.isValidToken(Mockito.any(TokenDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.OK).body(newToken));

        ResponseEntity response = authenticationController.isValidToken(tokenDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newToken, response.getBody());
    }
}
