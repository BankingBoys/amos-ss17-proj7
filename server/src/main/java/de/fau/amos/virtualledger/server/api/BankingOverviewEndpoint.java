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
import de.fau.amos.virtualledger.server.controllers.BankingOverviewController;

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
    @Produces(MediaType.APPLICATION_JSON)
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



    private Logger logger() {
        return Logger.getLogger(AuthApiEndpoint.class);
    }
}
