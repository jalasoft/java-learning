package cz.jalasoft.learning.spring.server.config.security;

import cz.jalasoft.learning.spring.server.security.LocalRepoAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(0)
@Configuration
@EnableWebSecurity
public class GlobalSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LocalRepoAuthenticationProvider localRepoAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(localRepoAuthenticationProvider);
        /*
        auth.inMemoryAuthentication()
                .withUser("pepa")
                .password("{noop}pepik")
                .roles("VUL", "TROUBA")
                .authorities("LIBAT_PRDEL");*/
    }

}
