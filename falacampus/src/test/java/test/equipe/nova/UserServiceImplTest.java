package test.equipe.nova;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.edu.ifpb.dac.falacampus.business.service.DepartamentService;
import br.edu.ifpb.dac.falacampus.business.service.impl.DepartamentConverterServiceImpl;
import br.edu.ifpb.dac.falacampus.business.service.impl.UserConverterServiceImpl;
import br.edu.ifpb.dac.falacampus.business.service.impl.UserServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.dto.DepartamentDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;


class UserServiceImplTest {

	@Autowired
	private static User user;
	
	private static Departament departament;
	
	@Autowired
	private static UserServiceImpl userServeImp;
	
	@Autowired
	private static DepartamentService departamentS;
	
	@Autowired
	private DepartamentConverterServiceImpl departamentConve;
	
	@Autowired
	private static UserConverterServiceImpl userCon;

	
	@BeforeAll
	static void initAll() {
		//Para criar um user, é obrigatorio 
		//nome (min=2 e max = 50)
		//email (@____.___
		//password
		//departamentId (tem q passar o id de uma departamento ja existente)
		//registration (pode ser branco)
		user = new User();
		user.setName("Tarcizo");
		user.setEmail("tarcizo@gmail.com");
		user.setPassword("123");
		
		departament = new Departament();
		departament.setName("Diretor");
		departament.setSiglaDepartamento("DG");
		
		departamentS.save(departament);
		
		user.setDepartament(departament);
		
		//userServeImp.save(user);
		
	}
	
	@Test
	void converterUserParaUserDto() {
		userCon = new UserConverterServiceImpl();
		UserDto dto = userCon.userToDTO(user);
		
		assertEquals(dto.getName(), user.getName());
		assertEquals(dto.getEmail(), user.getEmail());
		assertEquals(dto.getRegistration(), user.getRegistration());
		
		assertEquals(UserDto.class, dto.getClass());
	}
	
	@Test
	void converterDepartamentToDto() {
		departamentConve = new DepartamentConverterServiceImpl();
		
		DepartamentDto dto = new DepartamentDto();
		dto = departamentConve.departamentToDTO(departament);
		
		assertEquals(dto.getName(), departament.getName());
		assertEquals(dto.getSiglaDepartamento(), departament.getSiglaDepartamento());
		
		assertEquals(DepartamentDto.class, dto.getClass());
	}

	
	@Test
	void salvarUser() {
		
		assertDoesNotThrow(()->userServeImp.save(user));
	}
}
