package de.fau.amos.virtualledger.server.auth;

import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.security.Principal;

// TODO
//@Secured
//@Provider
//@Priority(Priorities.AUTHENTICATION)
public class SecuredFilter /* implements ContainerRequestFilter*/ {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    //@Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        try {

            final String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

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
            requestContext.setSecurityContext(newContext);

        } catch (InvalidCredentialsException e) {
        	logger.info("", e);
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("Invalid Credentials").build());
        }
    }
}
