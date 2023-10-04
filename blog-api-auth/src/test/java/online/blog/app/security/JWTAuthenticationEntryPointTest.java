//package online.blog.app.security;
//
//import static org.mockito.Mockito.*;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//
//public class JWTAuthenticationEntryPointTest {
//
//    private AuthenticationEntryPoint authenticationEntryPoint;
//
//    @BeforeEach
//    public void setUp() {
//        authenticationEntryPoint = new JWTAuthenticationEntryPoint();
//    }
//
//    @Test
//    public void testCommence() throws Exception {
//        // Create a mock HttpServletRequest and HttpServletResponse
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        MockHttpServletResponse response = new MockHttpServletResponse();
//
//        // Create a mock AuthenticationException
//        AuthenticationException authException = new AuthenticationException("Unauthorized") {};
//
//        // Call the commence method of the authentication entry point
//        authenticationEntryPoint.commence(request, response, authException);
//
//        // Verify that the response status code is SC_UNAUTHORIZED (401)
//        // and the response content is the message from the AuthenticationException
//        assert (response.getStatus() == HttpServletResponse.SC_UNAUTHORIZED);
//        assert (response.getContentAsString().equals("Unauthorized"));
//    }
//}
//
