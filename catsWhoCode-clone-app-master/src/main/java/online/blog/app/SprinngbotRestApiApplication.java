package online.blog.app;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import online.blog.app.entity.Role;
import online.blog.app.repository.RoleRepository2;
import online.blog.app.service.impl.SequenceGeneratorService;

@SpringBootApplication
public class SprinngbotRestApiApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SprinngbotRestApiApplication.class, args);
	}

//	@Autowired
//	private RoleRepository roleRepository;
	
	@Autowired
	private SequenceGeneratorService service;
	
	@Autowired
	private RoleRepository2 roleRepository2;

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setId(service.getSequenceNumber(Role.SEQUENCE_NAME));
		adminRole.setName("ROLE_ADMIN");
		
		roleRepository2.save(adminRole);

		Role userRole = new Role();
		userRole.setId(service.getSequenceNumber(Role.SEQUENCE_NAME));
		userRole.setName("ROLE_USER");
		roleRepository2.save(userRole);




	}
}
