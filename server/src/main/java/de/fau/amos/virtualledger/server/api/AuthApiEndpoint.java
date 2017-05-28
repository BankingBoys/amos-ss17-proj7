package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import de.fau.amos.virtualledger.server.factories.StringApiModelFactory;
import de.fau.amos.virtualledger.server.auth.AuthenticationController;
import de.fau.amos.virtualledger.server.auth.InvalidCredentialsException;
import de.fau.amos.virtualledger.server.auth.Secured;
import de.fau.amos.virtualledger.server.auth.VirtualLedgerAuthenticationException;
import de.fau.amos.virtualledger.server.model.UserCredential;

import java.util.logging.Level;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.sun.istack.logging.Logger;

/**
 * Endpoints for authentication / authorization
 */
@Path("/auth")
public class AuthApiEndpoint {

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
    	logger().info("Registration for " + credential.getEmail() + " was requested.");
    	if(credential.getEmail() == null || credential.getEmail().isEmpty() ||
                credential.getPassword() == null || credential.getPassword().isEmpty() ||
                credential.getFirstName() == null || credential.getFirstName().isEmpty() ||
                credential.getLastName() == null || credential.getLastName().isEmpty() ||
                credential.getId() != 0)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please check your inserted values. None of the parameters must be null or empty, id has to be 0.").build();
        }

        return this.register(credential);
    }

	private Logger logger() {
		return Logger.getLogger(AuthApiEndpoint.class);
	}

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(final LoginData loginData) throws InvalidCredentialsException {
    	logger().info("Login of "+loginData+" was requested.");
        final SessionData sessionData = authenticationController.login(loginData);

        return Response.ok(sessionData).build();
    }

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/logout")
    public Response logout(@Context SecurityContext securityContext)
    {
        final String email = securityContext.getUserPrincipal().getName();
        logger().info("Logout of "+email+" was requested");
        authenticationController.logout(email);
        return Response.ok(stringApiModelFactory.createStringApiModel("You were logged out! " + email)).build();
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
            logger().logException(ex, Level.INFO);
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        StringApiModel responseObj = stringApiModelFactory.createStringApiModel(responseMsg);
        return Response.ok(responseObj).build();
    }
}
