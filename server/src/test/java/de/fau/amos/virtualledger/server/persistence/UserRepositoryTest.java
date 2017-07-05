package de.fau.amos.virtualledger.server.persistence;

import de.fau.amos.virtualledger.dtos.LoginData;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRepositoryTest {

    @Test
    public void testExistsLogInWithNoExistingDataShouldReturnFalse() {
        // Arrange
        UserRepository componentUnderTest = mockDatabase(Arrays.asList());

        // Act
        boolean result = componentUnderTest.existsUserWithEmail(loginData().getEmail());

        /// Assert
        assertThat(result).isFalse();
    }

    @Test
    public void testExistsLogInWithExistingDataShouldReturnTrue() {
        // Arrange
        UserRepository componentUnderTest = mockDatabase(Arrays.asList(new Object()));

        // Act
        boolean result = componentUnderTest.existsUserWithEmail(loginData().getEmail());

        /// Assert
        assertThat(result).isTrue();
    }

    private UserRepository mockDatabase(List<Object> dbResponse) {
        EntityManagerFactory entityManagerFactoryMock = mock(EntityManagerFactory.class);
        EntityManager entityMangerMock = mock(EntityManager.class);
        Query queryMock = mock(Query.class);
        when(queryMock.getResultList()).thenReturn(dbResponse);
        when(entityMangerMock.createQuery(anyString())).thenReturn(queryMock);
        when(entityManagerFactoryMock.createEntityManager()).thenReturn(entityMangerMock);

        EntityManagerFactoryProvider entityManagerFactoryProvider = mock(EntityManagerFactoryProvider.class);
        when(entityManagerFactoryProvider.getEntityManagerFactory()).thenReturn(entityManagerFactoryMock);

        return new UserRepository(entityManagerFactoryProvider);
    }

    private LoginData loginData() {
        return new LoginData("some@e.mail", "some pw");
    }
}
