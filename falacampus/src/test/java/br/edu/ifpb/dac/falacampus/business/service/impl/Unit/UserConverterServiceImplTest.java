package br.edu.ifpb.dac.falacampus.business.service.impl.Unit;

import br.edu.ifpb.dac.falacampus.business.service.impl.UserConverterServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;

public class UserConverterServiceImplTest implements ConfigInterfaceUnitTest {
    @Autowired
    private UserConverterServiceImpl userConverterService;
    @Autowired
    private ModelMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new ModelMapper();
        userConverterService.setModelMapper(mapper);
    }

    @Test
    @Order(1)
    public void testUserToDTOListValid() {
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setName("João Miguel");
        user1.setEmail("joaomiguel@gmail.com");
        user1.setUsername("usernamejoaomiguel");
        user1.setPassword("senha1234");
        user1.setDepartament(new Departament(1L, "IT Department"));
        userList.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Rafael Farias");
        user2.setEmail("rf@gmail.com");
        user2.setUsername("usernamerf");
        user2.setPassword("senha4321");
        user2.setDepartament(new Departament(1L, "IT Department"));
        userList.add(user2);

        List<UserDto> userDtoList = userConverterService.userToDTOList(userList);

        Assertions.assertNotNull(userDtoList);
        Assertions.assertEquals(user1.getName(), userDtoList.get(0).getName());
        Assertions.assertEquals(user1.getEmail(), userDtoList.get(0).getEmail());
        Assertions.assertEquals(user2.getName(), userDtoList.get(1).getName());
        Assertions.assertEquals(user2.getEmail(), userDtoList.get(1).getEmail());
    }

    @Test
    @Order(2)
    public void testUserToDTOListInvalid() {
        List<User> userList = null;
        assertThrows(NullPointerException.class, () -> {
            userConverterService.userToDTOList(userList);
        });
    }

    @Test
    @Order(3)
    public void testDtoToUserValid() {

        UserDto userDto = new UserDto();
        userDto.setName("José Miguel");
        userDto.setEmail("josemiguel@gmail.com");

        User userEsperado = new User();
        userEsperado.setName("José Miguel");
        userEsperado.setEmail("josemiguel@gmail.com");

        User userConvertido = userConverterService.dtoToUser(userDto);
        Assertions.assertNotNull(userConvertido);
        Assertions.assertEquals(userEsperado, userConvertido);
    }

    @Test
    @Order(4)
    public void testDtoToUserInvalid() {
        UserDto userDto = null;
        assertThrows(IllegalArgumentException.class, () -> {
            userConverterService.dtoToUser(userDto);
        });
    }

    @Test
    @Order(5)
    public void testUserToDTOValid() {

        User user = new User();
        user.setName("Maria José");
        user.setEmail("mariajose@gmail.com");

        UserDto userDtoEsperado = new UserDto();
        userDtoEsperado.setName("Maria José");
        userDtoEsperado.setEmail("mariajose@gmail.com");

        UserDto userDtoConvertido = userConverterService.userToDTO(user);
        Assertions.assertNotNull(userDtoConvertido);
        Assertions.assertEquals(userDtoEsperado.getName(), userDtoConvertido.getName());
        Assertions.assertEquals(userDtoEsperado.getEmail(), userDtoConvertido.getEmail());
    }

    @Test
    @Order(6)
    public void testUserToDTOInvalid() {
        User user = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userConverterService.userToDTO(user);
        });
    }
}