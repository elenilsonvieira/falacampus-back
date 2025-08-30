package br.edu.ifpb.dac.falacampus.presentation.control;

import br.edu.ifpb.dac.falacampus.business.service.UserConverterService;
import br.edu.ifpb.dac.falacampus.business.service.UserService;
import br.edu.ifpb.dac.falacampus.business.service.impl.DepartamentService;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.presentation.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserConverterService userConverterService;

    @Autowired
    private UserService userService;
    @Autowired
    private DepartamentService departamentService;

    //SAVE
    @PostMapping
    public ResponseEntity save(@RequestBody UserDto dto) {

        try {
            if (dto.getDepartamentId() == null) {
                throw new IllegalStateException("departamentId cannot be null");
            }

            Long departamentId = dto.getDepartamentId();
            Departament departament = departamentService.findById(departamentId);

            if (departament == null) {
                throw new IllegalStateException(String.format("Could not find any departament with id=%1", departamentId));
            }

            User entity = userConverterService.dtoToUser(dto);
            entity.setDepartament(departament);
            entity = userService.save(entity);

            dto = userConverterService.userToDTO(entity);

            return new ResponseEntity(dto, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //PUT
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody UserDto dto) {
        try {
            dto.setId(id);

            if (dto.getDepartamentId() == null) {
                throw new IllegalStateException("departamentId cannot be null");
            }

            Long departamentId = dto.getDepartamentId();
            Departament departament = departamentService.findById(departamentId);

            if (departament == null) {
                throw new IllegalStateException(String.format("Could not find any departament with id=%1", departamentId));
            }

            User entity = userConverterService.dtoToUser(dto);
            entity.setDepartament(departament);


            entity = userService.update(entity);
            dto = userConverterService.userToDTO(entity);

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //DELETE
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            userService.delete(id);
            //.deleteById(id);

            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //FIND FILTER
    @GetMapping
    public ResponseEntity findByFilter(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "username", required = false) String username) {
        //@RequestParam(value = "role", required = false) Role role,
        //	@RequestParam(value = "departamentId", required = false) Long departamentId) {

        try {

            User filter = new User();
            filter.setId(id);
            filter.setName(name);
            filter.setEmail(email);
            filter.setUsername(username);
            //filter.setRole(role);
            //.setRole(role);

//			Departament departament = departamentService.findById(departamentId);
//			
//			if(departament == null) {
//				throw new IllegalStateException(String.format("Could not find any departament whit id=%1", departamentId));
//			}
//			
//			filter.setDepartament(departament);

            List<User> entities = (List<User>) userService.find(filter);
            //.find(filter);
            List<UserDto> dtos = userConverterService.userToDTOList(entities);

            return ResponseEntity.ok(dtos);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //FIND ALL
    // @GetMapping("/all")
    // public List<User> findAll() throws Exception {

    //     List<User> result = (List<User>) userService.findAll();

    //     if (result.isEmpty()) {
    //         throw new Exception("List is empty!");

    //     } else {
    //         return (List<User>) userService.findAll();
    //     }
    // }
    @GetMapping("/all")
    public List<UserDto> findAll() throws Exception {

        List<User> result = (List<User>) userService.findAll();
        List<UserDto> dtos = userConverterService.userToDTOList(result);

        if (dtos.isEmpty()) {
            throw new Exception("List is empty!");
        } else {
            return (List<UserDto>) dtos;
        }
    }
//	@GetMapping("/users")
//	public ResponseEntity execute(
//	         		 @PageableDefault(sort = "name",
//	                 //direction = Sort.Direction.ASC,
//	                 page = 0,
//	                 size = 10) Pageable page){
//
//	      return ResponseEntity.ok(userService.execute(page));
//	  }


    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) throws Exception {

        User result = userService.findById(id);

        if (result == null) {
            return ResponseEntity.badRequest().body(new Exception("User not exist!"));
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

}
