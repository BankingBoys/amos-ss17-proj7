package de.fau.amos.virtualledger.android.api.savings;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import de.fau.amos.virtualledger.dtos.SavingsAccount;
import io.reactivex.Observable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 01.07.2017.
 */

public class MockedSavingsProviderTest {
    MockedSavingsProvider mockedSavingsProvider;

    @Before
    public void setUp() {
        mockedSavingsProvider = new MockedSavingsProvider();
    }

    @Test
    public void get() {
        Observable testObservable = mockedSavingsProvider.get();
        assertThat(testObservable).isNotNull();
    }

    @Test
    public void add() {
        Observable testObservable = mockedSavingsProvider.add(new SavingsAccount("1", "name", 0, 0, new Date(12312), new Date(12312)));
        assertThat(testObservable).isNotNull();
    }

}
