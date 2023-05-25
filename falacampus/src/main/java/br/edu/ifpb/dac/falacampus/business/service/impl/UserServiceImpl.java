package br.edu.ifpb.dac.falacampus.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifpb.dac.falacampus.business.service.PasswordEnconderService;
import br.edu.ifpb.dac.falacampus.business.service.SystemRoleService;
import br.edu.ifpb.dac.falacampus.business.service.UserService;
import br.edu.ifpb.dac.falacampus.model.entity.SystemRole;
import br.edu.ifpb.dac.falacampus.model.entity.User;
import br.edu.ifpb.dac.falacampus.model.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SystemRoleService roleService;
	@Autowired
	private PasswordEnconderService passwordEnconderService;

	@Override
	public User save(User user) {
		
		if (user.getId() != null) {
			throw new IllegalStateException("User is already in the database");
		}
		
		passwordEnconderService.encryptPassword(user);
		
		List<SystemRole> roles = new ArrayList<>();
		if(findAll().size() == 0) {
			user.setPreviousRole(user.getRoles().get(0));
			roles.add(isRole("ADMIN"));
			
			
		}else {
			roles.add(isRole("TEACHER"));
		}
		user.setRoles(roles);
		
		return userRepository.save(user);
		
		}
	
	@Override
	public User update(User user) {
	    User userUp = findById(user.getId());
	    
	    if (userUp == null) {
	        throw new IllegalStateException("User id is null");
	    }
	    else if (userUp.getRoles().get(0).getName().equals("ADMIN") && user.getRoles().get(0).getName().equals("ADMIN")) {
	        throw new IllegalStateException("User is already ADMIN");
	    }
	    else if(user.getRoles().get(0).getName().equals("REMOVE") && !userUp.getRoles().get(0).getName().equals("ADMIN")) {
	    	throw new IllegalStateException("User is not an Admin");
	    }
	    else if (userUp.getRoles().get(0).getName().equals("ADMIN") && !user.getRoles().get(0).getName().equals("ADMIN")) {
	        if(sizeAdmin() == 1) {
	            throw new IllegalStateException("Cannot be changed, there is only one ADMIN");
	        }
	    }
	    
	    List<SystemRole> roles = userUp.getRoles();
	    List<SystemRole> newRoles = new ArrayList<>();
	  
	    if (user.getRoles().get(0).getName().equals("REMOVE")) {
	    	 newRoles.add(userUp.getPreviousRole());
	    }
	    else {
	        SystemRole newRole = isRole(user.getRoles().get(0).getName());
	        newRoles.add(newRole);
	        
	        // Se o novo papel for ADMIN, armazenar o papel anterior em "previousRole" antes de atribuir o papel ADMIN
	        if (newRole.getName().equals("ADMIN")) {
	            userUp.setPreviousRole(roles.get(0));
	        }
	    }

	
	    userUp.setEmail(user.getEmail());
	    userUp.setDepartament(user.getDepartament());
	    userUp.setRoles(newRoles);
	   
	    return userRepository.save(userUp);
	}

	
	@Override
	public void delete(Long id) {
		User user = findById(id);

		if (user == null) {
			throw new IllegalStateException(String.format("Could not find a entity with id=%1", id));
		}

		userRepository.deleteById(id);
	}

	@Override
	public User findById(Long id) {
		
		if(id == null) {
			throw new IllegalStateException("Id cannot be null");
		}
		return userRepository.findById(id).get();
	}
	
	@Override
	public Iterable<User> find(User filter) {
		Example example = Example.of(filter,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));

		return userRepository.findAll(example);

	}
	
	@Override
	public User findByUserName(String username) {
		
		if(username == null) {
			throw new IllegalStateException("Username cannot be null");
		}
		Optional<User> optional = userRepository.findByUsername(username); 
		
		return optional.isPresent() ? optional.get() : null;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Could not find any user with username %s", username));
		}
		return user;
	}

	@Override
	public ArrayList<User> findAll() {
		return (ArrayList<User>) userRepository.findAll();
	}
	
	public int sizeAdmin() {
		int u = 0;
		List<User> us = findAll();
		if(us.size() != 0) {
			for(User a: us) {
				 List<SystemRole> role =  a.getRoles();
				if(role.get(0).getName().equals("ADMIN")) {
					u++;
				}
			}
		}
		return u;
	}
	

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}
	
	public SystemRole isRole(String role) {
		if(role.equals("ADMIN")) {
			return roleService.findAdmin();
		}
		else if(role.equals("TEACHER")) {
			return roleService.findTeacher();
		}
		else if(role.equals("TECHNICIAN")) {
			return roleService.findTechnician();
		}
		else {
			return roleService.findDefault();
		}

	}

}
