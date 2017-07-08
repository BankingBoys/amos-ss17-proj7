package de.fau.amos.virtualledger.server.banking.adorsys.api;

import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.BankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.HttpBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.BankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.DummyBankAccountEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint.HttpBankAccountEndpoint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankingApiBinderTest {

    @Mock
    private BankingApiConfiguration bankingApiConfiguration;

    @Before
    public void setUp() {
        when(bankingApiConfiguration.getTestUserName()).thenReturn("test@user.de");
    }


    @Test
    public void getBankAccountEndpointDummy() {
        // SETUP
        String username = "test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, null, null,
                new HttpBankAccountEndpoint(), new DummyBankAccountEndpoint(null));

        // EXECUTE
        BankAccountEndpoint endpoint = bankingApiBinder.getBankAccountEndpoint(username);

        // ASSERT
        Assert.assertNotNull(endpoint);
        Assert.assertTrue(endpoint instanceof DummyBankAccountEndpoint);
        verify(bankingApiConfiguration, times(1)).getTestUserName();
    }

    @Test
    public void getBankAccountEndpointHttp() {
        // SETUP
        String username = "!test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, null, null,
                new HttpBankAccountEndpoint(), new DummyBankAccountEndpoint(null));

        // EXECUTE
        BankAccountEndpoint endpoint = bankingApiBinder.getBankAccountEndpoint(username);

        // ASSERT
        Assert.assertNotNull(endpoint);
        Assert.assertTrue(endpoint instanceof HttpBankAccountEndpoint);
        verify(bankingApiConfiguration, times(1)).getTestUserName();
    }

    @Test
    public void getBankAccessEndpointDummy() {
        // SETUP
        String username = "test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration, new HttpBankAccessEndpoint(null, null), new DummyBankAccessEndpoint(), null, null);

        // EXECUTE
        BankAccessEndpoint endpoint = bankingApiBinder.getBankAccessEndpoint(username);

        // ASSERT
        Assert.assertNotNull(endpoint);
        Assert.assertTrue(endpoint instanceof DummyBankAccessEndpoint);
        verify(bankingApiConfiguration, times(1)).getTestUserName();
    }

    @Test
    public void getBankAccessEndpointHttp() {
        // SETUP
        String username = "!test@user.de";
        BankingApiBinder bankingApiBinder = new BankingApiBinder(this.bankingApiConfiguration,
                new HttpBankAccessEndpoint(null, null), new DummyBankAccessEndpoint(), null, null);

        // EXECUTE
        BankAccessEndpoint endpoint = bankingApiBinder.getBankAccessEndpoint(username);

        // ASSERT
        Assert.assertNotNull(endpoint);
        Assert.assertTrue(endpoint instanceof HttpBankAccessEndpoint);
        verify(bankingApiConfiguration, times(1)).getTestUserName();
    }
}
