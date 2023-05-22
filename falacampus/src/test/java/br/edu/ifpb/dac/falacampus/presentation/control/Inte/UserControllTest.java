package br.edu.ifpb.dac.falacampus.presentation.control.Inte;

import br.edu.ifpb.dac.falacampus.business.service.impl.DepartamentService;
import br.edu.ifpb.dac.falacampus.business.service.impl.SystemRoleServiceImpl;
import br.edu.ifpb.dac.falacampus.business.service.impl.Unit.ConfigInterfaceUnitTest;
import br.edu.ifpb.dac.falacampus.business.service.impl.UserServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.SystemRole;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.control.UserController;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
public class UserControllTest implements ConfigInterfaceUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserController userController;
    @Autowired
    private DepartamentService departamentService;
    @Autowired
    private SystemRoleServiceImpl systemRoleService;
    private static SystemRole systemRole;
    @Autowired
    private static ObjectMapper objectMapper;
    private static Departament dep;
    private static UserDto userDto;

    @BeforeEach
    void up() {
        Departament departament = new Departament(null, "IT Department");
        dep = departamentService.save(departament);
        systemRole = systemRoleService.findAdmin();

    }

    @Test
    @Order(1)
    void userSaveControlTest() {
        UserDto author = new UserDto();
        author.setId(null);
        author.setName("John Doe");
        author.setPassword("password");
        author.setUsername("john");
        author.setEmail("john@example.com");
        author.setDepartamentId(dep.getId());
        List<SystemRole> roles = new ArrayList<>();
        roles.add(systemRole);
        author.setRoles(roles);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(author);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated()).andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();

            userDto = objectMapper.readValue(jsonResponse, UserDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNotNull(userDto);
        assertEquals(userDto.getDepartamentId(), author.getDepartamentId());
        assertEquals(userDto.getUsername(), author.getUsername());
        assertEquals(userDto.getEmail(), author.getEmail());
    }

    @Test
    @Order(2)
    void userSaveControlErroTest() {
        UserDto author = new UserDto();
        author.setId(null);
        author.setName("John Doe");
        List<SystemRole> roles = new ArrayList<>();
        roles.add(systemRole);
        author.setRoles(roles);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(author);


            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    @Order(3)
    void userUpdateControlTest() {
        UserDto author = new UserDto();
        author.setId(null);
        author.setName("John Doe");
        author.setPassword("password");
        author.setUsername("john");
        author.setEmail("john@example.com");
        author.setDepartamentId(dep.getId());
        List<SystemRole> roles = new ArrayList<>();
        roles.add(systemRole);
        author.setRoles(roles);
        userController.save(author);

        UserDto author2 = new UserDto();
        author2.setId(null);
        author2.setName("jose Doe");
        author2.setPassword("peassword");
        author2.setUsername("jeeohn");
        author2.setEmail("joeddhn@example.com");
        author2.setDepartamentId(dep.getId());
        author2.setRoles(roles);
        UserDto userDto = (UserDto) userController.save(author2).getBody();

        userDto.setUsername("Username2");
        userDto.setPassword("KMFENFO32r23r2");
        userDto.setDepartamentId(dep.getId());
        roles = new ArrayList<>();
        roles.add(systemRoleService.findTeacher());
        userDto.setRoles(roles);


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(userDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + userDto.getId()).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();

            userDto = objectMapper.readValue(jsonResponse, UserDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNotNull(userDto);

    }

    @Test
    @Order(4)
    void userUpdateControlErroTest() {
        UserDto author = new UserDto();
        author.setId(null);
        author.setName("John Doe");
        author.setPassword("password");
        author.setUsername("john");
        author.setEmail("john@example.com");
        author.setDepartamentId(dep.getId());
        List<SystemRole> roles = new ArrayList<>();
        roles.add(systemRole);
        author.setRoles(roles);
        userController.save(author);


        UserDto author2 = new UserDto();
        author2.setId(null);
        author2.setName("Zé");
        author2.setPassword("peassword");
        author2.setUsername("jeeohn");
        author2.setEmail("joeddhn@example.com");
        author2.setDepartamentId(dep.getId());
        author2.setRoles(roles);
        UserDto userDto = (UserDto) userController.save(author2).getBody();

        userDto.setName("A");
        userDto.setEmail("sememail");
        userDto.setPassword(null);
        userDto.setDepartamentId(dep.getId());
        roles = new ArrayList<>();
        roles.add(systemRoleService.findTeacher());
        userDto.setRoles(roles);


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(userDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + userDto.getId()).contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isBadRequest()).andReturn();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    @Order(5)
    void userDeleteControlTest() {
        UserDto author = new UserDto();
        author.setId(null);
        author.setName("Miguel");
        author.setPassword("password");
        author.setUsername("miguel15");
        author.setEmail("miguel@example.com");
        author.setDepartamentId(dep.getId());
        List<SystemRole> roles = new ArrayList<>();
        roles.add(systemRole);
        author.setRoles(roles);
        UserDto userDto = (UserDto) userController.save(author).getBody();

        try {
            mockMvc.perform(delete("/api/user/" + userDto.getId())).andExpect(status().isNoContent());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(6)
    void userDeleteControlErroTest() {

        try {
            mockMvc.perform(delete("/api/user/" + 3L)).andExpect(status().isBadRequest());

            mockMvc.perform(delete("/api/user/" + "String")).andExpect(status().isBadRequest());

            mockMvc.perform(delete("/api/user/" + null)).andExpect(status().isBadRequest());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(7)
    void userFindByIdTest() {
        UserDto author = new UserDto();
        author.setId(null);
        author.setName("Miguel");
        author.setPassword("password");
        author.setUsername("miguelzinho");
        author.setEmail("miguel@example.com");
        author.setDepartamentId(dep.getId());
        List<SystemRole> roles = new ArrayList<>();
        roles.add(systemRole);
        author.setRoles(roles);
        userController.save(author);

        try {

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + author.getId()).
                            contentType(MediaType.APPLICATION_JSON)).
                    andReturn();

            String responseBody = mvcResult.getResponse().getContentAsString();

            Assertions.assertNotNull(responseBody);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    @Order(8)
    void userFindByIdErroTest() {

        try {

            mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + null)
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

            mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + "String")
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

            mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + 2D)
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
@Test
@Order(9)
    void userFindAllTest() {
        UserDto author = new UserDto();
        author.setId(null);
        author.setName("Miguel");
        author.setPassword("password");
        author.setUsername("miguelzinho");
        author.setEmail("miguel@example.com");
        author.setDepartamentId(dep.getId());
        List<SystemRole> roles = new ArrayList<>();
        roles.add(systemRole);
        author.setRoles(roles);
        userController.save(author);

        UserDto author2 = new UserDto();
        author2.setId(null);
        author2.setName("Junior");
        author2.setPassword("password");
        author2.setEmail("junior@email.com");
        author2.setUsername("junin1020");
        author2.setDepartamentId(dep.getId());
        roles.add(systemRole);
        author2.setRoles(roles);
        userController.save(author2);

        try {
            objectMapper = new ObjectMapper();
            String jsonResponse = mockMvc.perform(get("/api/user/all").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();

            List<User> users = objectMapper.readValue(jsonResponse, new TypeReference<List<User>>() {
            });

            Assertions.assertTrue(users.size() > 1);

        } catch (Exception e) {

        }
    }
}
