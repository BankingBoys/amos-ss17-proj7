package de.fau.amos.virtualledger.android.api.banking;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 01.07.2017.
 */

public class MockedBankingProviderTest {

    MockedBankingProvider mockedBankingProvider;

    @Before
    public void setUp() {
        mockedBankingProvider = new MockedBankingProvider();
    }

    @Test
    public void getBankingOverviewTest() {
        Observable testObservable = mockedBankingProvider.getBankingOverview();
        assertThat(testObservable).isNotNull();
    }
}
