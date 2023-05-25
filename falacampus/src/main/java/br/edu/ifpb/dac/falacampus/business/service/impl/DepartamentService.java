package br.edu.ifpb.dac.falacampus.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.dac.falacampus.model.entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.repository.DepartamentRepository;

@Service
public class DepartamentService {

	@Autowired
	private DepartamentRepository departamentRepository;


	public Departament findByName(String name) {
		try{
			if (name == null) {
				throw new Exception();
			}

			Departament departamentSalvo = departamentRepository.findByName(name);


			return departamentSalvo;

		}catch (Exception e){
			throw new IllegalStateException("Departament not Found");
		}
	}

	public Departament save(Departament departament) {
		if (departament == null) {
			throw new IllegalStateException("Departament not Found");
		}
		return departamentRepository.save(departament);
	}

	public void deleteById(Long id) {

		Departament departament = findById(id);

		if (departament == null) {
			throw new IllegalStateException(String.format("Could not find a entity with id=%1", id));
		}

		departamentRepository.deleteById(id);
	}

	public Departament update(Departament departament) {

		Departament departamentUp = findById(departament.getId());
		departamentUp.setName(departament.getName());
		departamentUp.setResponsibleUsers(departament.getResponsibleUsers());

		return departamentRepository.save(departament);

	}

	public Departament update(Long id) {

		try{
			if (id == null) {
				throw new Exception();
			}

			Departament departamentSalvo = departamentRepository.getById(id);
			return departamentRepository.save(departamentSalvo);
		}catch (Exception e){
			throw new IllegalStateException("Id cannot be null");
		}

	}

	public Departament findById(Long id) {
		try{
			Departament dp = departamentRepository.findById(id).get();
			return dp;
		}catch (Exception e){
			throw new IllegalStateException("Id cannot be null");
		}
	}

	public List<Departament> findAll() {
		return departamentRepository.findAll();
	}

	public List<Departament> find(Departament filter) {
		Example example = Example.of(filter,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));

		return departamentRepository.findAll(example);
	}

	public void haveResponsible(Long id) throws Exception{
		 Departament dep = findById(id);
		 if(dep != null) {
			 if(dep.getResponsibleUsers().isEmpty()) {
				 throw new Exception("Responsible not exist!");
			 }
		 }
	}


}
