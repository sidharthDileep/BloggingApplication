//package online.blog.app.config;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//public class SecurityConfigTest {
//
//    @Autowired
//    private SecurityConfig securityConfig;
//
//    @MockBean
//    private HttpSecurity httpSecurity;
//
//    @BeforeEach
//    public void setUp() {
//        // Reset the mock before each test
//        reset(httpSecurity);
//    }
//
//    @Test
//    public void testSecurityConfiguration() throws Exception {
//        // Call the configure method of SecurityConfig
//        securityConfig.configure(httpSecurity);
//
//        // Verify that the configure method is called with the expected parameters
//        verify(httpSecurity, times(1))
//                .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(any())
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(any())
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
//                .antMatchers("/api/v1/auth/**").permitAll()
//                .antMatchers("/api-docs/**").permitAll()
//                .antMatchers("/swagger-ui/**").permitAll()
//                .antMatchers("/actuator/**").permitAll()
//                .antMatchers("/swagger-resources/**").permitAll()
//                .antMatchers("/swagger-ui-blog.html").permitAll()
//                .antMatchers("/webjars/**").permitAll()
//                .anyRequest()
//                .authenticated();
//    }
//
//    // You can add more test cases as needed for different scenarios and configurations
//}
