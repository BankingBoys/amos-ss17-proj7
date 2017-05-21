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

    /**
     *
     */
    @Inject
    AuthenticationController authenticationController;

    /**
     *
     */
    @Inject
    StringApiModelFactory stringApiModelFactory;

    /**
     * Endpoint for registering a new user.
     * @param credential
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(UserCredential credential) {
    	logger().info("Registration for "+credential+" was requested.");
        String responseMsg;
        try
        {
            responseMsg = authenticationController.register(credential);
        } catch(VirtualLedgerAuthenticationException ex)
        {
        	logger().logException(ex, Level.CONFIG);
        	return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        StringApiModel responseObj = stringApiModelFactory.createStringApiModel(responseMsg);
        return Response.ok(responseObj).build();
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
}
