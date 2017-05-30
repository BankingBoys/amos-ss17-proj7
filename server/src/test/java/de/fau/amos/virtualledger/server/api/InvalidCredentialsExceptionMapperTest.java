package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.auth.InvalidCredentialsException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Response;

/**
 * Created by Georg on 28.05.2017.
 */
public class InvalidCredentialsExceptionMapperTest {

    @Test
    public void toResponse_forbiddenStatus() {
        // SETUP
        InvalidCredentialsExceptionMapper invalidCredentialsExceptionMapper = new InvalidCredentialsExceptionMapper();

        // ACT
        Response response = invalidCredentialsExceptionMapper.toResponse(new InvalidCredentialsException());

        // ASSERT
        Assert.assertNotNull(response);
        int expectedStatusCode = 403;
        Assert.assertTrue(response.getStatus() == expectedStatusCode);

    }
}
