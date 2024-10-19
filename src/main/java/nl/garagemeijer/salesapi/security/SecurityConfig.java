package nl.garagemeijer.salesapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, CustomUserDetailService myUserDetailsService) throws Exception {
        var builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(myUserDetailsService).passwordEncoder(encoder);
        return builder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http
                .httpBasic(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/vehicles/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/vehicles/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/vehicles/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/vehicles/**").hasAuthority("ADMIN")

                        .requestMatchers("purchases/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sales/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/sales/**").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.PUT, "/sales/**").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/sales/**").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/customers").authenticated()
                        .requestMatchers(HttpMethod.POST, "/customers").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.PUT, "/customers").hasAuthority("SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/customers/**").hasAuthority("ADMIN")

                        .requestMatchers("/profiles/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/users/*/password").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/users/*/profile").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")

                        .anyRequest().denyAll()
                )
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
