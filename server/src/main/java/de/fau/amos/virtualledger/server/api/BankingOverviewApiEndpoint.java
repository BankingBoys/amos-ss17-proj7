package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import de.fau.amos.virtualledger.server.auth.Secured;
import de.fau.amos.virtualledger.server.banking.BankingOverviewController;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Endpoints for basic banking logic
 */
@Path("/banking")
public class BankingOverviewApiEndpoint {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private BankingOverviewController bankingOverviewController;

    @Autowired
    public BankingOverviewApiEndpoint(BankingOverviewController bankingOverviewController) {
        this.bankingOverviewController = bankingOverviewController;
    }
    protected BankingOverviewApiEndpoint() { }


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
        logger.info("getBankingOverviewEndpoint of " + email + " was requested");

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
        logger.info("addBankAccessEndpoint was requested by " + email);

        return this.addBankAccess(email, bankAccessCredential);
    }

    /**
     * Endpoint for deleting a bank access. bankAccessId must not be null or empty.
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
        logger.info("deleteBankAccessEndpoint was requested by " + email);

        return this.deleteBankAccess(email, bankAccessId);
    }

    /**
     * Endpoint for deleting a bank account. bankAccessId and bankAccountId must not be null or empty.
     * User must be authenticated.
     * @param securityContext
     * @param bankAccessId
     * @param bankAccountId
     * @return
     */
    @DELETE
    @Path("/{bankAccessId}/{bankAccountId}")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteBankAccountEndpoint(@Context SecurityContext securityContext, @PathParam("bankAccessId") String bankAccessId, @PathParam("bankAccountId") String bankAccountId)
    {
        if(securityContext.getUserPrincipal().getName() == null || securityContext.getUserPrincipal().getName().isEmpty())
        {
            return Response.status(Response.Status.FORBIDDEN).entity("Authentication failed! Your email wasn't found.").build();
        } if(bankAccessId == null || bankAccessId.isEmpty() ||
            bankAccountId == null || bankAccountId.isEmpty())
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please check your inserted values. None of the parameters must be null or empty.").build();
        }
        final String email = securityContext.getUserPrincipal().getName();
        logger.info("deleteBankAccountEndpoint was requested by " + email);

        return this.deleteBankAccount(email, bankAccessId, bankAccountId);
    }

    /**
     * Endpoint for synchronizing bank accounts. bankAccountSyncList must not be null, all BankAccountSync must contain parameters that are not null or empty.
     * User must be authenticated.
     * @param securityContext
     * @param bankAccountSyncList
     * @return
     */
    @PUT
    @Path("/sync")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response syncBankAccountsEndpoint(@Context SecurityContext securityContext, List<BankAccountSync> bankAccountSyncList)
    {
        if(securityContext.getUserPrincipal().getName() == null || securityContext.getUserPrincipal().getName().isEmpty())
        {
            return Response.status(Response.Status.FORBIDDEN).entity("Authentication failed! Your email wasn't found.").build();
        } if(bankAccountSyncList == null)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please check your inserted values. None of the parameters must be null.").build();
        }
        for(BankAccountSync bankAccountSync: bankAccountSyncList)
        {
            if(bankAccountSync == null ||
                    bankAccountSync.getBankaccessid() == null || bankAccountSync.getBankaccessid().isEmpty()||
                    bankAccountSync.getBankaccountid() == null || bankAccountSync.getBankaccountid().isEmpty()||
                    bankAccountSync.getPin() == null || bankAccountSync.getPin().isEmpty())
            {
                return Response.status(Response.Status.BAD_REQUEST).entity("Please check your inserted values. None of the parameters must be null or empty.").build();
            }
        }

        final String email = securityContext.getUserPrincipal().getName();
        logger.info("syncBankAccountsEndpoint was requested by " + email);

        return this.syncBankAccounts(email, bankAccountSyncList);
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
            logger.error("", ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(bankAccesses).build();
    }

    /**
     * Does the logic for adding a bank access.
     * Handles exceptions and returns corresponding response codes.
     * @param email
     * @param bankAccessCredential
     * @return
     */
    private Response addBankAccess(String email, BankAccessCredential bankAccessCredential)
    {
        try {
            BankAccess addedBankAccess = bankingOverviewController.addBankAccess(email, bankAccessCredential);
            return Response.status(Response.Status.CREATED).entity(addedBankAccess).build();
        } catch (BankingException ex)
        {
            logger.error("", ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Does the logic for deleting a bank access.
     * Handles exceptions and returns corresponding response codes.
     * @param email
     * @param bankAccessId
     * @return
     */
    private Response deleteBankAccess(String email, String bankAccessId)
    {
        try
        {
            bankingOverviewController.deleteBankAccess(email, bankAccessId);
        } catch (BankingException ex)
        {
            logger.error("", ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    /**
     * Does the logic for deleting a bank account.
     * Handles exceptions and returns corresponding response codes.
     * @param email
     * @param bankAccessId
     * @param bankAccountId
     * @return
     */
    private Response deleteBankAccount(String email, String bankAccessId, String bankAccountId)
    {
        try
        {
            bankingOverviewController.deleteBankAccount(email, bankAccessId, bankAccountId);
        } catch (BankingException ex)
        {
            logger.error("", ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).build();
    }

    /**
     * Does the logic for synchronizing a list of bank accounts.
     * Handles exceptions and returns corresponding response codes.
     * @param email
     * @param bankAccountSyncList
     * @return
     */
    private Response syncBankAccounts(String email, List<BankAccountSync> bankAccountSyncList)
    {
        try
        {
            final BankAccountSyncResult result = bankingOverviewController.syncBankAccounts(email, bankAccountSyncList);
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (BankingException ex)
        {
            logger.error("", ex);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
