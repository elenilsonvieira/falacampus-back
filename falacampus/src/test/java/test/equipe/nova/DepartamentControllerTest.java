package test.equipe.nova;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.edu.ifpb.dac.falacampus.presentation.control.DepartamentController;
import br.edu.ifpb.dac.falacampus.presentation.dto.DepartamentDto;

class DepartamentControllerTest {

	@Test
	void testCriarDepartamentoSemNome() {
		DepartamentController c = new DepartamentController();
		DepartamentDto d = new DepartamentDto();
		d.setName("Direção");
		
		assertNull(c.save(null));
	}
	
	@Test
	void testCriarDepartamentoComId() {
		DepartamentController c = new DepartamentController();
		DepartamentDto d = new DepartamentDto();
		Long id = (long) 3;
		d.setId(id);
		d.setName(null);
	}

}
