package cz.jalasoft.learning.spring.server.config.security;

import cz.jalasoft.learning.spring.server.setting.LdapSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;

@Profile("authldap")
@EnableWebSecurity
@Configuration
public class ApiLdapSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LdapSetting setting;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/api/**")
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userDnPatterns(setting.getUserDnPattern())
                .contextSource()
                    .url(setting.getServer())
                .and()
                .passwordCompare()
                    .passwordEncoder(new LdapShaPasswordEncoder())
                    .passwordAttribute(setting.getPasswordAttribute());

    }
}
