//package online.blog.app.security;
//
//import static org.mockito.Mockito.when;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//
//import io.jsonwebtoken.SignatureAlgorithm;
//
//public class JWTTokenProviderTest {
//
//    private JWTTokenProvider jwtTokenProvider;
//
//    @BeforeEach
//    public void setUp() {
//        jwtTokenProvider = new JWTTokenProvider();
//        jwtTokenProvider.jwtSecret = "mySecretKey";
//        jwtTokenProvider.jwtExpirationInMs = 3600000; // 1 hour
//    }
//
//    @Test
//    public void testGenerateToken() {
//        // Create a mock Authentication object
//        Authentication authentication = new UsernamePasswordAuthenticationToken("testUser", null);
//
//        // Generate a JWT token
//        String token = jwtTokenProvider.generateToken(authentication);
//
//        // Assert that the token is not null
//        assert (token != null);
//    }
//
//    @Test
//    public void testGetUsernameFromJWT() {
//        // Create a mock JWT token
//        String mockToken = "mockToken";
//
//        // Mock the behavior of the Jwts parser
//        jwtTokenProvider.jwtSecret = "mySecretKey";
//        when(jwtTokenProvider.getUsernameFromJWT(mockToken)).thenReturn("testUser");
//
//        // Get the username from the JWT token
//        String username = jwtTokenProvider.getUsernameFromJWT(mockToken);
//
//        // Assert that the username is as expected
//        assert (username.equals("testUser"));
//    }
//
//    @Test
//    public void testValidateValidToken() {
//        // Create a valid JWT token
//        String validToken = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken("testUser", null));
//
//        // Validate the valid JWT token
//        boolean isValid = jwtTokenProvider.validateToken(validToken);
//
//        // Assert that the token is valid
//        assert (isValid);
//    }
//
//    @Test
//    public void testValidateInvalidToken() {
//        // Create an invalid JWT token (with a different secret)
//        String invalidToken = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken("testUser", null));
//        jwtTokenProvider.jwtSecret = "differentSecret";
//
//        // Validate the invalid JWT token
//        boolean isValid = jwtTokenProvider.validateToken(invalidToken);
//
//        // Assert that the token is invalid
//        assert (!isValid);
//    }
//
//    @Test
//    public void testValidateExpiredToken() {
//        // Create an expired JWT token
//        jwtTokenProvider.jwtExpirationInMs = 0; // Set expiration time to 0 milliseconds
//        String expiredToken = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken("testUser", null));
//
//        // Validate the expired JWT token
//        boolean isValid = jwtTokenProvider.validateToken(expiredToken);
//
//        // Assert that the token is invalid due to expiration
//        assert (!isValid);
//    }
//
//    @Test
//    public void testValidateMalformedToken() {
//        // Create a malformed JWT token (invalid format)
//        String malformedToken = "invalidToken";
//
//        // Validate the malformed JWT token
//        boolean isValid = jwtTokenProvider.validateToken(malformedToken);
//
//        // Assert that the token is invalid due to being malformed
//        assert (!isValid);
//    }
//
//    @Test
//    public void testValidateUnsupportedToken() {
//        // Create an unsupported JWT token
//        String unsupportedToken = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken("testUser", null));
//
//        // Set an unsupported signature algorithm
//        jwtTokenProvider.jwtSecret = "mySecretKey";
//        jwtTokenProvider.jwtExpirationInMs = 3600000;
//        jwtTokenProvider.signatureAlgorithm = SignatureAlgorithm.RS256;
//
//        // Validate the unsupported JWT token
//        boolean isValid = jwtTokenProvider.validateToken(unsupportedToken);
//
//        // Assert that the token is invalid due to being unsupported
//        assert (!isValid);
//    }
//
//    @Test
//    public void testValidateTokenWithEmptyClaims() {
//        // Create a token with empty claims
//        String emptyClaimsToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyfQ.";
//
//        // Validate the token with empty claims
//        boolean isValid = jwtTokenProvider.validateToken(emptyClaimsToken);
//
//        // Assert that the token is invalid due to empty claims
//        assert (!isValid);
//    }
//}