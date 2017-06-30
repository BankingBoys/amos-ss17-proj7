package de.fau.amos.virtualledger.server.auth;

import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.security.Principal;
import java.util.Collection;

public class SecuredFilter extends OncePerRequestFilter {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private UserCredentialRepository userCredentialRepository;

    public SecuredFilter(UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String email = userCredentialRepository.getEmailForSessionId(authorizationHeader);

            final SecurityContext newContext = new SecurityContext() {

                @Override
                public Principal getUserPrincipal() {
                    return () -> email;
                }

                @Override
                public boolean isUserInRole(String role) {
                    return true;
                }

                @Override
                public boolean isSecure() {
                    return true;
                }

                @Override
                public String getAuthenticationScheme() {
                    return SecurityContext.BASIC_AUTH;
                }
            };
            Authentication authentication = new SimpleAuthentication(email);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (InvalidCredentialsException e) {
            logger.info("", e);
            throw new SecurityException();
        }
    }
}
