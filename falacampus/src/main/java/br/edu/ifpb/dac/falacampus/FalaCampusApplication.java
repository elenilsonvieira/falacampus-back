package br.edu.ifpb.dac.falacampus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.edu.ifpb.dac.falacampus.business.service.ConverterService;
import br.edu.ifpb.dac.falacampus.business.service.SuapService;
import br.edu.ifpb.dac.falacampus.business.service.SystemRoleService;
import br.edu.ifpb.dac.falacampus.business.service.impl.SuapServiceImpl;
import br.edu.ifpb.dac.falacampus.model.entity.Departament;

@SpringBootApplication
@EnableWebMvc
@EnableJpaRepositories(basePackages = {"br.edu.ifpb.dac.falacampus.model.repository"})
public class FalaCampusApplication implements WebMvcConfigurer, CommandLineRunner {
	@Autowired
	private SystemRoleService systemRoleService;
//---------------	
	@Autowired
	private SuapServiceImpl suapS;
	
	@Autowired
	private ConverterService converter;
//---------------	

	
	public static void main(String[] args) {
		SpringApplication.run(FalaCampusApplication.class, args);
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH");
	}

	@Override
	public void run(String... args) throws Exception {
		systemRoleService.createDefaultValues();

//---------------	

//		String listaDoSuap = suapS.findAllDepartament("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo1OTMyNiwidXNlcm5hbWUiOiIyMDIwMTUwMjAwMzIiLCJleHAiOjE2NjY0NjIxMjksImVtYWlsIjoiIiwib3JpZ19pYXQiOjE2NjYzNzU3Mjl9.sEi5m2i0dwOjWRCf-CSFnQTPP6n6V9ryEh8o8poh43w");
//		Departament d =  converter.jsonToDepartament(listaDoSuap);
//		System.out.println(d.getName());
		
//---------------	

	}
}
