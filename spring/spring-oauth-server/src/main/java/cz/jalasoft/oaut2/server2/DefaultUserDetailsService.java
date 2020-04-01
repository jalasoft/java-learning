package cz.jalasoft.oaut2.server2;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Jan Lastovicka
 * @since 2019-04-02
 */
@Component
public final class DefaultUserDetailsService implements UserDetailsService {

    private static final User HONZALES_USER = new User("honzales", "misulka", Arrays.asList(new SimpleGrantedAuthority("USER")));

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        if ("honzales".equals(s)) {
            return HONZALES_USER;
        }

        throw new UsernameNotFoundException("Proste nenasel " + s);
    }
}
