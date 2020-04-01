package cz.jalasoft.learning.spring.server.repositories;

import cz.jalasoft.learning.spring.server.model.Credentials;
import cz.jalasoft.learning.spring.server.model.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends CrudRepository<Person, UUID> {

    @Query("select p from Person p join fetch p.credentials Credentials where p.id in (select c.person.id from Credentials c where c.username = ?1)")
    Optional<Person> findByUsername(String username);

    @Query("select c from Credentials c where c.username = ?1")
    Optional<Credentials> findCredentialsByUsername(String username);
}
