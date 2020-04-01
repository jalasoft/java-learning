package cz.jalasoft.learning.spring.server.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = ApiAdvice.class)
public class ApiAdvice {

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<String> handlePersonNotFound(PersonNotFoundException exc) {

        return ResponseEntity.badRequest().body("No person with username '" + exc.getUsername() + "' has been found.\n");
    }
}
