package de.fau.amos.virtualledger.server;

import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiFacade;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Test class
 */
@Path("test")
public class TestRestProvider {

    @Autowired
    private BankingApiFacade bankingFacade;


    /**
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloWorld() {
        return Response.ok("Hello world").build();
    }
}
