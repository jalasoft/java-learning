package cz.jalasoft.learning.spring.server.security;

import cz.jalasoft.learning.spring.server.model.Credentials;
import cz.jalasoft.learning.spring.server.model.Person;
import cz.jalasoft.learning.spring.server.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public final class LocalRepoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final PersonRepository personRepository;

    @Autowired
    public LocalRepoAuthenticationProvider(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        String userName = usernamePasswordAuthenticationToken.getName();

        Optional<Person> maybePerson = personRepository.findByUsername(userName);

        if (!maybePerson.isPresent()) {
            throw new BadCredentialsException("Username '" + userName + "' is unknown.");
        }

        Person person = maybePerson.get();

        Optional<Credentials> maybeCredentials = person.credentialsByUsername(userName);

        if (!maybeCredentials.isPresent()) {
            throw new RuntimeException("There is no credentials for username '" + userName + "'.");
        }

        Credentials credentials = maybeCredentials.get();

        String passwordToCheck = usernamePasswordAuthenticationToken.getCredentials().toString();

        if (!credentials.password().equals(passwordToCheck)) {
            throw new BadCredentialsException("Password is invalid.");
        }

        return new User(credentials, true, true, true, true);
    }
}
