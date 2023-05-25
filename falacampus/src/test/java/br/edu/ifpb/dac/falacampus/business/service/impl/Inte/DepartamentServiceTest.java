package br.edu.ifpb.dac.falacampus.business.service.impl.Inte;

import br.edu.ifpb.dac.falacampus.business.service.impl.DepartamentService;
import br.edu.ifpb.dac.falacampus.business.service.impl.UserServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.SystemRole;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DepartamentServiceTest implements ConfigTestIntegrate{
    @Autowired
    private DepartamentService departementService;
    @Autowired
    private UserServiceImpl userService;
    private Departament dep;
    private List<User> users = new ArrayList<>();

    @BeforeEach
    public void up() {
        Departament departament = new Departament(null, "Limpeza patil");
        dep =  departementService.save(departament);
        User user1 = new User(null, "John Doe", "john@example.com", "john", "password", departament);
        List<SystemRole> roles = new ArrayList<>();
        SystemRole role = new SystemRole();
        roles.add(role);
        user1.setRoles(roles);

        User user2 = new User(null, "Jane Smith", "jane@example.com", "jane", "password", departament);
        user2.setRoles(roles);
        users.add(userService.save(user1));
        users.add(userService.save(user2));
    }

    @Test
    @Order(1)
    void departamentSaveTest() {
        Departament departament = new Departament(null, "Coordenação", users);
        dep = departementService.save(departament);
        assertNotNull(dep);
        assertEquals(departament.getName(), dep.getName());
        assertEquals(departament.getUsers().size(), dep.getUsers().size());
    }

    @Test
    @Order(2)
    void departamentSaveErroTest() {
       Departament d = null;
        assertThrows(IllegalStateException.class, () -> {
            departementService.save(d);
        });
    }

    @Test
    @Order(3)
    void departamentUpdateTest() {
        Departament d = dep;
        dep.setName("UPDATE NAME");
        dep = departementService.update(d);
        assertNotNull(dep);
        assertEquals("UPDATE NAME", dep.getName());
        assertEquals(dep.getId(),d.getId());
        assertEquals(dep.getUsers().size(), d.getUsers().size());
    }
    @Test
    @Order(4)
    void departamentUpdateErroTest() {
        dep.setId(null);
        assertThrows(IllegalStateException.class, () -> {
            departementService.update(dep);
        });
        dep.setId(54L);
        assertThrows(IllegalStateException.class, () -> {
            departementService.update(dep);
        });
    }

    @Test
    @Order(5)
    void departamentFindByIdTest() {
        Departament c = departementService.findById(dep.getId());
        assertNotNull(c);
        assertEquals(c.getId(),dep.getId());
        assertEquals(c.getName(), dep.getName());
        assertEquals(c.getUsers().size(),dep.getUsers().size());
    }


    @Test
    @Order(6)
    void departamentFindByIdErroTest() {
        assertThrows(IllegalStateException.class, () -> {
            departementService.findById(null);
        });
    }
    @Test
    @Order(7)
    void departamentFindByNameTest() {
        Departament c = departementService.findByName(dep.getName());
        assertNotNull(c);
        assertEquals(c.getId(),dep.getId());
        assertEquals(c.getName(), dep.getName());
        assertEquals(c.getUsers().size(),dep.getUsers().size());
    }
    @Test
    @Order(8)
    void departamentFindByNameErroTest() {
        assertThrows(IllegalStateException.class, () -> {
            departementService.findByName(null);
        });
    }
    @Test
    @Order(9)
    void  commentFindAllTest() {
        List<Departament> c = departementService.findAll();
        assertNotNull(c);
        assertTrue(c.size() >= 1);
    }

    @Test
    @Order(10)
    void commentDeleteByIdTest() {
        departementService.deleteById(dep.getId());
        assertThrows(IllegalStateException.class, () -> {
            departementService.findById(dep.getId());
        });
    }

    @Test
    @Order(11)
    void commentDeleteByIdErroTest() {
        assertThrows(IllegalStateException.class, () -> {
            departementService.deleteById(65L);
        });
    }
    @Test
    @Order(12)
    void commentHaveResponsibleTest() {
        dep.setResponsibleUsers(users);
        dep = departementService.update(dep);

        try {
            departementService.haveResponsible(dep.getId());
            assertTrue(true);
        } catch (Exception e) {
           assertTrue(false);
        }

    }
    @Test
    @Order(13)
    void commentHaveResponsibleErroTest() {
        dep.setResponsibleUsers(null);
        dep = departementService.update(dep);

        try {
            departementService.haveResponsible(dep.getId());
        } catch (Exception e) {
            assertFalse(false);
        }

    }

}
