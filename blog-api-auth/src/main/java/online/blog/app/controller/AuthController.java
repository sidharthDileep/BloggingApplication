package online.blog.app.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.blog.app.entity.Role;
import online.blog.app.entity.User;
import online.blog.app.payload.JWTAuthResponse;
import online.blog.app.payload.LoginDTO;
import online.blog.app.payload.SignUpDTO;
import online.blog.app.repository.RoleRepository2;
import online.blog.app.repository.UserRepository2;
import online.blog.app.security.JWTTokenProvider;
import online.blog.app.service.impl.SequenceGeneratorService;

@RestController
@RequestMapping("/api/v1/auth")
//@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository2 userRepository2;

//    @Autowired
//    private RoleRepository roleRepository;
    
    @Autowired
    private RoleRepository2 roleRepository2;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
	private SequenceGeneratorService service;
    
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);
        return  ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/signup")
public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO){

        if(userRepository2.existsByUsername(signUpDTO.getUsername())){
            return  new ResponseEntity<>("username is already taken",HttpStatus.BAD_REQUEST);

        }

        if(userRepository2.existsByEmail(signUpDTO.getEmail())){
            return  new ResponseEntity<>("email is already taken",HttpStatus.BAD_REQUEST);

        }

        User user = new User();
        user.setId(service.getSequenceNumber(User.SEQUENCE_NAME));
        user.setName(signUpDTO.getName());
        user.setUsername(signUpDTO.getUsername());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Role roles = roleRepository2.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository2.save(user);

        return  new ResponseEntity<>("user saved successfully !", HttpStatus.OK);
        
//
    }
}
