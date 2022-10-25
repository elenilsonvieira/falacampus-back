package br.edu.ifpb.dac.falacampus.presentation.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ifpb.dac.falacampus.model.entity.Departament;

public class DepartamentDto {
	
	private Long id;
	
	@NotNull @NotEmpty @Size(min = 2, max=100)
	private String name;


	//------------
	private String id_responsible;
	
	private String acronymDepartment;
	//------------
	
	

	public DepartamentDto() {
		
	}

	public DepartamentDto(Departament departament) {
		this.id = departament.getId();
		this.name = departament.getName();
	}
	
	public static List<DepartamentDto> convert(List<Departament> departament){
		return departament.stream().map(DepartamentDto::new).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getId_responsible() {
		return id_responsible;
	}

	public void setId_responsible(String id_responsible) {
		this.id_responsible = id_responsible;
	}

	public String getAcronymDepartment() {
		return acronymDepartment;
	}

	public void setAcronymDepartment(String acronymDepartment) {
		this.acronymDepartment = acronymDepartment;
	}


}
