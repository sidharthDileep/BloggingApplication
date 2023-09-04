package online.blog.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    
    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJWTFormRequest(request);

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
            String username=jwtTokenProvider.getUsernameFromJWT(token);
            UserDetails userDetails = customeUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities()
            );
             authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);

    }

    private String getJWTFormRequest(HttpServletRequest request){
        String bearedToken = request.getHeader("Authorization");
       if (StringUtils.hasText(bearedToken) && bearedToken.startsWith("Bearer")){
           return bearedToken.substring(7,bearedToken.length());
       }
       return null;
    }
}
