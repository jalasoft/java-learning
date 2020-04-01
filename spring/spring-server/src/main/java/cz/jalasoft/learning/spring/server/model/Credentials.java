package cz.jalasoft.learning.spring.server.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "credentials")
@Access(AccessType.FIELD)
public class Credentials {

    protected Credentials() {}

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    //@Column(name = "person_id", insertable = false, updatable = false)
    //private UUID personId;

    @ManyToOne
//    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    public UUID id() {
        return id;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }
}
