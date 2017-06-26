package de.fau.amos.virtualledger.android.api.auth;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Simon on 26.06.2017.
 */

public class MockedAuthenticationProviderTest {

    MockedAuthenticationProvider testProvider;

    @Before
    public void setUp() {
        testProvider = new MockedAuthenticationProvider();
    }

}
