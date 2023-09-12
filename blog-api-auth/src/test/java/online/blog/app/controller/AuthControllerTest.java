package online.blog.app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import online.blog.app.entity.Role;
import online.blog.app.entity.User;
import online.blog.app.payload.LoginDTO;
import online.blog.app.payload.SignUpDTO;
import online.blog.app.repository.RoleRepository2;
import online.blog.app.repository.UserRepository2;
import online.blog.app.security.JWTTokenProvider;
import online.blog.app.service.impl.SequenceGeneratorService;

@SpringBootTest
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository2 userRepository2;

    @Mock
    private RoleRepository2 roleRepository2;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTTokenProvider tokenProvider;

    @Mock
    private SequenceGeneratorService service;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        // Mock the authentication response
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(tokenProvider.generateToken(authentication)).thenReturn("mockToken");

        // Create a LoginDTO for the request body
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsernameOrEmail("testuser");
        loginDTO.setPassword("testpassword");

        // Perform the POST request
        mockMvc.perform(post("/api/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("mockToken"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        // Mock the UserRepository2 and other dependencies
        when(userRepository2.existsByUsername(anyString())).thenReturn(false);
        when(userRepository2.existsByEmail(anyString())).thenReturn(false);
        Role role = new Role();
		role.setName("ROLE_ADMIN");
        when(roleRepository2.findByName(any())).thenReturn(Optional.of(role));
        when(service.getSequenceNumber(anyString())).thenReturn(1);

        // Create a SignUpDTO for the request body
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setName("Test User");
        signUpDTO.setUsername("testuser");
        signUpDTO.setEmail("test@example.com");
        signUpDTO.setPassword("testpassword");

        // Perform the POST request
        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("user saved successfully !"));

        // Verify that userRepository2.save(user) was called
        verify(userRepository2, times(1)).save(any(User.class));
    }
}
