package de.fau.amos.virtualledger.server.banking.api;

import de.fau.amos.virtualledger.server.banking.api.testEndpoint.DummyTestEndpoint;
import de.fau.amos.virtualledger.server.banking.api.testEndpoint.HttpTestEndpoint;
import de.fau.amos.virtualledger.server.banking.api.testEndpoint.TestEndpoint;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Georg on 18.05.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class BankingApiBinderTest {

    BankingApiBinder bankingApiBinder;

    @Mock
    BankingApiConfiguration bankingApiConfiguration;

    @Before
    public void setUp()
    {
        bankingApiBinder = new BankingApiBinder();
    }

    @Test
    public void getTestEndpoint_dummy()
    {
        // SETUP
        when(bankingApiConfiguration.isUseTestDummy())
                .thenReturn(true);
        bankingApiBinder.setConfiguration(bankingApiConfiguration);
        bankingApiBinder.setTestEndpoint(new DummyTestEndpoint(), new HttpTestEndpoint());

        // EXECUTE
        TestEndpoint testEndpoint = bankingApiBinder.getTestEndpoint();

        // ASSERT
        Assert.isNotNull(testEndpoint, "testEndpoint was null!");
        Assert.isTrue(testEndpoint instanceof DummyTestEndpoint, "testEndpoint was not a DummyTestEndpoint!");
        verify(bankingApiConfiguration, times(1))
                .isUseTestDummy();
    }


    @Test
    public void getTestEndpoint_http()
    {
        // SETUP
        when(bankingApiConfiguration.isUseTestDummy())
                .thenReturn(false);
        bankingApiBinder.setConfiguration(bankingApiConfiguration);
        bankingApiBinder.setTestEndpoint(new DummyTestEndpoint(), new HttpTestEndpoint());

        // EXECUTE
        TestEndpoint testEndpoint = bankingApiBinder.getTestEndpoint();

        // ASSERT
        Assert.isNotNull(testEndpoint, "testEndpoint was null!");
        Assert.isTrue(testEndpoint instanceof HttpTestEndpoint, "testEndpoint was not a HttpTestEndpoint!");
        verify(bankingApiConfiguration, times(1))
                .isUseTestDummy();
    }
}
