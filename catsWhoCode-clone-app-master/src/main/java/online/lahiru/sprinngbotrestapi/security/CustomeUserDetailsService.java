package online.lahiru.sprinngbotrestapi.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import online.lahiru.sprinngbotrestapi.entity.Role;
import online.lahiru.sprinngbotrestapi.entity.User;
import online.lahiru.sprinngbotrestapi.repository.UserRepository2;

@Service
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository2 userRepository;

    public CustomeUserDetailsService(UserRepository2 userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email " + usernameOrEmail));

        return  new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword()
        ,mapRolesToAuthorities(user.getRoles()));

    }

    private Collection <? extends GrantedAuthority > mapRolesToAuthorities(Set<Role> roles){
      return   roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
