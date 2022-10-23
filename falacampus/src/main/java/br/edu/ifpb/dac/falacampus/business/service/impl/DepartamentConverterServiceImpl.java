package br.edu.ifpb.dac.falacampus.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import br.edu.ifpb.dac.falacampus.business.service.ConverterService;
import br.edu.ifpb.dac.falacampus.business.service.DepartamentConverterService;
import br.edu.ifpb.dac.falacampus.business.service.DepartamentService;
import br.edu.ifpb.dac.falacampus.business.service.SuapService;
import br.edu.ifpb.dac.falacampus.business.service.TokenService;
import br.edu.ifpb.dac.falacampus.business.service.UserService;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.presentation.dto.DepartamentDto;

@Service
public class DepartamentConverterServiceImpl implements DepartamentConverterService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	//------------
	@Autowired
	private DepartamentService dS;
//=---------------
	@Autowired
	private SuapService suapService;

	@Autowired
	private ConverterService converterService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@Value("${app.logintype}")
	private String logintype;
	private String suapToken;
	
	//------------
	
	@Override
	public List<DepartamentDto> departamentToDTO(List<Departament> entities) {
		List<DepartamentDto> dtos = new ArrayList<>();
		
		for (Departament dto : entities) {
			DepartamentDto entity = departamentToDTO(dto);
			dtos.add(entity);
		}
		return dtos;
	}

	@Override
	public Departament dtoToDepartament(DepartamentDto dto) {
		
		Departament entity = modelMapper.map(dto, Departament.class);
		//Departament entity = new Departament();
		
		//entity.setId(dto.getId());
		//entity.setName(dto.getName());
		
		return entity;
	}

	@Override
	public DepartamentDto departamentToDTO(Departament entity) {
		
		DepartamentDto dto = modelMapper.map(entity, DepartamentDto.class);
		//DepartamentDto dto = new DepartamentDto();
		
		//dto.setId(entity.getId());
		//dto.setName(entity.getName());
		
		return dto;
	}
	
	//----------------------

	public void SalvarTodosOsDepartamentos(String token) {
		//Converter token
		
		try {
			
			this.suapToken = converterService.jsonToToken(suapService.findAllDepartament(token));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(this.suapToken == null) {
			throw new IllegalArgumentException();
		}
		String suapDepartamentJson = this.suapService.findAllDepartament(token);
		
		Departament departament = null;
	
		try {
			departament = converterService.jsonToDepartament(suapDepartamentJson);
			dS.save(departament);
			
			System.out.println(departament.getName());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
