package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint.DummyBankAccessEndpoint;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Georg on 27.06.2017.
 */
public class DummyBankAccountEndpointTest {
    @Test
    public void getBankAccounts_accountExists() throws Exception {
        // SETUP
        String testUserId = "userId";
        DummyBankAccessEndpoint dummyBankAccessEndpoint = new DummyBankAccessEndpoint();
        dummyBankAccessEndpoint.addBankAccess(testUserId, new BankAccessBankingModel());
        BankAccessBankingModel bankAccessBankingModel = dummyBankAccessEndpoint.getBankAccesses(testUserId).get(0);

        DummyBankAccountEndpoint dummyBankAccountEndpoint = new DummyBankAccountEndpoint(dummyBankAccessEndpoint);

        // ACT
        List<BankAccountBankingModel> bankAccounts = dummyBankAccountEndpoint.getBankAccounts(testUserId, bankAccessBankingModel.getId());

        // ASSERT
        assertNotNull(bankAccounts);
        assertTrue(bankAccounts.size() >= 1);

        for(BankAccountBankingModel bankAccountBankingModel : bankAccounts) {
            assertEquals(bankAccountBankingModel.getBankAccessId(), bankAccessBankingModel.getId());
        }
    }

    @Test
    public void getBankAccounts_accountExistsNot() throws Exception {
        // SETUP
        String testUserId = "userId";
        DummyBankAccessEndpoint dummyBankAccessEndpoint = new DummyBankAccessEndpoint();
        String accessId = "access";

        DummyBankAccountEndpoint dummyBankAccountEndpoint = new DummyBankAccountEndpoint(dummyBankAccessEndpoint);

        // ACT
        List<BankAccountBankingModel> bankAccounts = dummyBankAccountEndpoint.getBankAccounts(testUserId, accessId);

        // ASSERT
        assertNotNull(bankAccounts);
        assertTrue(bankAccounts.size() == 0);
    }

    @Test
    public void syncBankAccount_accountExistsNot() throws Exception {
        // SETUP
        String testUserId = "userId";
        DummyBankAccessEndpoint dummyBankAccessEndpoint = new DummyBankAccessEndpoint();
        String accessId = "access";

        DummyBankAccountEndpoint dummyBankAccountEndpoint = new DummyBankAccountEndpoint(dummyBankAccessEndpoint);

        // ACT
        List<BookingModel> bookingModelList = dummyBankAccountEndpoint.syncBankAccount(testUserId, accessId, "dummy", "dummy");

        // ASSERT
        assertNotNull(bookingModelList);
        assertTrue(bookingModelList.size() == 0);
    }

    @Test
    public void syncBankAccounts_accountExists() throws Exception {
        // SETUP
        String testUserId = "userId";
        DummyBankAccessEndpoint dummyBankAccessEndpoint = new DummyBankAccessEndpoint();
        dummyBankAccessEndpoint.addBankAccess(testUserId, new BankAccessBankingModel());
        BankAccessBankingModel bankAccessBankingModel = dummyBankAccessEndpoint.getBankAccesses(testUserId).get(0);

        DummyBankAccountEndpoint dummyBankAccountEndpoint = new DummyBankAccountEndpoint(dummyBankAccessEndpoint);
        List<BankAccountBankingModel> bankAccounts = dummyBankAccountEndpoint.getBankAccounts(testUserId, bankAccessBankingModel.getId());
        assertTrue(bankAccounts.size() >= 1);

        // ACT
        List<BookingModel> bookingModelList = dummyBankAccountEndpoint.syncBankAccount(testUserId, bankAccessBankingModel.getId(), bankAccounts.get(0).getId(), "dummy");

        // ASSERT
        assertNotNull(bookingModelList);
        assertTrue(bookingModelList.size() >= 1);
    }
}