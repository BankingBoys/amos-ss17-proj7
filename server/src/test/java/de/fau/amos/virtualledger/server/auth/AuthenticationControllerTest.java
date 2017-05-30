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
	public void test_register_withExistingUser_shouldThrowException() throws Exception {
		//Arrange
		AuthenticationController component_under_test = new AuthenticationController();

		UserCredentialRepository userCredentialRepositoryMock = mock(UserCredentialRepository.class);
		when(userCredentialRepositoryMock.existsUserCredentialEmail(someEmail())).thenReturn(Boolean.TRUE);
		component_under_test.userCredentialRepository = userCredentialRepositoryMock;

		UserCredential credential = getValidUserCredential();
		credential.setEmail(someEmail());

		//Act
		component_under_test.register(credential);
	}

	@Test
	public void test_register_withValidUser_shouldRegister() throws Exception {
		//Arrange
		AuthenticationController component_under_test = new AuthenticationController();

		UserCredentialRepository userCredentialRepositoryMock = mock(UserCredentialRepository.class);
		component_under_test.userCredentialRepository = userCredentialRepositoryMock;

		BankingApiFacade bankingFacadeMock = mock(BankingApiFacade.class);
		component_under_test.bankingApiFacade = bankingFacadeMock;

		//Act
		String result = component_under_test.register(getValidUserCredential());

		//Assert
		assertThat(result).isEqualTo("You were registered! blablabl@bbla.de");
		verify(bankingFacadeMock).createUser(getValidUserCredential().getEmail());
	}
	
	@Test
	public void test_login_withValidLoginData_shouldLogIn() throws Exception {
		//Arrange
		AuthenticationController component_under_test = new AuthenticationController();
		
		LoginData validLoginData = validLoginData();
		UserCredentialRepository userCredentialRepositoryMock = mock(UserCredentialRepository.class);
		when(userCredentialRepositoryMock.checkLogin(validLoginData)).thenReturn(Boolean.TRUE);
		component_under_test.userCredentialRepository= userCredentialRepositoryMock;
		
		SessionIdGenerator generatorMock = mock(SessionIdGenerator.class);
		when(generatorMock.generate()).thenReturn(expectedSessionID());
		component_under_test.sessionIdGenerator = generatorMock;
		
		//Act
		SessionData result = component_under_test.login(validLoginData);
		
		//Assert
		assertThat(result.getEmail()).isEqualTo(validLoginData.email);
		assertThat(result.getSessionid()).isEqualTo(expectedSessionID());
	}

	@Test(expected=InvalidCredentialsException.class)
	public void test_login_withInvalidLoginData_shouldThrowException() throws Exception {
		// Arrange
		AuthenticationController component_under_test = new AuthenticationController();
		
		LoginData validLoginData = validLoginData();
		UserCredentialRepository userCredentialRepositoryMock = mock(UserCredentialRepository.class);
		when(userCredentialRepositoryMock.checkLogin(validLoginData)).thenReturn(Boolean.FALSE);
		component_under_test.userCredentialRepository= userCredentialRepositoryMock;
		
		//Act
		component_under_test.login(validLoginData);
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
		credential.setFirstName("some first name");
		credential.setLastName("some last anme");
		credential.setId(0);
		credential.setPassword("some fancy passw");
		return credential;
	}

	private String someEmail() {
		return "this@specific.email";
	}
}
