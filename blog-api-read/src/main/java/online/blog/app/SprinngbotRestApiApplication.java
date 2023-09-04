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

	@Override
	public void run(String... args) throws Exception {


	}
}
