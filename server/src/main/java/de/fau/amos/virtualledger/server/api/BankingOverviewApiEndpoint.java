package de.fau.amos.virtualledger.server.api;

import java.util.List;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.sun.istack.logging.Logger;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.server.auth.Secured;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.BankingOverviewController;

/**
 * Created by Georg on 20.05.2017.
 */

/**
 * Endpoints for basic banking logic
 */
@Path("/banking")
public class BankingOverviewApiEndpoint {

    private BankingOverviewController bankingOverviewController;

    @Inject
    public BankingOverviewApiEndpoint(BankingOverviewController bankingOverviewController) {
        this.bankingOverviewController = bankingOverviewController;
    }
    protected BankingOverviewApiEndpoint() { }

    private Logger logger() {
        return Logger.getLogger(BankingOverviewApiEndpoint.class);
    }


    /**
     * Endpoint for getting banking overview data (bankaccesses + bankaccunts).
     * User must be authenticated.
     * @param securityContext
     * @return
     */
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBankingOverviewEndpoint(@Context SecurityContext securityContext)
    {
        if(securityContext.getUserPrincipal().getName() == null || securityContext.getUserPrincipal().getName().isEmpty())
        {
            return Response.status(Response.Status.FORBIDDEN).entity("Authentication failed! Your email wasn't found.").build();
        }
        final String email = securityContext.getUserPrincipal().getName();
        logger().info("getBankingOverviewEndpoint of " + email + " was requested");

        return this.getBankingOverview(email);
    }

    /**
     * Endpoint for adding a bank access. Sent parameters must not be null or empty.
     * User must be authenticated.
     * @param securityContext
     * @param bankAccessCredential
     * @return
     */
    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBankAccessEndpoint(@Context SecurityContext securityContext, BankAccessCredential bankAccessCredential)
    {
        if(securityContext.getUserPrincipal().getName() == null || securityContext.getUserPrincipal().getName().isEmpty())
        {
            return Response.status(Response.Status.FORBIDDEN).entity("Authentication failed! Your email wasn't found.").build();
        } if(bankAccessCredential.getBankcode() == null || bankAccessCredential.getBankcode().isEmpty() ||
            bankAccessCredential.getBanklogin() == null || bankAccessCredential.getBanklogin().isEmpty() ||
            bankAccessCredential.getPin() == null || bankAccessCredential.getPin().isEmpty())
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please check your inserted values. None of the parameters must be null or empty.").build();
        }
        final String email = securityContext.getUserPrincipal().getName();
        logger().info("addBankAccessEndpoint was requested by " + email);

        return this.addBankAccess(email, bankAccessCredential);
    }

    /**
     * Endpoint for deleting a bank access. bankAccessId null or empty.
     * User must be authenticated.
     * @param securityContext
     * @param bankAccessId
     * @return
     */
    @DELETE
    @Path("/{bankAccessId}")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteBankAccessEndpoint(@Context SecurityContext securityContext, @PathParam("bankAccessId") String bankAccessId)
    {
        if(securityContext.getUserPrincipal().getName() == null || securityContext.getUserPrincipal().getName().isEmpty())
        {
            return Response.status(Response.Status.FORBIDDEN).entity("Authentication failed! Your email wasn't found.").build();
        } if(bankAccessId == null || bankAccessId.isEmpty())
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please check your inserted values. None of the parameters must be null or empty.").build();
        }
        final String email = securityContext.getUserPrincipal().getName();
        logger().info("deleteBankAccessEndpoint was requested by " + email);

        return this.deleteBankAccess(email, bankAccessId);
    }

    @DELETE
    @Path("/{bankAccessId}/{bankAccountId}")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteBankAccess(@Context SecurityContext securityContext, @PathParam("bankAccessId") String bankAccessId, @PathParam("bankAccountId") String bankAccountId)
    {
        final String email = securityContext.getUserPrincipal().getName();
        try
        {
            bankingOverviewController.deleteBankAccount(email, bankAccessId, bankAccountId);
        } catch (BankingException ex)
        {
            logger().logException(ex, Level.INFO);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("/sync")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response syncBankAccounts(@Context SecurityContext securityContext, List<BankAccountSync> bankAccountSyncList)
    {
        final String email = securityContext.getUserPrincipal().getName();
        try
        {
            bankingOverviewController.syncBankAccounts(email, bankAccountSyncList);
        } catch (BankingException ex)
        {
            logger().logException(ex, Level.INFO);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).build();
    }


    /**
     * Does the logic for getting the banking overview data.
     * Handles exceptions and returns corresponding response codes.
     * @param email
     * @return
     */
    private Response getBankingOverview(String email)
    {
        List<BankAccess> bankAccesses = null;
        try
        {
            bankAccesses = bankingOverviewController.getBankingOverview(email);
        } catch (BankingException ex)
        {
            logger().logException(ex, Level.INFO);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(bankAccesses).build();
    }

    private Response addBankAccess(String email, BankAccessCredential bankAccessCredential)
    {
        try {
            bankingOverviewController.addBankAccess(email, bankAccessCredential);
        } catch (BankingException ex)
        {
            logger().logException(ex, Level.INFO);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    private Response deleteBankAccess(String email, String bankAccessId)
    {
        try
        {
            bankingOverviewController.deleteBankAccess(email, bankAccessId);
        } catch (BankingException ex)
        {
            logger().logException(ex, Level.INFO);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).build();
    }
}
