package online.blog.app.security;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class JWTAuthenticationFilterTest {

    private JWTAuthenticationFilter jwtAuthenticationFilter;
    private JWTTokenProvider jwtTokenProvider;
    private CustomeUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = mock(JWTTokenProvider.class);
        customUserDetailsService = mock(CustomeUserDetailsService.class);
        //jwtAuthenticationFilter = new JWTAuthenticationFilter(jwtTokenProvider, customUserDetailsService);
    }

    @Test
    public void testDoFilterInternal() throws Exception {
        // Create a mock HttpServletRequest and HttpServletResponse
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Create a mock JWT token
        String mockToken = "mockToken";

        // Set up the JWT token provider to validate the token
        when(jwtTokenProvider.validateToken(mockToken)).thenReturn(true);

        // Set up the JWT token provider to get the username from the token
        when(jwtTokenProvider.getUsernameFromJWT(mockToken)).thenReturn("testUser");

        // Create a mock UserDetails object
        UserDetails userDetails = new User("testUser", "password", null);

        // Set up the custom user details service to load the UserDetails
        when(customUserDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

        // Create a mock Authentication object
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Set up the SecurityContextHolder with the mock Authentication
        WebAuthenticationDetailsSource detailsSource = new WebAuthenticationDetailsSource();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(detailsSource.buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // Set the Authorization header with the mock token
        request.addHeader("Authorization", "Bearer " + mockToken);

        // Call the doFilterInternal method of the JWTAuthenticationFilter
        jwtAuthenticationFilter.doFilterInternal(request, response, null);

        // Verify that the SecurityContextHolder is updated with the mock Authentication
        assert (SecurityContextHolder.getContext().getAuthentication().equals(authentication));
    }
}
