package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import de.fau.amos.virtualledger.server.auth.AuthenticationController;
import de.fau.amos.virtualledger.server.auth.InvalidCredentialsException;
import de.fau.amos.virtualledger.server.auth.Secured;
import de.fau.amos.virtualledger.server.auth.VirtualLedgerAuthenticationException;
import de.fau.amos.virtualledger.server.factories.StringApiModelFactory;
import de.fau.amos.virtualledger.server.model.UserCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.lang.invoke.MethodHandles;

/**
 * Endpoints for authentication / authorization
 */
@Path("/auth")
public class AuthApiEndpoint {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    AuthenticationController authenticationController;
    StringApiModelFactory stringApiModelFactory;

    @Inject
    public AuthApiEndpoint(AuthenticationController authenticationController, StringApiModelFactory stringApiModelFactory) {
        this.authenticationController = authenticationController;
        this.stringApiModelFactory = stringApiModelFactory;
    }
    protected AuthApiEndpoint() { }


    /**
     * Endpoint for registering a new user. Parameters must not be null or empty, id has to be null or 0.
     * @param credential
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response registerEndpoint(UserCredential credential) {
    	if(credential.getEmail() == null || credential.getEmail().isEmpty() ||
                credential.getPassword() == null || credential.getPassword().isEmpty() ||
                credential.getFirstName() == null || credential.getFirstName().isEmpty() ||
                credential.getLastName() == null || credential.getLastName().isEmpty() ||
                credential.getId() != 0)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please check your inserted values. None of the parameters must be null or empty, id has to be 0.").build();
        }
        logger.info("Registration for " + credential.getEmail() + " was requested.");

        return this.register(credential);
    }

    /**
     * Endpoint for logging in. Parameters must not be null or empty.
     * @param loginData
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response loginEndpoint(final LoginData loginData) {
        if(loginData.email == null || loginData.email.isEmpty() ||
                loginData.password == null || loginData.password.isEmpty())
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please check your inserted values. None of the parameters must be null or empty.").build();
        }
        logger.info("Login of "+ loginData.email +" was requested.");

        return this.login(loginData);
    }

    /**
     * Endpoint for logging out. User must be authenticated.
     * @param securityContext
     * @return
     */
    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/logout")
    public Response logoutEndpoint(@Context SecurityContext securityContext)
    {
        if(securityContext.getUserPrincipal().getName() == null || securityContext.getUserPrincipal().getName().isEmpty())
        {
            return Response.status(Response.Status.FORBIDDEN).entity("Authentication failed! Your email wasn't found.").build();
        }
        final String email = securityContext.getUserPrincipal().getName();
        logger.info("Logout of " + email + " was requested");

        return this.logout(email);
    }


    /**
     * Does the logic operation for registering the user.
     * Also does exception handling.
     * @param credential
     * @return a response with status code depending on result
     */
    private Response register(UserCredential credential)
    {
        String responseMsg;
        try
        {
            responseMsg = authenticationController.register(credential);
        } catch(VirtualLedgerAuthenticationException ex)
        {
            logger.error("", ex);
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        StringApiModel responseObj = stringApiModelFactory.createStringApiModel(responseMsg);
        return Response.ok(responseObj).build();
    }

    /**
     * Does the logic operation for logging in a user.
     * Also does exception handling.
     * @param loginData
     * @return
     */
    private Response login(LoginData loginData)
    {
        final SessionData sessionData;
        try {
            sessionData = authenticationController.login(loginData);
        } catch (InvalidCredentialsException ex) {
            logger.error("", ex);
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return Response.ok(sessionData).build();
    }

    /**
     * Does the logic operation for logging out a user
     * @param email
     * @return
     */
    private Response logout(String email)
    {
        authenticationController.logout(email);
        return Response.ok(stringApiModelFactory.createStringApiModel("You were logged out! " + email)).build();
    }
}
