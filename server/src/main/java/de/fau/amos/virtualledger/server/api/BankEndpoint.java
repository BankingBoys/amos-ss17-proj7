package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import de.fau.amos.virtualledger.server.factories.StringApiModelFactory;
import de.fau.amos.virtualledger.server.bank.BankController;
import de.fau.amos.virtualledger.server.bank.InvalidBanksException;
import de.fau.amos.virtualledger.server.model.BankAccess;
import de.fau.amos.virtualledger.server.model.BankAccount;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Endpoints for Bank / Our Database
 */
@Path("/bank")
public class BankEndpoint {

    /**
     *
     */
    @Inject
    BankController bankController;

    /**
     *
     */
    @Inject
    StringApiModelFactory stringApiModelFactory;

    /**
     * Endpoint for create BankAccess.
     * @param access
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createaccess")
    public Response createAccess(BankAccess access) {

        String responseMsg;
        try
        {
            responseMsg = bankController.createAccess(access);
        } catch(InvalidBanksException ex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        StringApiModel responseObj = stringApiModelFactory.createStringApiModel(responseMsg);
        return Response.ok(responseObj).build();
    }


    /**
     * Endpoint for delete BankAccess.
     * @param access
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteaccess")
    public Response deleteAccess(BankAccess access) {

        String responseMsg;
        try
        {
            responseMsg = bankController.deleteAccess(access);
        } catch(InvalidBanksException ex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        StringApiModel responseObj = stringApiModelFactory.createStringApiModel(responseMsg);
        return Response.ok(responseObj).build();
    }


    /**
     * Endpoint for create BankAccount.
     * @param account
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createaccount")
    public Response createAccount(BankAccount account) {

        String responseMsg;
        try
        {
            responseMsg = bankController.createAccount(account);
        } catch(InvalidBanksException ex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        StringApiModel responseObj = stringApiModelFactory.createStringApiModel(responseMsg);
        return Response.ok(responseObj).build();
    }


    /**
     * Endpoint for delete BankAccount.
     * @param account
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteaccount")
    public Response deleteAccount(BankAccount account) {

        String responseMsg;
        try
        {
            responseMsg = bankController.deleteAccount(account);
        } catch(InvalidBanksException ex)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        StringApiModel responseObj = stringApiModelFactory.createStringApiModel(responseMsg);
        return Response.ok(responseObj).build();
    }

}
