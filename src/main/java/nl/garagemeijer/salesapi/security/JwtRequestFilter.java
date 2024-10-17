package nl.garagemeijer.salesapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public JwtRequestFilter(UserDetailsService userDetailsService, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = extractJwtFromRequest(request);
        if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            processTokenAndSetAuthentication(jwt, request);
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private void processTokenAndSetAuthentication(String jwt, HttpServletRequest request) {
        String username = jwtService.extractUsername(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (username != null && jwtService.validateToken(jwt, userDetails)) {
            List<GrantedAuthority> role = jwtService.extractSimpleGrantedAuthorities(jwt);
            setAuthentication(username, role, request, jwt);
        }
    }

    private void setAuthentication(String username, List<GrantedAuthority> role, HttpServletRequest request, String jwt) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, role);
        CustomUserDetails userDetails = new CustomUserDetails(username, jwtService.extractRole(jwt).toString());
        token.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
