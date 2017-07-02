package de.fau.amos.virtualledger.android.api.auth;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    public void loginTest() {
        Observable<String> testObservable = testProvider.login("testuser", "testpw");
        Assertions.assertThat(testObservable).isNotNull();
    }

    @Test
    public void getTokenTest() {
        testProvider.getToken()
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull final String token) throws Exception {

                        Assertions.assertThat(token).isEqualTo(testtoken);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull final Throwable throwable) throws Exception {

                        Assertions.fail(throwable.getMessage());
                    }
                });
    }

    @Test
    public void logoutTest() {
        Observable<String> testObservable = testProvider.logout();
        Assertions.assertThat(testObservable).isNotNull();
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
