package cz.jalasoft.oauth.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author Jan Lastovicka
 * @since 2019-04-02
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Main {


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
