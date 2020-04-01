package cz.jalasoft.oauth.provider.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Jan Lastovicka
 * @since 2019-04-02
 */
@Component
//@Qualifier("default")
public final class DefaultUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserDetails details = users()
                .stream()
                .filter(u -> u.getUsername().equals(s))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException(s));


        return details;
    }

    private Collection<User> users() {
        return Arrays.asList(
                new User("honzales", "misulka", Arrays.asList(
                    new SimpleGrantedAuthority("ROLE_CLIENT"),
                    new SimpleGrantedAuthority("ROLE_ACCOUNT_HOLDER"))
                ),
               new User("another", "another", Arrays.asList(
                    new SimpleGrantedAuthority("ROLE_CLIENT"))
                )
        );
    }
}
