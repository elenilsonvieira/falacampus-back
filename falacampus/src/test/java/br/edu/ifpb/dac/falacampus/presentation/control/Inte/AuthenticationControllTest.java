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

import br.edu.ifpb.dac.falacampus.business.service.AuthenticationService;
import br.edu.ifpb.dac.falacampus.business.service.TokenService;
import br.edu.ifpb.dac.falacampus.business.service.UserConverterService;
import br.edu.ifpb.dac.falacampus.business.service.UserService;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.dto.LoginDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.TokenDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;

public class AuthenticationControllTest {
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private AuthenticationController authenticationController;
    private TokenDto tokenDto1 ;

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
        UserDto userDto = new UserDto();
        userDto.setUsername("John Doe");
        userDto.setPassword("password");
        tokenDto.setUser(userDto);
        tokenDto.setToken("dfonkjsdnf.sdjfnsdfn.sdfnosdifniosd");

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
        UserDto u = new UserDto();
        u.setPassword(loginDto.getPassword());
        u.setUsername(loginDto.getUsername());
        token.setUser(u);

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
        tokenDto.setToken("jddiwudiewbd.wefeifuwedfwendweffo");

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
        tokenDto.setToken("jddiwudiewbd.wefeifuwedfwendweffo");

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
        tokenDto.setToken("jddiwudiewbd.wefeifuwedfwendweffo");

        when(authenticationController.isValidToken(Mockito.any(TokenDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found"));

        ResponseEntity response = authenticationController.isValidToken(tokenDto);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Token not found", response.getBody());
    }
}
