package de.fau.amos.virtualledger.server.auth;


import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserCredentialRepository userCredentialRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(new SecuredFilter(userCredentialRepository), BasicAuthenticationFilter.class)
            .antMatcher("/api/auth/logout")
            .antMatcher("/api/banking/**")
            .antMatcher("/api/savings/**");
        http.csrf().disable();
    }
}
