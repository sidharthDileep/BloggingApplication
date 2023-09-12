package online.blog.app.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import online.blog.app.entity.Role;
import online.blog.app.entity.User;
import online.blog.app.repository.UserRepository2;
import online.blog.app.security.CustomeUserDetailsService;

public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository2 userRepository;

    private CustomeUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customUserDetailsService = new CustomeUserDetailsService(userRepository);
    }

    @Test
    public void testLoadUserByUsernameWithUsername() {
        // Prepare a sample user
        String usernameOrEmail = "testuser";
        User user = new User();
        user.setEmail("testuser@test.com");
        user.setPassword("password");
        Role role = new Role();
        role.setName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        // Mock userRepository to return the sample user
        when(userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)).thenReturn(Optional.of(user));

        // Load user by username
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(usernameOrEmail);

        // Verify that the UserDetails object was created correctly
        assertNotNull(userDetails);
        assertEquals("testuser@test.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());

        // Check the authorities (roles)
        assertTrue(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_USER"::equals));
    }

    @Test
    public void testLoadUserByUsernameWithEmail() {
        // Prepare a sample user
        String usernameOrEmail = "testuser@test.com";
        User user = new User();
        user.setEmail("testuser@test.com");
        user.setPassword("password");
        Role role = new Role();
        role.setName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        // Mock userRepository to return the sample user
        when(userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)).thenReturn(Optional.of(user));

        // Load user by email
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(usernameOrEmail);

        // Verify that the UserDetails object was created correctly
        assertNotNull(userDetails);
        assertEquals("testuser@test.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());

        // Check the authorities (roles)
        assertTrue(userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_USER"::equals));
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        // Mock userRepository to return an empty Optional (user not found)
        String usernameOrEmail = "nonexistentuser";
        when(userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)).thenReturn(Optional.empty());

        // Try to load a nonexistent user by username or email
        assertThrows(org.springframework.security.core.userdetails.UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(usernameOrEmail));
    }
}
