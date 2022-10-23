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
<<<<<<< HEAD
		
=======
	

	//------------
	private String id_responsavel;
	
	private String siglaDepartamento;
	//------------
	
>>>>>>> 1f128a960bd304c4b92f4cb162da636c0daa7834
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
	
	public String getId_responsavel() {
		return id_responsavel;
	}

	public void setId_responsavel(String id_responsavel) {
		this.id_responsavel = id_responsavel;
	}

	public String getSiglaDepartamento() {
		return siglaDepartamento;
	}

	public void setSiglaDepartamento(String siglaDepartamento) {
		this.siglaDepartamento = siglaDepartamento;
	}
}
