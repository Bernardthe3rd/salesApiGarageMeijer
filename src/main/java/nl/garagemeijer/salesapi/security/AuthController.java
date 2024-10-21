package nl.garagemeijer.salesapi.security;

import nl.garagemeijer.salesapi.dtos.authentication.AuthenticationInputDto;
import nl.garagemeijer.salesapi.dtos.authentication.AuthenticationOutputDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationInputDto input) {
        UsernamePasswordAuthenticationToken uploadedDetails = new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword());

        try {
            Authentication auth = authenticationManager.authenticate(uploadedDetails);
            System.out.println("role " + auth.getAuthorities());
            var userDetails = userDetailsService.loadUserByUsername(auth.getName());
            String token = jwtService.generateToken(userDetails);
            AuthenticationOutputDto response = new AuthenticationOutputDto(token, "login succesful");
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(response);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
