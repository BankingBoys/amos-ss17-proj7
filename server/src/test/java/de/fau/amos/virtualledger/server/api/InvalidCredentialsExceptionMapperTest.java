package de.fau.amos.virtualledger.server.api;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

import org.junit.Test;

import de.fau.amos.virtualledger.server.auth.InvalidCredentialsException;

/**
 * Created by Georg on 28.05.2017.
 */
public class InvalidCredentialsExceptionMapperTest {

    private static final int HTTP_ERROR = 403;

    @Test
    public void toResponseForbiddenStatus() {
        // SETUP
        InvalidCredentialsExceptionMapper invalidCredentialsExceptionMapper = new InvalidCredentialsExceptionMapper();

        // ACT
        Response response = invalidCredentialsExceptionMapper.toResponse(new InvalidCredentialsException());

        // ASSERT
        assertThat(response.getStatus()).isEqualTo(HTTP_ERROR);
    }
}
