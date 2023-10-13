//package online.blog.app.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class WebConfig implements WebMvcConfigurer{
//	
//	@Override
//    public void addCorsMappings(CorsRegistry corsRegistry) {
//        corsRegistry.addMapping("/**")
//        		.allowedOrigins("*")
//                .allowedMethods("GET", "POST")
//                .maxAge(3600L)
//                .allowedHeaders("*")
//                .exposedHeaders("Authorization")
//                .allowCredentials(true)
//                ;
//    }
//
//}
