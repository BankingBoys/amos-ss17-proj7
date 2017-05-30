package de.fau.amos.virtualledger.server.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Test;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.server.model.UserCredential;

public class UserCredentialRepositoryTest {
	@Test
	public void teste_checkLogIn_withExistingCredentials_shouldLogIn() {
		// Arrange
		UserCredentialRepository component_under_test = new UserCredentialRepository();

		mockDatabase(component_under_test, Arrays.asList(matchingUserCredential()));
		
		//Act
		boolean result = component_under_test.checkLogin(loginData());

		//Assert
		assertThat(result).isTrue();
	}

	@Test
	public void test_checkLogin_withExistingCredentialsAndWrongPassword_shouldNotLogIn() {
		// Arrange
		UserCredentialRepository component_under_test = new UserCredentialRepository();
		mockDatabase(component_under_test, Arrays.asList(notNatchingUserCredential()));

		//Act
		boolean result = component_under_test.checkLogin(loginData());

		//Assert
		assertThat(result).isFalse();
	}

	@Test
	public void test_checkLogin_withNoExistingData_shouldNotLogIn() {
		// Arrange
		UserCredentialRepository component_under_test = new UserCredentialRepository();
		mockDatabase(component_under_test, Arrays.asList());

		//Act
		boolean result = component_under_test.checkLogin(loginData());

		//Assert
		assertThat(result).isFalse();
	}

	@Test
	public void test_existsLogIn_withNoExistingData_shouldReturnFalse() {
		// Arrange
		UserCredentialRepository component_under_test = new UserCredentialRepository();
		mockDatabase(component_under_test, Arrays.asList());

		//Act
		boolean result = component_under_test.existsUserCredentialEmail(loginData().email);

		///Assert
		assertThat(result).isFalse();
	}

	@Test
	public void test_existsLogIn_withExistingData_shouldReturnTrue() {
		// Arrange
		UserCredentialRepository component_under_test = new UserCredentialRepository();
		mockDatabase(component_under_test, Arrays.asList(new Object()));

		//Act
		boolean result = component_under_test.existsUserCredentialEmail(loginData().email);

		///Assert
		assertThat(result).isTrue();
	}

	private void mockDatabase(UserCredentialRepository component_under_test, List<Object> dbResponse) {
		EntityManagerFactory entityManagerFactoryMock = mock(EntityManagerFactory.class);
		EntityManager entityMangerMock = mock(EntityManager.class);
		Query queryMock = mock(Query.class);
		when(queryMock.getResultList()).thenReturn(dbResponse);
		when(entityMangerMock.createQuery(anyString())).thenReturn(queryMock);
		when(entityManagerFactoryMock.createEntityManager()).thenReturn(entityMangerMock);
		component_under_test.entityManagerFactory = entityManagerFactoryMock;
	}

	private LoginData loginData() {
		return new LoginData("some@e.mail", "some pw");
	}

	private UserCredential matchingUserCredential() {
		UserCredential userCredential = new UserCredential();
		userCredential.setEmail(loginData().email);
		userCredential.setPassword(loginData().password);
		return userCredential;
	}

	private UserCredential notNatchingUserCredential() {
		UserCredential userCredential = new UserCredential();
		userCredential.setEmail(loginData().email);
		userCredential.setPassword(loginData().password + "someOther");
		return userCredential;
	}
}
