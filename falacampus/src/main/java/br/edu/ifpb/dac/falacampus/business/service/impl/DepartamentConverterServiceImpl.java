package br.edu.ifpb.dac.falacampus.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import br.edu.ifpb.dac.falacampus.business.service.DepartamentConverterService;
import br.edu.ifpb.dac.falacampus.business.service.SuapService;
import br.edu.ifpb.dac.falacampus.business.service.TokenService;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.presentation.dto.DepartamentDto;

@Service
public class DepartamentConverterServiceImpl implements DepartamentConverterService {
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private DepartamentService departamentService;
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
	private Departament departament;

	
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
		return entity;
	}

	@Override
	public DepartamentDto departamentToDTO(Departament entity) {
		
		DepartamentDto dto = modelMapper.map(entity, DepartamentDto.class);
		
		return dto;
	}
	
	
	public void SaveAllDepartments(String url) {
	    // Converter token
	    try {
	        this.suapToken = converterService.jsonToTokenDepartament(suapService.findAllDepartament(url));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    if (this.suapToken == null) {
	        throw new IllegalArgumentException();
	    }

	    String suapDepartamentJson = this.suapService.findAllDepartament(url);

	    try {
	        JsonObject result = converterService.jsonToDepartament(suapDepartamentJson);
	        Departament departament = new Departament();

	        String name = result.get("nome").getAsString();
	        departament.setName(name);

	        String initials = result.get("sigla").getAsString();
	        departament.setAcronymDepartment(initials);
	       
	        if(!name.equals("CAMPUS MONTEIRO")) {
	        	if(departamentService.findByName(name) == null){
					departamentService.save(departament);
				}
	        }
			

	        JsonArray childSectors = result.get("setores_filho").getAsJsonArray();
	        List<CompletableFuture<Void>> futures = new ArrayList<>();

	        for (JsonElement jsonElement : childSectors) {
	            String childSectorUrl = jsonElement.toString();
	            futures.add(CompletableFuture.runAsync(() -> SaveAllDepartments(childSectorUrl)));
	        }

	        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
