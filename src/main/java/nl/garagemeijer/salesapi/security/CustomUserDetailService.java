package nl.garagemeijer.salesapi.security;

import nl.garagemeijer.salesapi.exceptions.RecordNotFoundException;
import nl.garagemeijer.salesapi.models.User;
import nl.garagemeijer.salesapi.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public CustomUserDetailService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User getUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (getUser == null) {
            throw new RecordNotFoundException("Can't find anything");
        } else {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(getUser.getUsername())
                    .password(getUser.getPassword())
                    .roles(String.valueOf(getUser.getProfile().getRole()))
                    .build();
        }
    }
}