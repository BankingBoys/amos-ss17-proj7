package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.model.UserCredential;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Georg on 07.05.2017.
 */
@Path("/auth")
public class AuthController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(UserCredential credential) {

        if(credential == null || credential.getEmail() == null || credential.getPassword() == null)
        { // if not null, matches the pattern -> specified in model class
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        EntityManager entityManager = Persistence.createEntityManagerFactory("auth-db").createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(credential);
        entityManager.getTransaction().commit();
        entityManager.close();

        // TODO call bean that inserts the credential into the database if email isn't used yet
        return Response.ok("You were registered! " + credential.getEmail()).build();
    }
}
