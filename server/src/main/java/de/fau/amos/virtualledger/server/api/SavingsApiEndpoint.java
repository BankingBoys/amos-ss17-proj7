package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.SavingsAccount;
import de.fau.amos.virtualledger.server.auth.Secured;
import de.fau.amos.virtualledger.server.savings.SavingsController;
import de.fau.amos.virtualledger.server.savings.SavingsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
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
    private SavingsController savingsController;

    @Inject
    public SavingsApiEndpoint(SavingsController savingsController) {
        this.savingsController = savingsController;
    }
    protected SavingsApiEndpoint()  {}

    /**
     * Gets all available saving accounts to the authenticated user
     * @param securityContext
     * @return
     */
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


    /**
     * Adds a saving accounts to the authenticated user
     * @param securityContext
     * @param savingsAccount
     * @return status 201 if successful
     */
    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSavingAccountEndpoint(@Context SecurityContext securityContext, SavingsAccount savingsAccount) {
        if(securityContext.getUserPrincipal().getName() == null || securityContext.getUserPrincipal().getName().isEmpty())
        {
            return Response.status(Response.Status.FORBIDDEN).entity("Authentication failed! Your email wasn't found.").build();
        } if(savingsAccount == null
                || (savingsAccount.getId() != null && ! savingsAccount.getId().isEmpty())
                || savingsAccount.getName() == null || savingsAccount.getName().isEmpty()
                || savingsAccount.getFinaldate() == null ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please check your inserted values. None of the parameters must be null or empty.").build();
        }
        final String email = securityContext.getUserPrincipal().getName();
        logger.info("getSavingAccountsEndpoint of " + email + " was requested");

        return this.addSavingAccount(email, savingsAccount);
    }

    /**
     * Does the logic for adding a saving account to a specific user.
     * Handles exceptions and returns corresponding response codes.
     * @param email
     * @param savingsAccount
     * @return status 201 if successful
     */
    private Response addSavingAccount(String email, SavingsAccount savingsAccount) {

        try {
            savingsController.addSavingAccount(email, savingsAccount);
        } catch (SavingsException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Does the logic for getting all saving accounts to a specific user.
     * Handles exceptions and returns corresponding response codes.
     * @param email
     * @return
     */
    private Response getSavingAccounts(String email) {

        List<SavingsAccount> savingsAccountList = savingsController.getSavingAccounts(email);
        return Response.ok(savingsAccountList).build();
    }

}
