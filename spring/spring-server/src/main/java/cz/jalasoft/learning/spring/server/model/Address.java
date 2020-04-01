package cz.jalasoft.learning.spring.server.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "address")
@Access(AccessType.FIELD)
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private long houseNumber;

    @Column(name = "town")
    private String town;

    @Column(name = "pobox")
    private String postOfficeBox;

    @Column(name = "country")
    private String country;

    protected Address() {}

    public UUID id() {
        return id;
    }

    public String street() {
        return street;
    }

    public long houseNumber() {
        return houseNumber;
    }

    public String town() {
        return town;
    }

    public String postOfficeBox() {
        return postOfficeBox;
    }

    public String country() {
        return country;
    }
}
