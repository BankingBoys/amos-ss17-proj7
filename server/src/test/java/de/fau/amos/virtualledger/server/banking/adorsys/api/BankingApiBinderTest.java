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

    @Mock
    BankingApiConfiguration bankingApiConfiguration;

    @Before
    public void setUp()
    {
        when(bankingApiConfiguration.getTestUserName())
                .thenReturn("test@user.de");
    }


    @Test
    public void getUserEndpoint_dummy()
    {
        // SETUP
        String username = "test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, new HttpUserEndpoint(), new DummyUserEndpoint(), null, null, null, null);

        // EXECUTE
        UserEndpoint endpoint = bankingApiBinder.getUserEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof DummyUserEndpoint, "endpoint was not a dummy endpoint!");
        verify(bankingApiConfiguration, times(1))
                .getTestUserName();
    }


    @Test
    public void getUserEndpoint_http()
    {
        // SETUP
        String username = "!test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, new HttpUserEndpoint(), new DummyUserEndpoint(), null, null, null, null);

        // EXECUTE
        UserEndpoint endpoint = bankingApiBinder.getUserEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof HttpUserEndpoint, "endpoint was not a http endpoint!");
        verify(bankingApiConfiguration, times(1))
                .getTestUserName();
    }

    @Test
    public void getBankAccountEndpoint_dummy()
    {
        // SETUP
        String username = "test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, null, null, null, null, new HttpBankAccountEndpoint(), new DummyBankAccountEndpoint());

        // EXECUTE
        BankAccountEndpoint endpoint = bankingApiBinder.getBankAccountEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof DummyBankAccountEndpoint, "endpoint was not a dummy endpoint!");
        verify(bankingApiConfiguration, times(1))
                .getTestUserName();
    }


    @Test
    public void getBankAccountEndpoint_http()
    {
        // SETUP
        String username = "!test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, null, null, null, null, new HttpBankAccountEndpoint(), new DummyBankAccountEndpoint());

        // EXECUTE
        BankAccountEndpoint endpoint = bankingApiBinder.getBankAccountEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof HttpBankAccountEndpoint, "endpoint was not a http endpoint!");
        verify(bankingApiConfiguration, times(1))
                .getTestUserName();
    }

    @Test
    public void getBankAccessEndpoint_dummy()
    {
        // SETUP
        String username = "test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, null, null, new HttpBankAccessEndpoint(), new DummyBankAccessEndpoint(), null, null);

        // EXECUTE
        BankAccessEndpoint endpoint = bankingApiBinder.getBankAccessEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof DummyBankAccessEndpoint, "endpoint was not a dummy endpoint!");
        verify(bankingApiConfiguration, times(1))
                .getTestUserName();
    }


    @Test
    public void getBankAccessEndpoint_http()
    {
        // SETUP
        String username = "!test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, null, null, new HttpBankAccessEndpoint(), new DummyBankAccessEndpoint(), null, null);

        // EXECUTE
        BankAccessEndpoint endpoint = bankingApiBinder.getBankAccessEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof HttpBankAccessEndpoint, "endpoint was not a http endpoint!");
        verify(bankingApiConfiguration, times(1))
                .getTestUserName();
    }
}
