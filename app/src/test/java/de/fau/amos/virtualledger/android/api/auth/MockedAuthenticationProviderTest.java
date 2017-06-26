package de.fau.amos.virtualledger.android.api.auth;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by Simon on 26.06.2017.
 */

public class MockedAuthenticationProviderTest {

    MockedAuthenticationProvider testProvider;
    String testtoken = "";

    @Before
    public void setUp() {
        testProvider = new MockedAuthenticationProvider();
    }

    @Test
    public void registerTest() {
        Observable<String> testObservable = testProvider.register("testEmail", "testpw", "testfirstname", "testlastname");
        Assertions.assertThat(testObservable).isNotNull();
    }

    @Test
    public void login() {
        Observable<String> testObservable = testProvider.login("testuser", "testpw");
        Assertions.assertThat(testObservable).isNotNull();
    }

    @Test
    public void getTokenTest() {
        Assertions.assertThat(testProvider.getToken()).isEqualTo(testtoken);
    }

    @Test
    public void logout() {
        Observable<String> testObservable = testProvider.logout();
        Assertions.assertThat(testObservable).isNotNull();
        Assertions.assertThat(testProvider.getToken()).isEqualTo(testtoken);
    }

    @Test
    public void isLoggedInTest() {
        Assertions.assertThat(testProvider.isLoggedIn()).isEqualTo(true);
    }

    @Test
    public void getUserIdTest() {
        String testUser = "test@test.de";
        Assertions.assertThat(testProvider.getUserId()).isEqualTo(testUser);
    }


}
