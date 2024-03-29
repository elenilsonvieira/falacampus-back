 package br.edu.ifpb.dac.falacampus.presentation.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.connector.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.edu.ifpb.dac.falacampus.business.service.DepartamentConverterService;
import br.edu.ifpb.dac.falacampus.business.service.UserConverterService;
import br.edu.ifpb.dac.falacampus.business.service.impl.CommentService;
import br.edu.ifpb.dac.falacampus.business.service.impl.DepartamentConverterServiceImpl;
import br.edu.ifpb.dac.falacampus.business.service.impl.DepartamentService;
import br.edu.ifpb.dac.falacampus.business.service.impl.UserServiceImpl;
import br.edu.ifpb.dac.falacampus.exceptions.CommentCannotUpdateException;
import br.edu.ifpb.dac.falacampus.model.entity.Comment;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.enums.StatusComment;
import br.edu.ifpb.dac.falacampus.presentation.dto.DepartamentDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.DetailsCommentDto;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;

import static br.edu.ifpb.dac.falacampus.business.service.SuapService.DEPARTAMENTS_URL_MONTEIRO;

 @RestController
@RequestMapping("/api/departament")
public class DepartamentController {

	@Autowired
	private DepartamentConverterService departamentConvertService;

	@Autowired
	private DepartamentService departamentService;
	
	@Autowired
	private UserServiceImpl userS;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private DepartamentConverterServiceImpl departamentConverterServiceImpl;

	@PostMapping
	public ResponseEntity save(@RequestBody @Valid DepartamentDto dto) {
		try {
			Departament entity = departamentConvertService.dtoToDepartament(dto);

			entity = departamentService.save(entity);

			dto = departamentConvertService.departamentToDTO(entity);

			return new ResponseEntity(dto, HttpStatus.CREATED);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable("id") Long id, @RequestBody @Valid DepartamentDto dto) {
		try {
			Departament departament = departamentConvertService.dtoToDepartament(dto);
			ArrayList<User> users = new ArrayList<>();
			if(dto.getResponsibleUsers() != null) {
				for (String user : dto.getResponsibleUsers()) {
					User o = userS.findById(Long.parseLong( user));
					if(o==null) {
						throw new NullPointerException("Id do usuario não encontrado");
					}else {
					users.add(o);
					}
				}
			}
			departament.setResponsibleUsers(users);
			departament = departamentService.update(departament);
			dto = departamentConvertService.departamentToDTO(departament);

			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());

		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable("id") Long id) {
		try {
//			 
			departamentService.deleteById(id);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		

		}
	}


	@GetMapping
	public ResponseEntity findByFilter(@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "name", required = false) String name) {

		try {

			Departament departamento = new Departament();
			departamento.setId(id);
			departamento.setName(name);

			List<Departament> entities = departamentService.find(departamento);
			List<DepartamentDto> dtos = departamentConvertService.departamentToDTO(entities);
			return ResponseEntity.ok(dtos);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public Departament findById(@PathVariable("id") Long id) throws Exception {

		Departament result = departamentService.findById(id);

		if (result == null) {
			throw new Exception("Departament not exist!");

		} else {
			return result;
		}
	}
	
	@GetMapping("/getDepartmentsApi")
	public void getDepartmentsApi() {
		departamentConverterServiceImpl.SaveAllDepartments(DEPARTAMENTS_URL_MONTEIRO);
	}
	
	private DepartamentDto mapToDepartamentDto(Departament departament) {
		return mapper.map(departament, DepartamentDto.class);
	}
	
	// FIND ALL
	@GetMapping("/all")
	public ResponseEntity<?> findAll() throws Exception {

		List<DepartamentDto> dtos = departamentService.findAll().stream().map(this::mapToDepartamentDto).toList();

		return ResponseEntity.ok(dtos);

	}
	
	@GetMapping("/responsables/{id}")
	public ResponseEntity isResponsables(@PathVariable("id") Long id){
		List <Departament> deps = new ArrayList<>();
		try {			
			List<Departament> dp = departamentService.findAll();

			for (int i = 0; i < dp.size(); i++)  {
				for(int j = 0; j<dp.get(i).getResponsibleUsers().size();j++) {
					User user = dp.get(i).getResponsibleUsers().get(j);
					if(user.getId().equals(id)){
						deps.add(dp.get(i));		
					}
				}
			}		
			System.out.println(deps);
			List<DepartamentDto> dtos = departamentConvertService.departamentToDTO(deps);
			return ResponseEntity.ok(dtos);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}