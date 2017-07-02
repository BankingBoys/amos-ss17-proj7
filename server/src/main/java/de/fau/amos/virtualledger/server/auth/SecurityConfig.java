package de.fau.amos.virtualledger.server.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(new SecuredFilter(userCredentialRepository), BasicAuthenticationFilter.class)
            .antMatcher("/api/auth/logout")
            .antMatcher("/api/banking/**")
            .antMatcher("/api/savings/**");
        http.csrf().disable();
    }
}
