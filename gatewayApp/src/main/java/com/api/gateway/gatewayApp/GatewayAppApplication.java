package com.api.gateway.gatewayApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@SpringBootApplication
//@CrossOrigin(origins = "http:localhost:4200")
public class GatewayAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayAppApplication.class, args);
	}
	
//	@Bean
//    public WebFluxConfigurer corsConfigurer() {
//        return new WebFluxConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                		.allowedOrigins("*")
//                		.allowedMethods("*")
//                		//.allowCredentials(true)
//                		.allowedHeaders("*")
//                		.maxAge(3600L)
//                		.exposedHeaders("Authorization");
//            }
//        };
//    }

}
