package cz.jalasoft.learning.spring.server.model;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "person")
@Access(AccessType.FIELD)
public class Person {

    protected Person() {}

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @JoinColumn(name = "address")
    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "person")
    private List<Credentials> credentials;

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String surname() {
        return surname;
    }

    public String fullname() {
        return name() + " " + surname();
    }

    public String email() {
        return email;
    }

    public Address address() {
        return address;
    }

    public Optional<Credentials> credentialsByUsername(String username) {
        return credentials.stream().filter(c -> c.username().equals(username)).findFirst();
    }
}
