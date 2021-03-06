package cz.jalasoft.oauth2.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Jan Lastovicka
 * @since 2019-04-01
 */
@EnableOAuth2Client
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext context;

    @Autowired
    private AuthorizationCodeResourceDetails resourceDetail;

    @Autowired
    private ResourceServerProperties resourcePropertiesss;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login/oauth2/code/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                .formLogin().loginPage("/login/oauth2/code/local")
                .and()
                .logout()
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "MYSESSIONID")
                .and()
                .csrf().disable()
                .addFilterBefore(oauth2Filter(), BasicAuthenticationFilter.class);
    }

    private OAuth2ClientAuthenticationProcessingFilter oauth2Filter() {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/login/oauth2/code/local");

        OAuth2RestTemplate template = template();
        filter.setRestTemplate(template);

        ResourceServerProperties facebookResource = resourcePropertiesss;
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource.getUserInfoUri(), resourceDetail.getClientId());
        tokenServices.setRestTemplate(template);

        filter.setTokenServices(tokenServices);

        return filter;
    }

    @Bean
    public OAuth2RestTemplate template() {
        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetail, context);
        return template;
    }

    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}
