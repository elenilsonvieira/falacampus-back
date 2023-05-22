package br.edu.ifpb.dac.falacampus.presentation.control.Inte;
import br.edu.ifpb.dac.falacampus.business.service.impl.DepartamentService;
import br.edu.ifpb.dac.falacampus.business.service.impl.SystemRoleServiceImpl;
import br.edu.ifpb.dac.falacampus.business.service.impl.Unit.ConfigInterfaceUnitTest;
import br.edu.ifpb.dac.falacampus.business.service.impl.UserServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.SystemRole;
import br.edu.ifpb.dac.falacampus.presentation.control.UserController;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
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

    private static Departament dep;

    private static UserDto userDto;
    @BeforeEach
    void up(){
        Departament departament = new Departament(null, "IT Department");
        dep = departamentService.save(departament);
        systemRole = systemRoleService.findAdmin();

    }


    @Test
    @Order(1)
    void userSaveControlTest(){
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

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isCreated())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();

            userDto = objectMapper.readValue(jsonResponse, UserDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNotNull(userDto);
        assertEquals(userDto.getDepartamentId(), author.getDepartamentId());
        assertEquals(userDto.getUsername(),author.getUsername());
        assertEquals(userDto.getEmail(), author.getEmail());
    }

    @Test
    @Order(2)
    void userSaveControlErroTest(){
        UserDto author = new UserDto();
        author.setId(null);
        author.setName("John Doe");
        List<SystemRole> roles = new ArrayList<>();
        roles.add(systemRole);
        author.setRoles(roles);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(author);


            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andReturn();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
    @Test
    @Order(3)
    void userUpdateControlTest(){
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
        UserDto userDto  = (UserDto) userController.save(author2).getBody();

        userDto.setUsername("Username2");
        userDto.setPassword("KMFENFO32r23r2");
        userDto.setDepartamentId(dep.getId());
        roles = new ArrayList<>();
        roles.add(systemRoleService.findTeacher());
        userDto.setRoles(roles);


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(userDto);

            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + userDto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();

            userDto = objectMapper.readValue(jsonResponse, UserDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertNotNull(userDto);

    }
}
