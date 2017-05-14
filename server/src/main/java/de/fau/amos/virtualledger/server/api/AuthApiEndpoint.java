package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.LogoutApiModel;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import de.fau.amos.virtualledger.server.api.modelFactories.StringApiModelFactory;
import de.fau.amos.virtualledger.server.auth.AuthenticationController;
import de.fau.amos.virtualledger.server.auth.InvalidCredentialsException;
import de.fau.amos.virtualledger.server.auth.VirtualLedgerAuthenticationException;
import de.fau.amos.virtualledger.server.model.UserCredential;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

        String responseMsg;
        try
        {
            responseMsg = authenticationController.register(credential);
        } catch(VirtualLedgerAuthenticationException ex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        StringApiModel responseObj = stringApiModelFactory.createStringApiModel(responseMsg);
        return Response.ok(responseObj).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(final LoginData loginData) throws InvalidCredentialsException {
        final SessionData sessionData = authenticationController.login(loginData);

        return Response.ok(sessionData).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/logout")
    public Response logout(LogoutApiModel logoutApiModel)
    {
        authenticationController.logout(logoutApiModel);
        return Response.ok(stringApiModelFactory.createStringApiModel("You were logged out! " + logoutApiModel.getemail())).build();
    }
}
