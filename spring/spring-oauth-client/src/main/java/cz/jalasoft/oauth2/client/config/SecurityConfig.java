package cz.jalasoft.oauth2.client.config;

import cz.jalasoft.oauth2.client.config.beans.OAuth2TargetUrl;
import cz.jalasoft.oauth2.client.config.beans.ResourceServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

/**
 * @author lastovicka
 */

@EnableOAuth2Client
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext context;

    @Autowired
    private AuthorizationCodeResourceDetails authDetails;

    @Autowired
    private ResourceServerProperties resourceDetails;

    @Autowired
    private OAuth2TargetUrl targetUrl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                    .antMatchers("/login**")
                    .permitAll()
                .anyRequest()
				.authenticated()
                .and()
                //toto je nutne aby se provedl redirect na autentikaci pokud
                //nejse zalogovany
               // .formLogin().loginPage(targetUrl.getValue())
               // .and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(targetUrl.getValue());

        OAuth2RestTemplate template = restTemplate(authDetails, context); //new OAuth2RestTemplate(authDetails, context);
        filter.setRestTemplate(template);

        UserInfoTokenServices tokenServices = new UserInfoTokenServices(resourceDetails.getUserInfoUri(), authDetails.getClientId());
        tokenServices.setRestTemplate(template);
        filter.setTokenServices(tokenServices);

        return filter;
    }

    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean<OAuth2ClientContextFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(filter);
        bean.setOrder(-100);
        return bean;
    }

    private OAuth2RestTemplate restTemplate(OAuth2ProtectedResourceDetails resourceDetails, OAuth2ClientContext context) {
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
}
