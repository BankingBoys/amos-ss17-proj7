package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.model.UserCredential;
import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;

import javax.inject.Inject;
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

    @Inject
    UserCredentialRepository userCredentialRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(UserCredential credential) {

        if(credential == null || credential.getEmail() == null || credential.getPassword() == null)
        { // if not null, matches the pattern -> specified in model class
            return Response.status(Response.Status.BAD_REQUEST).entity("Please check your inserts! At least one was not formatted correctly!").build();
        }
        if(this.userCredentialRepository.existsUserCredentialEmail(credential.getEmail()))
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("There already exists an account with this Email address.").build();
        }

        this.userCredentialRepository.createUserCredential(credential);

        // TODO call bean that inserts the credential into the database if email isn't used yet
        return Response.ok("You were registered! " + credential.getEmail()).build();
    }
}
