package de.fau.amos.virtualledger.server.auth;

import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecuredFilter implements ContainerRequestFilter {

    @Inject
    private UserCredentialRepository userCredentialRepository;

    @Override
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
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("Invalid Credentials").build());
        }
    }
}
