package cz.jalasoft.learning.spring.server.controller.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public final class PersonResource {

    @Getter
    private final String firstName;

    @Getter
    private final String lastName;

    @Getter
    private final String username;

    @Getter
    private final String email;
}
