package cz.jalasoft.learning.spring.server.controller.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class PersonNotFoundException extends Exception {


    @Getter
    private final String username;
}
