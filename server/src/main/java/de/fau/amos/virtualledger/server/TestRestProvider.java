package de.fau.amos.virtualledger.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("test")
public class TestRestProvider {

    @GET
    public Response helloWorld() {
        return Response.ok("Hello World With Travis CI Test").build();
    }
}
 