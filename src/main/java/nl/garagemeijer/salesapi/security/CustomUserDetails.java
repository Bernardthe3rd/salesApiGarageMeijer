package nl.garagemeijer.salesapi.security;

import nl.garagemeijer.salesapi.enums.Role;
import nl.garagemeijer.salesapi.models.Profile;
import nl.garagemeijer.salesapi.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public CustomUserDetails(String username, String role) {
        user = new User();
        user.setUsername(username);

        Profile profile = new Profile();
        profile.setRole(Role.valueOf(role));
        user.setProfile(profile);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getProfile().getRole().toString()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive();
    }

    public String getRole() {
        return user.getProfile().getRole().toString();
    }
}
