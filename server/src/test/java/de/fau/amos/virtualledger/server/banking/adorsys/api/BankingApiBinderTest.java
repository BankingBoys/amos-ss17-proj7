package de.fau.amos.virtualledger.server.banking.adorsys.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.persistence.jpa.jpql.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.BankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.HttpBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.BankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.DummyBankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.HttpBankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.DummyUserEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.HttpUserEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.userEndpoint.UserEndpoint;

@RunWith(MockitoJUnitRunner.class)
public class BankingApiBinderTest {

    @Mock
    private BankingApiConfiguration bankingApiConfiguration;

    @Before
    public void setUp() {
        when(bankingApiConfiguration.getTestUserName()).thenReturn("test@user.de");
    }

    @Test
    public void getUserEndpointDummy() {
        // SETUP
        String username = "test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, new HttpUserEndpoint(),
                new DummyUserEndpoint(), null, null, null, null);

        // EXECUTE
        UserEndpoint endpoint = bankingApiBinder.getUserEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof DummyUserEndpoint, "endpoint was not a dummy endpoint!");
        verify(bankingApiConfiguration, times(1)).getTestUserName();
    }

    @Test
    public void getUserEndpointHttp() {
        // SETUP
        String username = "!test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, new HttpUserEndpoint(),
                new DummyUserEndpoint(), null, null, null, null);

        // EXECUTE
        UserEndpoint endpoint = bankingApiBinder.getUserEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof HttpUserEndpoint, "endpoint was not a http endpoint!");
        verify(bankingApiConfiguration, times(1)).getTestUserName();
    }

    @Test
    public void getBankAccountEndpointDummy() {
        // SETUP
        String username = "test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, null, null, null, null,
                new HttpBankAccountEndpoint(), new DummyBankAccountEndpoint(null));

        // EXECUTE
        BankAccountEndpoint endpoint = bankingApiBinder.getBankAccountEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof DummyBankAccountEndpoint, "endpoint was not a dummy endpoint!");
        verify(bankingApiConfiguration, times(1)).getTestUserName();
    }

    @Test
    public void getBankAccountEndpointHttp() {
        // SETUP
        String username = "!test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, null, null, null, null,
                new HttpBankAccountEndpoint(), new DummyBankAccountEndpoint(null));

        // EXECUTE
        BankAccountEndpoint endpoint = bankingApiBinder.getBankAccountEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof HttpBankAccountEndpoint, "endpoint was not a http endpoint!");
        verify(bankingApiConfiguration, times(1)).getTestUserName();
    }

    @Test
    public void getBankAccessEndpointDummy() {
        // SETUP
        String username = "test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, null, null,
                new HttpBankAccessEndpoint(), new DummyBankAccessEndpoint(), null, null);

        // EXECUTE
        BankAccessEndpoint endpoint = bankingApiBinder.getBankAccessEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof DummyBankAccessEndpoint, "endpoint was not a dummy endpoint!");
        verify(bankingApiConfiguration, times(1)).getTestUserName();
    }

    @Test
    public void getBankAccessEndpointHttp() {
        // SETUP
        String username = "!test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, null, null,
                new HttpBankAccessEndpoint(), new DummyBankAccessEndpoint(), null, null);

        // EXECUTE
        BankAccessEndpoint endpoint = bankingApiBinder.getBankAccessEndpoint(username);

        // ASSERT
        Assert.isNotNull(endpoint, "endpoint was null!");
        Assert.isTrue(endpoint instanceof HttpBankAccessEndpoint, "endpoint was not a http endpoint!");
        verify(bankingApiConfiguration, times(1)).getTestUserName();
    }
}
