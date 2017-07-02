package de.fau.amos.virtualledger.server.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiFacade;
import de.fau.amos.virtualledger.server.model.UserCredential;
import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;

public class AuthenticationControllerTest {
    @Test(expected = VirtualLedgerAuthenticationException.class)
    public void testRegisterWithExistingUserShouldThrowException() throws Exception {
        // Arrange
        AuthenticationController componentUnderTest = new AuthenticationController();

        UserCredentialRepository userCredentialRepositoryMock = mock(UserCredentialRepository.class);
        when(userCredentialRepositoryMock.existsUserCredentialEmail(someEmail())).thenReturn(Boolean.TRUE);
        componentUnderTest.setUserCredentialRepository(userCredentialRepositoryMock);

        UserCredential credential = getValidUserCredential();
        credential.setEmail(someEmail());

        // Act
        componentUnderTest.register(credential);
    }

    @Test
    public void testRegisterWithValidUserShouldRegister() throws Exception {
        // Arrange
        AuthenticationController componentUnderTest = new AuthenticationController();

        UserCredentialRepository userCredentialRepositoryMock = mock(UserCredentialRepository.class);
        componentUnderTest.setUserCredentialRepository(userCredentialRepositoryMock);

        BankingApiFacade bankingFacadeMock = mock(BankingApiFacade.class);
        componentUnderTest.setBankingApiFacade(bankingFacadeMock);

        // Act
        String result = componentUnderTest.register(getValidUserCredential());

        // Assert
        assertThat(result).isEqualTo("You were registered! blablabl@bbla.de");
        verify(bankingFacadeMock).createUser(getValidUserCredential().getEmail());
    }

    @Test
    public void testLoginWithValidLoginDataShouldLogIn() throws Exception {
        // Arrange
        AuthenticationController componentUnderTest = new AuthenticationController();

        LoginData validLoginData = validLoginData();
        UserCredentialRepository userCredentialRepositoryMock = mock(UserCredentialRepository.class);
        when(userCredentialRepositoryMock.checkLogin(validLoginData)).thenReturn(Boolean.TRUE);
        componentUnderTest.setUserCredentialRepository(userCredentialRepositoryMock);

        SessionIdGenerator generatorMock = mock(SessionIdGenerator.class);
        when(generatorMock.generate()).thenReturn(expectedSessionID());
        componentUnderTest.setSessionIdGenerator(generatorMock);

        // Act
        SessionData result = componentUnderTest.login(validLoginData);

        // Assert
        assertThat(result.getEmail()).isEqualTo(validLoginData.getEmail());
        assertThat(result.getSessionid()).isEqualTo(expectedSessionID());
    }

    @Test(expected = InvalidCredentialsException.class)
    public void testLoginWithInvalidLoginDataShouldThrowException() throws Exception {
        // Arrange
        AuthenticationController componentUnderTest = new AuthenticationController();

        LoginData validLoginData = validLoginData();
        UserCredentialRepository userCredentialRepositoryMock = mock(UserCredentialRepository.class);
        when(userCredentialRepositoryMock.checkLogin(validLoginData)).thenReturn(Boolean.FALSE);
        componentUnderTest.setUserCredentialRepository(userCredentialRepositoryMock);

        // Act
        componentUnderTest.login(validLoginData);
    }

    private String expectedSessionID() {
        return "some session id";
    }

    private LoginData validLoginData() {
        return new LoginData("some@mail.de", "fancy fancy");
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
