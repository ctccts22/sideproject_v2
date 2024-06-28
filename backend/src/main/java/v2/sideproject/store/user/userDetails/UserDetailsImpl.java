package v2.sideproject.store.user.userDetails;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import v2.sideproject.store.user.entity.Users;

import java.util.*;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsImpl.class);
    private Users users;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + users.getRoles().getName().toString()));
        log.info("Authorities : {}", authorities);
        return authorities;
    }

    public String getRoleNames() {
        return users.getRoles().getName().toString();
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
