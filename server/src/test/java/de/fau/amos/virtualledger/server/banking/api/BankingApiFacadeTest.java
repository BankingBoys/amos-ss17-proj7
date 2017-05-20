package de.fau.amos.virtualledger.server.banking.api;

import de.fau.amos.virtualledger.server.banking.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.api.bankAccountEndpoint.DummyBankAccountEndpoint;
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
    public void createUser_binderCalled()
    {
        // SETUP
        DummyUserEndpoint endpoint = mock(DummyUserEndpoint.class);
        when(bankingApiBinder.getUserEndpoint())
                .thenReturn(endpoint);
        bankingApiFacade.setBinder(bankingApiBinder);

        // ACT
        bankingApiFacade.createUser("test");

        // ASSERT
        Mockito.verify(bankingApiBinder, times(1))
                .getUserEndpoint();
    }

    @Test
    public void getAccesses_binderCalled()
    {
        // SETUP
        DummyBankAccessEndpoint endpoint = mock(DummyBankAccessEndpoint.class);
        when(bankingApiBinder.getBankAccessEndpoint())
                .thenReturn(endpoint);
        bankingApiFacade.setBinder(bankingApiBinder);

        // ACT
        bankingApiFacade.getBankAccesses("test");

        // ASSERT
        Mockito.verify(bankingApiBinder, times(1))
                .getBankAccessEndpoint();
    }

    @Test
    public void getAccounts_binderCalled()
    {
        // SETUP
        DummyBankAccountEndpoint endpoint = mock(DummyBankAccountEndpoint.class);
        when(bankingApiBinder.getBankAccountEndpoint())
                .thenReturn(endpoint);
        bankingApiFacade.setBinder(bankingApiBinder);

        // ACT
        bankingApiFacade.getBankAccounts("test", "test");

        // ASSERT
        Mockito.verify(bankingApiBinder, times(1))
                .getBankAccountEndpoint();
    }
}
