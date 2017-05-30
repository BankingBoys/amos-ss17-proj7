package de.fau.amos.virtualledger.server.banking.adorsys.api;

import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.BankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.HttpBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.BankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.DummyBankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.HttpBankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.DummyUserEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.HttpUserEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.UserEndpoint;
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
    public void getUserEndpoint_dummy()
    {
        // SETUP
        when(bankingApiConfiguration.isUseUserEndpointDummy())
                .thenReturn(true);
        bankingApiBinder.setUserEndpoint(new DummyUserEndpoint(), new HttpUserEndpoint(), bankingApiConfiguration);

        // EXECUTE
        UserEndpoint endpoint = bankingApiBinder.getUserEndpoint();

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof DummyUserEndpoint, "endpoint was not a dummy endpoint!");
        verify(bankingApiConfiguration, times(1))
                .isUseUserEndpointDummy();
    }


    @Test
    public void getUserEndpoint_http()
    {
        // SETUP
        when(bankingApiConfiguration.isUseUserEndpointDummy())
                .thenReturn(false);
        bankingApiBinder.setUserEndpoint(new DummyUserEndpoint(), new HttpUserEndpoint(), bankingApiConfiguration);

        // EXECUTE
        UserEndpoint endpoint = bankingApiBinder.getUserEndpoint();

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof HttpUserEndpoint, "endpoint was not a http endpoint!");
        verify(bankingApiConfiguration, times(1))
                .isUseUserEndpointDummy();
    }

    @Test
    public void getBankAccountEndpoint_dummy()
    {
        // SETUP
        when(bankingApiConfiguration.isUseBankAccountEndpointDummy())
                .thenReturn(true);
        bankingApiBinder.setBankAccountEndpoint(new DummyBankAccountEndpoint(), new HttpBankAccountEndpoint(), bankingApiConfiguration);

        // EXECUTE
        BankAccountEndpoint endpoint = bankingApiBinder.getBankAccountEndpoint();

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof DummyBankAccountEndpoint, "endpoint was not a dummy endpoint!");
        verify(bankingApiConfiguration, times(1))
                .isUseBankAccountEndpointDummy();
    }


    @Test
    public void getBankAccountEndpoint_http()
    {
        // SETUP
        when(bankingApiConfiguration.isUseBankAccountEndpointDummy())
                .thenReturn(false);
        bankingApiBinder.setBankAccountEndpoint(new DummyBankAccountEndpoint(), new HttpBankAccountEndpoint(), bankingApiConfiguration);

        // EXECUTE
        BankAccountEndpoint endpoint = bankingApiBinder.getBankAccountEndpoint();

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof HttpBankAccountEndpoint, "endpoint was not a http endpoint!");
        verify(bankingApiConfiguration, times(1))
                .isUseBankAccountEndpointDummy();
    }

    @Test
    public void getBankAccessEndpoint_dummy()
    {
        // SETUP
        when(bankingApiConfiguration.isUseBankAccessEndpointDummy())
                .thenReturn(true);
        bankingApiBinder.setBankAccessEndpoint(new DummyBankAccessEndpoint(), new HttpBankAccessEndpoint(), bankingApiConfiguration);

        // EXECUTE
        BankAccessEndpoint endpoint = bankingApiBinder.getBankAccessEndpoint();

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof DummyBankAccessEndpoint, "endpoint was not a dummy endpoint!");
        verify(bankingApiConfiguration, times(1))
                .isUseBankAccessEndpointDummy();
    }


    @Test
    public void getBankAccessEndpoint_http()
    {
        // SETUP
        when(bankingApiConfiguration.isUseBankAccessEndpointDummy())
                .thenReturn(false);
        bankingApiBinder.setBankAccessEndpoint(new DummyBankAccessEndpoint(), new HttpBankAccessEndpoint(), bankingApiConfiguration);

        // EXECUTE
        BankAccessEndpoint endpoint = bankingApiBinder.getBankAccessEndpoint();

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof HttpBankAccessEndpoint, "endpoint was not a http endpoint!");
        verify(bankingApiConfiguration, times(1))
                .isUseBankAccessEndpointDummy();
    }
}
