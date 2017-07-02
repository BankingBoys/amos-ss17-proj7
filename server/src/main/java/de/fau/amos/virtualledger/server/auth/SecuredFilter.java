package de.fau.amos.virtualledger.server.auth;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;

public class SecuredFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private UserCredentialRepository userCredentialRepository;

    public SecuredFilter(UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String email = userCredentialRepository.getEmailForSessionId(authorizationHeader);

            Authentication authentication = new SimpleAuthentication(email);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (InvalidCredentialsException e) {
            LOGGER.info("", e);
            throw new SecurityException();
        }
    }
}
