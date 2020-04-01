package cz.jalasoft.erstebank.pds2.client.config;

import cz.jalasoft.erstebank.pds2.client.service.ErsteResourceServerTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

/**
 * @author lastovicka
 */
@Configuration
@EnableOAuth2Client
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Autowired
    private AuthorizationCodeResourceDetails authServerProperties;

    @Autowired
    private OAuth2ClientContext context;

    @Autowired
    private OAuth2RestTemplate restTemplate;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login/oauth2/code/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                .formLogin().loginPage("/login/oauth2/code/erste")
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
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/login/oauth2/code/erste");

        filter.setRestTemplate(restTemplate);

        ResourceServerTokenServices tokenServices = new ErsteResourceServerTokenService(authServerProperties.getClientId());
        //UserInfoTokenServices tokenServices = new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(), authServerProperties.getClientId());

        filter.setTokenServices(tokenServices);

        return filter;
    }

    @Bean
    public OAuth2RestTemplate restTemplate(OAuth2ProtectedResourceDetails resourceDetails, OAuth2ClientContext context) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy= new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.private.fio.cz", 8080));
        requestFactory.setProxy(proxy);

        AuthorizationCodeAccessTokenProvider authorizationCodeAccessTokenProvider = new AuthorizationCodeAccessTokenProvider();
        authorizationCodeAccessTokenProvider.setRequestFactory(requestFactory);

        ImplicitAccessTokenProvider implicitAccessTokenProvider = new ImplicitAccessTokenProvider();
        implicitAccessTokenProvider.setRequestFactory(requestFactory);

        AccessTokenProvider accessTokenProvider = new AccessTokenProviderChain(
        Arrays.<AccessTokenProvider> asList(authorizationCodeAccessTokenProvider, implicitAccessTokenProvider));

        OAuth2RestTemplate client = new OAuth2RestTemplate(resourceDetails, context);
        client.setAccessTokenProvider(accessTokenProvider);

        client.setRequestFactory(requestFactory);

        return client;
    }

    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}
