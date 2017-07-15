package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessBankingModelEntity;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpointRepository;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DummyBankAccountEndpointTest {

    private final int amountAccountsToGenerate = 5;

    @Test
    public void getBankAccountsAccountsExist() throws Exception {

        // SETUP
        String testUserId = "userId";
        DummyBankAccessEndpoint dummyBankAccessEndpoint = mock(DummyBankAccessEndpoint.class);
        DummyBankAccountBankingModelRepository dummyBankAccountBankingModelRepository = mock(DummyBankAccountBankingModelRepository.class);
        DummyBankAccessEndpointRepository dummyBankAccessEndpointRepository = mock(DummyBankAccessEndpointRepository.class);

        when(dummyBankAccessEndpoint.existsBankAccess(anyString())).thenReturn(true);
        when(dummyBankAccountBankingModelRepository.existBankAccountsForAccessId(anyString())).thenReturn(true);
        when(dummyBankAccountBankingModelRepository.findAllByAccessId(anyString())).thenReturn(generateBankingAccounts());

        DummyBankAccountEndpoint dummyBankAccountEndpoint = new DummyBankAccountEndpoint(dummyBankAccountBankingModelRepository, dummyBankAccessEndpointRepository, dummyBankAccessEndpoint);

        // ACT
        List<BankAccountBankingModel> bankAccounts = dummyBankAccountEndpoint.getBankAccounts(testUserId,
                "bankAccessId");

        // ASSERT
        assertNotNull(bankAccounts);
        assertTrue(bankAccounts.size() == amountAccountsToGenerate);
        verify(dummyBankAccountBankingModelRepository, times(1)).findAllByAccessId(anyString());
    }

    @Test
    public void getBankAccountsAccountsNotExist() throws Exception {

        // SETUP
        String testUserId = "userId";
        DummyBankAccessEndpoint dummyBankAccessEndpoint = mock(DummyBankAccessEndpoint.class);
        DummyBankAccountBankingModelRepository dummyBankAccountBankingModelRepository = mock(DummyBankAccountBankingModelRepository.class);
        DummyBankAccessEndpointRepository dummyBankAccessEndpointRepository = mock(DummyBankAccessEndpointRepository.class);

        when(dummyBankAccessEndpoint.existsBankAccess(anyString())).thenReturn(true);
        when(dummyBankAccountBankingModelRepository.existBankAccountsForAccessId(anyString())).thenReturn(false);

        DummyBankAccountEndpoint dummyBankAccountEndpoint = new DummyBankAccountEndpoint(dummyBankAccountBankingModelRepository, dummyBankAccessEndpointRepository, dummyBankAccessEndpoint);

        // ACT
        List<BankAccountBankingModel> bankAccounts = dummyBankAccountEndpoint.getBankAccounts(testUserId,
                "bankAccessId");

        // ASSERT
        assertNotNull(bankAccounts);
        assertTrue(bankAccounts.size() >= 0);
        verify(dummyBankAccountBankingModelRepository, times(1)).findAllByAccessId(anyString());
        verify(dummyBankAccountBankingModelRepository, atLeastOnce()).save(any(List.class));
    }

    @Test
    public void syncBankAccountsAccountsNotExist() throws Exception {

        // SETUP
        String testUserId = "userId";
        DummyBankAccessEndpoint dummyBankAccessEndpoint = mock(DummyBankAccessEndpoint.class);
        DummyBankAccountBankingModelRepository dummyBankAccountBankingModelRepository = mock(DummyBankAccountBankingModelRepository.class);
        DummyBankAccessEndpointRepository dummyBankAccessEndpointRepository = mock(DummyBankAccessEndpointRepository.class);

        DummyBankAccountEndpoint dummyBankAccountEndpoint = new DummyBankAccountEndpoint(dummyBankAccountBankingModelRepository, dummyBankAccessEndpointRepository, dummyBankAccessEndpoint);

        // ACT
        List<BookingModel> bookings = dummyBankAccountEndpoint.syncBankAccount(testUserId,
                "bankAccessId", "bankAccountId", "pin");

        // ASSERT
        assertNotNull(bookings);
        assertTrue(bookings.size() >= 0);
    }


    private List<DummyBankAccountBankingModelEntity> generateBankingAccounts() {
        List<DummyBankAccountBankingModelEntity> dummyBankAccountBankingModelEntityList = new ArrayList<>();

        for (int i = 0; i < amountAccountsToGenerate; i++) {
            DummyBankAccountBankingModelEntity accountEntity = new DummyBankAccountBankingModelEntity();

            DummyBankAccessBankingModelEntity accessEntity = new DummyBankAccessBankingModelEntity();
            accessEntity.setId("id");
            accountEntity.setBankAccess(accessEntity);

            dummyBankAccountBankingModelEntityList.add(accountEntity);
        }
        return dummyBankAccountBankingModelEntityList;
    }

}
