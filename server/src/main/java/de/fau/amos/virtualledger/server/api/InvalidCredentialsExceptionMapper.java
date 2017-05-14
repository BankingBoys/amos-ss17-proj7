package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.auth.InvalidCredentialsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidCredentialsExceptionMapper implements ExceptionMapper<InvalidCredentialsException> {

    @Override
    public Response toResponse(InvalidCredentialsException exception) {
        return Response.status(Response.Status.FORBIDDEN).entity("Invalid credentials").build();
    }
}
