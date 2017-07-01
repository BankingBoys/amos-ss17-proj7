package de.fau.amos.virtualledger.android.api.banking;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
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

    @Test
    public void getBankingTransactionTest() {
        Observable testObservable = mockedBankingProvider.getBankingTransactions(new ArrayList<BankAccountSync>());
        assertThat(testObservable).isNotNull();
    }

    @Test
    public void addBankAccessTest() {
        Observable testObservable = mockedBankingProvider.addBankAccess(new BankAccessCredential("1","1","1"));
        assertThat(testObservable).isNotNull();
    }

    @Test
    public void deleteBankAccessTest() {
        Observable testObservable = mockedBankingProvider.deleteBankAccess("testId");
        assertThat(testObservable).isNotNull();
    }

}
