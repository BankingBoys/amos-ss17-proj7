package de.fau.amos.virtualledger.server.auth;

import de.fau.amos.virtualledger.server.model.UserCredential;
import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {
    @Test(expected = VirtualLedgerAuthenticationException.class)
    public void testRegisterWithExistingUserShouldThrowException() throws Exception {
        // Arrange

        UserCredentialRepository userCredentialRepositoryMock = mock(UserCredentialRepository.class);
        when(userCredentialRepositoryMock.existsUserCredentialEmail(someEmail())).thenReturn(Boolean.TRUE);

        AuthenticationController componentUnderTest = new AuthenticationController(userCredentialRepositoryMock);

        UserCredential credential = getValidUserCredential();
        credential.setEmail(someEmail());

        // Act
        componentUnderTest.register(credential);
    }

    @Test
    public void testRegisterWithValidUserShouldRegister() throws Exception {
        // Arrange

        UserCredentialRepository userCredentialRepositoryMock = mock(UserCredentialRepository.class);

        AuthenticationController componentUnderTest = new AuthenticationController(userCredentialRepositoryMock);

        // Act
        String result = componentUnderTest.register(getValidUserCredential());

        // Assert
        assertThat(result).isEqualTo("You were registered! blablabl@bbla.de");
    }

    private UserCredential getValidUserCredential() {
        UserCredential credential = new UserCredential();
        credential.setEmail("blablabl@bbla.de");
        credential.setFirstname("some first name");
        credential.setLastname("some last anme");
        credential.setId(0);
        credential.setPassword("some fancy passw");
        return credential;
    }

    private String someEmail() {
        return "this@specific.email";
    }
}
