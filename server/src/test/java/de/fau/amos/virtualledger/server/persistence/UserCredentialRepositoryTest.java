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
    public void testeCheckLogInWithExistingCredentialsShouldLogIn() {
        // Arrange
        UserCredentialRepository componentUnderTest = new UserCredentialRepository();

        mockDatabase(componentUnderTest, Arrays.asList(matchingUserCredential()));

        // Act
        boolean result = componentUnderTest.checkLogin(loginData());

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    public void testCheckLoginWithExistingCredentialsAndWrongPasswordShouldNotLogIn() {
        // Arrange
        UserCredentialRepository componentUnderTest = new UserCredentialRepository();
        mockDatabase(componentUnderTest, Arrays.asList(notNatchingUserCredential()));

        // Act
        boolean result = componentUnderTest.checkLogin(loginData());

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    public void testCheckLoginWithNoExistingDataShouldNotLogIn() {
        // Arrange
        UserCredentialRepository componentUnderTest = new UserCredentialRepository();
        mockDatabase(componentUnderTest, Arrays.asList());

        // Act
        boolean result = componentUnderTest.checkLogin(loginData());

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    public void testExistsLogInWithNoExistingDataShouldReturnFalse() {
        // Arrange
        UserCredentialRepository componentUnderTest = new UserCredentialRepository();
        mockDatabase(componentUnderTest, Arrays.asList());

        // Act
        boolean result = componentUnderTest.existsUserCredentialEmail(loginData().getEmail());

        /// Assert
        assertThat(result).isFalse();
    }

    @Test
    public void testExistsLogInWithExistingDataShouldReturnTrue() {
        // Arrange
        UserCredentialRepository componentUnderTest = new UserCredentialRepository();
        mockDatabase(componentUnderTest, Arrays.asList(new Object()));

        // Act
        boolean result = componentUnderTest.existsUserCredentialEmail(loginData().getEmail());

        /// Assert
        assertThat(result).isTrue();
    }

    private void mockDatabase(UserCredentialRepository componentUnderTest, List<Object> dbResponse) {
        EntityManagerFactory entityManagerFactoryMock = mock(EntityManagerFactory.class);
        EntityManager entityMangerMock = mock(EntityManager.class);
        Query queryMock = mock(Query.class);
        when(queryMock.getResultList()).thenReturn(dbResponse);
        when(entityMangerMock.createQuery(anyString())).thenReturn(queryMock);
        when(entityManagerFactoryMock.createEntityManager()).thenReturn(entityMangerMock);
        componentUnderTest.setEntityManagerFactory(entityManagerFactoryMock);
    }

    private LoginData loginData() {
        return new LoginData("some@e.mail", "some pw");
    }

    private UserCredential matchingUserCredential() {
        UserCredential userCredential = new UserCredential();
        userCredential.setEmail(loginData().getEmail());
        userCredential.setPassword(loginData().getPassword());
        return userCredential;
    }

    private UserCredential notNatchingUserCredential() {
        UserCredential userCredential = new UserCredential();
        userCredential.setEmail(loginData().getEmail());
        userCredential.setPassword(loginData().getPassword() + "someOther");
        return userCredential;
    }
}
