package cz.jalasoft.learning.spring.server.controller.api;

import cz.jalasoft.learning.spring.server.model.Person;
import cz.jalasoft.learning.spring.server.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Transactional
public class ApiController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/person")
    public ResponseEntity<PersonResource> personByUsername(
            @RequestParam("username")
            String username) throws PersonNotFoundException {

        Optional<Person> maybePerson = personRepository.findByUsername(username);

        if (!maybePerson.isPresent()) {
            throw new PersonNotFoundException(username);
        }

        Person p = maybePerson.get();

        PersonResource resource = new PersonResource(p.name(), p.surname(), username, p.email());

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/fullname")
    public String fullNameByUsername(
            @RequestParam("username")
            String username) throws PersonNotFoundException {

        Optional<Person> maybePerson = personRepository.findByUsername(username);

        if (!maybePerson.isPresent()) {
            throw new PersonNotFoundException(username);
        }

        Person person = maybePerson.get();
        return person.fullname();
    }
}
