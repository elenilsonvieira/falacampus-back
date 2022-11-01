package test.equipe.nova;


import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.edu.ifpb.dac.falacampus.business.service.DepartamentConverterService;
import br.edu.ifpb.dac.falacampus.business.service.impl.DepartamentConverterServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;

import br.edu.ifpb.dac.falacampus.presentation.dto.DepartamentDto;



class DepartamentConverterServiceImplTest {
	
		
	@Autowired
	private static DepartamentConverterServiceImpl departamentConvertServiceImpl;
	
	@Autowired
	private static DepartamentDto dto;
	
	@Autowired
	private static Departament departament;


	@BeforeAll
	static void initAll() throws Exception {
		departamentConvertServiceImpl = new DepartamentConverterServiceImpl();
		departament = new Departament();
		departament.setId_responsavel("1");
		departament.setName("Direção Geral");
		departament.setSiglaDepartamento("DG");
	
	}
	@Test
	public void departamentToDTOTest() {
		
		dto = departamentConvertServiceImpl.departamentToDTO(departament);
		
		assertEquals(departament.getName(),dto.getName());
		assertEquals(departament.getId_responsavel() ,dto.getId_responsavel());
		assertEquals(departament.getSiglaDepartamento(), dto.getSiglaDepartamento());
		
		assertEquals(DepartamentDto.class, dto.getClass());
		
	}
	@Test
	public void dtoToDepartamentTest() {
		
		departament = departamentConvertServiceImpl.dtoToDepartament(dto);
		
		assertAll("dtoToDepartament", 
				() -> assertEquals(dto.getId(),departament.getId()),
				() -> assertEquals(dto.getName(),departament.getName()),
				() -> assertEquals(dto.getId_responsavel(), departament.getId_responsavel()),
				() -> assertEquals(dto.getSiglaDepartamento(),departament.getSiglaDepartamento()));
		
		assertEquals(Departament.class, departament.getClass());
	}	

}
