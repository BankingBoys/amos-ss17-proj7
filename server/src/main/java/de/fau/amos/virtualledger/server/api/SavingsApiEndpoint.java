package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.SavingsAccount;
import de.fau.amos.virtualledger.server.auth.Secured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.lang.invoke.MethodHandles;
import java.util.*;

/**
 * Endpoints for savings
 */
@Path("/savings")
public class SavingsApiEndpoint {

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSavingAccountsEndpoint(@Context SecurityContext securityContext) {
        if(securityContext.getUserPrincipal().getName() == null || securityContext.getUserPrincipal().getName().isEmpty())
        {
            return Response.status(Response.Status.FORBIDDEN).entity("Authentication failed! Your email wasn't found.").build();
        }
        final String email = securityContext.getUserPrincipal().getName();
        logger.info("getSavingAccountsEndpoint of " + email + " was requested");

        return this.getSavingAccounts(email);
    }

    private Response getSavingAccounts(String email) {
        return Response.ok().build();
    }

}
