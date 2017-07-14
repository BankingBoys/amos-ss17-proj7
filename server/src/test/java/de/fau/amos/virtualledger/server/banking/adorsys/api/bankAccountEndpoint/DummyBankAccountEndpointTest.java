package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

public class DummyBankAccountEndpointTest {

    public void getBankAccountsAccountExists() throws Exception {

    }
 /*       // SETUP
        String testUserId = "userId";
        DummyBankAccessEndpoint dummyBankAccessEndpoint = new DummyBankAccessEndpoint();
        dummyBankAccessEndpoint.addBankAccess(testUserId, new BankAccessBankingModel());
        BankAccessBankingModel bankAccessBankingModel = dummyBankAccessEndpoint.getBankAccesses(testUserId).get(0);

        DummyBankAccountEndpoint dummyBankAccountEndpoint = new DummyBankAccountEndpoint(dummyBankAccessEndpoint);

        // ACT
        List<BankAccountBankingModel> bankAccounts = dummyBankAccountEndpoint.getBankAccounts(testUserId,
                bankAccessBankingModel.getId());

        // ASSERT
        assertNotNull(bankAccounts);
        assertTrue(bankAccounts.size() >= 1);

        for (BankAccountBankingModel bankAccountBankingModel : bankAccounts) {
            assertEquals(bankAccountBankingModel.getBankAccessId(), bankAccessBankingModel.getId());
        }
    }

    private void assertEquals(String bankAccessId, String id) {
    }

    public void getBankAccountsAccountExistsNot() throws Exception {
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

    public void syncBankAccountAccountExistsNot() throws Exception {
        // SETUP
        String testUserId = "userId";
        DummyBankAccessEndpoint dummyBankAccessEndpoint = new DummyBankAccessEndpoint();
        String accessId = "access";

        DummyBankAccountEndpoint dummyBankAccountEndpoint = new DummyBankAccountEndpoint(dummyBankAccessEndpoint);

        // ACT
        List<BookingModel> bookingModelList = dummyBankAccountEndpoint.syncBankAccount(testUserId, accessId, "dummy",
                "dummy");

        // ASSERT
        assertNotNull(bookingModelList);
        assertTrue(bookingModelList.size() == 0);
    }

    public void syncBankAccountsAccountExists() throws Exception {
        // SETUP
        String testUserId = "userId";
        DummyBankAccessEndpoint dummyBankAccessEndpoint = new DummyBankAccessEndpoint();
        dummyBankAccessEndpoint.addBankAccess(testUserId, new BankAccessBankingModel());
        BankAccessBankingModel bankAccessBankingModel = dummyBankAccessEndpoint.getBankAccesses(testUserId).get(0);

        DummyBankAccountEndpoint dummyBankAccountEndpoint = new DummyBankAccountEndpoint(dummyBankAccessEndpoint);
        List<BankAccountBankingModel> bankAccounts = dummyBankAccountEndpoint.getBankAccounts(testUserId,
                bankAccessBankingModel.getId());
        assertTrue(bankAccounts.size() >= 1);

        // ACT
        List<BookingModel> bookingModelList = dummyBankAccountEndpoint.syncBankAccount(testUserId,
                bankAccessBankingModel.getId(), bankAccounts.get(0).getId(), "dummy");

        // ASSERT
        assertNotNull(bookingModelList);
        assertTrue(bookingModelList.size() >= 1);
    }*/
}
