package de.fau.amos.virtualledger.server.banking.api;

import de.fau.amos.virtualledger.server.banking.api.userEndpoint.DummyUserEndpoint;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Created by Georg on 18.05.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class BankingApiFacadeTest {

    BankingApiFacade bankingApiFacade;

    @Mock
    BankingApiBinder bankingApiBinder;

    @Before
    public void setUp()
    {
        bankingApiFacade = new BankingApiFacade();
    }

    @Test
    public void getTest_binderCalled()
    {
        // SETUP
        String dummyResult = "test";
        DummyUserEndpoint endpoint = mock(DummyUserEndpoint.class);
        when(endpoint.testEndpointMethod1())
                .thenReturn(dummyResult);
        when(bankingApiBinder.getUserEndpoint())
                .thenReturn(endpoint);
        bankingApiFacade.setBinder(bankingApiBinder);

        // ACT
        String result = bankingApiFacade.getTest();

        // ASSERT
        Assert.isEqual(result, dummyResult, "Returned value was not as expected!");
        Mockito.verify(bankingApiBinder, times(1))
                .getUserEndpoint();
    }
}
