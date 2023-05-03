package br.edu.ifpb.dac.falacampus.model.entityDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.dto.DepartamentDto;

public class DepartamentDtoTest {
	@Autowired
	private static DepartamentDto departamentDto;

	@BeforeAll
	public static void setUp() {
		departamentDto = new DepartamentDto();
		departamentDto.setId(1L);
		departamentDto.setName("Finanças");
		departamentDto.setAcronymDepartment("is True");	
	    ArrayList<User> names = new ArrayList<>();
	    names.add(new User());
		departamentDto.setResponsibleUsers(names);
		
	}
	
	@Test
	public void gets() {
		assertEquals(1L, departamentDto.getId());
		assertEquals("Finanças", departamentDto.getName());
		assertEquals("is True", departamentDto.getAcronymDepartment());
	}
	
	@Test
	public void converter() {
		Departament departamen = new Departament();
		
		departamen.setId(8L);
		departamen.setName("Musica");
		departamen.setAcronymDepartment("PAZ");
		ArrayList<User> users = new ArrayList<>();
		users.add(new User());
		departamen.setUsers(users);

		List<Departament> departamens = new ArrayList<>();
		departamens.add(departamen);

		List<DepartamentDto> departamenDtos = DepartamentDto.toConvert(departamens);
	
		assertEquals(1, departamenDtos.size());
		assertEquals(departamen.getId(), departamenDtos.get(0).getId());
		assertEquals(departamen.getName(), departamenDtos.get(0).getName());
		assertEquals(departamen.getAcronymDepartment(), departamenDtos.get(0).getAcronymDepartment());
		
	}
}

	
	

