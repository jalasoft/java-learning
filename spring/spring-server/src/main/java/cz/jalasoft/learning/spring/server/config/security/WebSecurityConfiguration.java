package cz.jalasoft.learning.spring.server.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Order(1)
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/web/**")
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                .formLogin();

        //http.antMatcher("/web/**");
                //.formLogin().successForwardUrl("/index")
                //.and()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.);

    }
}
