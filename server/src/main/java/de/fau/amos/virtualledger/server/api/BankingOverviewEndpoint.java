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
import de.fau.amos.virtualledger.server.auth.Secured;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.BankingOverviewController;

/**
 * Created by Georg on 20.05.2017.
 */
@Path("/banking")
public class BankingOverviewEndpoint {

    @Inject
    BankingOverviewController bankingOverviewController;


    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBankingOverview(@Context SecurityContext securityContext)
    {
        final String email = securityContext.getUserPrincipal().getName();
        List<BankAccess> bankAccesses = null;
        try
        {
            bankAccesses = bankingOverviewController.getBankingOverview(email);
        } catch (Exception ex)
        {
            logger().logException(ex, Level.INFO);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(bankAccesses).build();
    }

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBankAccess(@Context SecurityContext securityContext, BankAccessCredential bankAccessCredential)
    {
        final String email = securityContext.getUserPrincipal().getName();
        try {
            bankingOverviewController.addBankAccess(email, bankAccessCredential);
        } catch (Exception ex)
        {
            logger().logException(ex, Level.INFO);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{bankAccessId}")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteBankAccess(@Context SecurityContext securityContext, @PathParam("bankAccessId") String bankAccessId)
    {
        final String email = securityContext.getUserPrincipal().getName();
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


    private Logger logger() {
        return Logger.getLogger(AuthApiEndpoint.class);
    }
}
