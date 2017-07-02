package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.server.model.DeletedBankAccount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Georg on 21.06.2017.
 */
public class DeletedBankAccountFactoryTest {

    DeletedBankAccountFactory deletedBankAccountFactory;

    @Before
    public void setup() {
        deletedBankAccountFactory = new DeletedBankAccountFactory();
    }

    @Test
    public void create_successful() {
        // SETUP
        String testEmail = "email";
        String accountId = "accountId";
        String accessId = "accessId";

        // ACT
        DeletedBankAccount deletedBankAccount = deletedBankAccountFactory.createDeletedBankAccount(testEmail, accessId, accountId);

        // ASSERT
        Assert.assertNotNull(deletedBankAccount);
        Assert.assertEquals(testEmail, deletedBankAccount.getUserEmail());
        Assert.assertEquals(accountId, deletedBankAccount.getBankAccountId());
        Assert.assertEquals(accessId, deletedBankAccount.getBankAccessId());
    }
}
