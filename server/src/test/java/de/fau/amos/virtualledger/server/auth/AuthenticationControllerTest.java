package de.fau.amos.virtualledger.server.auth;

import de.fau.amos.virtualledger.server.model.User;
import de.fau.amos.virtualledger.server.persistence.UserRepository;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTest {
    @Test(expected = UserAlreadyExistsException.class)
    public void testRegisterWithExistingUserShouldThrowException() throws Exception {
        // Arrange

        UserRepository userRepositoryMock = mock(UserRepository.class);
        when(userRepositoryMock.existsUserWithEmail(someEmail())).thenReturn(Boolean.TRUE);

        AuthenticationController componentUnderTest = new AuthenticationController(userRepositoryMock);

        User user = getValidUserCredential();
        user.setEmail(someEmail());

        // Act
        componentUnderTest.register(user);
    }

    @Test
    public void testRegisterWithValidUserShouldRegister() throws Exception {
        // Arrange

        UserRepository userRepositoryMock = mock(UserRepository.class);

        AuthenticationController componentUnderTest = new AuthenticationController(userRepositoryMock);

        // Act
        String result = componentUnderTest.register(getValidUserCredential());

        // Assert
        assertThat(result).isEqualTo("You were registered! blablabl@bbla.de");
    }

    private User getValidUserCredential() {
        User user = new User();
        user.setEmail("blablabl@bbla.de");
        user.setFirstName("some first name");
        user.setLastName("some last anme");
        user.setId(0);
        return user;
    }

    private String someEmail() {
        return "this@specific.email";
    }
}
