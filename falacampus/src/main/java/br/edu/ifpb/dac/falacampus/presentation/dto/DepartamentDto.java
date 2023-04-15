package br.edu.ifpb.dac.falacampus.presentation.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;

public class DepartamentDto {
	
	private Long id;
	
	@NotNull @NotEmpty @Size(min = 2, max=100)
	private String name;

	//------------
	private List<String> responsibleUsers;
	
	private String acronymDepartment;
	//------------
	
	

	public DepartamentDto() {
		
	}
	//Revisar isso com o professor, Não faz mt sentido se é pra passar uam lista de  objetos usuario ou so os nomes
	public DepartamentDto(Departament departament) {
	
	
		this.id = departament.getId();
		this.name = departament.getName();
		ArrayList<String> nameUsersResponse = new ArrayList<String>();
		
		 for(User a: departament.getResponsibleUsers()) {
			 nameUsersResponse.add(a.getName());
		 }
		this.responsibleUsers = nameUsersResponse;
		this.acronymDepartment = departament.getAcronymDepartment();
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

	public List<String> getResponsibleUsers() {
		return responsibleUsers;
	}

	public void setResponsibleUsers(List<String> responsibleUsers) {
		this.responsibleUsers = responsibleUsers;
	}

	public String getAcronymDepartment() {
		return acronymDepartment;
	}

	public void setAcronymDepartment(String acronymDepartment) {
		this.acronymDepartment = acronymDepartment;
	}

	public static List<DepartamentDto> toConvert(List<Departament> departaments){
		List<DepartamentDto> list = departaments.stream().map(DepartamentDto::new).collect(Collectors.toList());
		for(DepartamentDto d: list) {
			System.out.print(d.acronymDepartment + d.name);
		}
		return departaments.stream().map(DepartamentDto::new).collect(Collectors.toList());
	}
}
