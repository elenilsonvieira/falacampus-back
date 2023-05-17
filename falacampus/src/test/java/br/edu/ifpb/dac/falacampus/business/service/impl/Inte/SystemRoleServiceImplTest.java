package br.edu.ifpb.dac.falacampus.business.service.impl.Inte;

import br.edu.ifpb.dac.falacampus.business.service.impl.SystemRoleServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.SystemRole;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SystemRoleServiceImplTest implements ConfigTestIntegrate {

    @Autowired
    private SystemRoleServiceImpl systemRoleService;

    @Test
    @Order(1)
    public void createDefaultValues() {

        systemRoleService.createDefaultValues();

        SystemRole studentsRole = systemRoleService.findByName("STUDENTS");
        assertNotNull(studentsRole);
        assertEquals("STUDENTS", studentsRole.getName());

        SystemRole adminRole = systemRoleService.findByName("ADMIN");
        assertNotNull(adminRole);
        assertEquals("ADMIN", adminRole.getName());

        SystemRole technicianRole = systemRoleService.findByName("TECHNICIAN");
        assertNotNull(technicianRole);
        assertEquals("TECHNICIAN", technicianRole.getName());

        SystemRole teacherRole = systemRoleService.findByName("TEACHER");
        assertNotNull(teacherRole);
        assertEquals("TEACHER", teacherRole.getName());
    }
}