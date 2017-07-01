package de.fau.amos.virtualledger.android.api.savings;

import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import io.reactivex.Observable;

/**
 * Created by Simon on 01.07.2017.
 */

public class MockedSavingsProviderTest {
    MockedSavingsProvider mockedSavingsProvider;

    @Before
    public void setUp() {
        mockedSavingsProvider= new MockedSavingsProvider();
    }

    @Test
    public void getSavingAccountsTest() {
        Observable testObservable = mockedSavingsProvider.getSavingAccounts();
        assertThat(testObservable).isNotNull();
    }
}
