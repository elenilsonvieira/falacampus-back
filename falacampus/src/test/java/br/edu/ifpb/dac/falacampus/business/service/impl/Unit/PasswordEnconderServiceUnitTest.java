package br.edu.ifpb.dac.falacampus.business.service.impl.Unit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import br.edu.ifpb.dac.falacampus.business.service.impl.PasswordEnconderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifpb.dac.falacampus.model.entity.User;

public class PasswordEnconderServiceUnitTest {

    private PasswordEnconderServiceImpl passwordEncoder;
    private User user;

    @BeforeEach
    void setUp() {
        passwordEncoder = new PasswordEnconderServiceImpl();
        user = new User();
        user.setPassword("password");
    }

    @Test
    void encryptPasswordTest() {
        passwordEncoder.encryptPassword(user);

        assertEquals(60, user.getPassword().length());
    }

    @Test
    void encryptPasswordFailTest() {
        user.setPassword(null);
        passwordEncoder.encryptPassword(user);
        assertNull(user.getPassword());
    }

}

