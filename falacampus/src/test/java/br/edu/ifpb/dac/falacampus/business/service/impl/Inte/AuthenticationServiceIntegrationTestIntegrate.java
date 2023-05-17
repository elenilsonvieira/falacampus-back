package br.edu.ifpb.dac.falacampus.business.service.impl.Inte;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import br.edu.ifpb.dac.falacampus.business.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.edu.ifpb.dac.falacampus.model.entity.User;

@Testable
@DisplayName("AuthenticationService")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationServiceIntegrationTestIntegrate implements ConfigTestIntegrate {

    @Mock
    private AuthenticationServiceImpl authentication;
    @Mock
    private User userMock;
    
    @Order(1)
    @Test
    public void validLoginTest() {
        Mockito.when(authentication.login("202015130001", "@Testes")).thenReturn("validToken");
        String token = authentication.login("202015130001", "@Testes");
        assertNotNull(token);
    }

    @Order(2)
    @Test
    public void getLoggedTest() {
        Mockito.when(authentication.getLoggedUser()).thenReturn(userMock);
        User loggedUser = authentication.getLoggedUser();
        assertNotNull(loggedUser);
    }
    
    @Order(3)
    @Test
    public void invalidLoginTest() {
        Mockito.when(authentication.login("usuario", "senhaErrada")).thenReturn(null);
        String token = authentication.login("usuario", "senhaErrada");
        assertNull(token);
    }
    
    @Order(4)
    @Test
    public void getLoggedNullTest() {
        Mockito.when(authentication.getLoggedUser()).thenReturn(null);
        User loggedUser = authentication.getLoggedUser();
        assertNull(loggedUser);
    }
}
