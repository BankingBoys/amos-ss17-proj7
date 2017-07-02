package de.fau.amos.virtualledger.server.factories;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fau.amos.virtualledger.server.model.DeletedBankAccess;

/**
 * Created by Georg on 21.06.2017.
 */
public class DeletedBankAccessFactoryTest {

    DeletedBankAccessFactory deletedBankAccessFactory;

    @Before
    public void setup() {
        deletedBankAccessFactory = new DeletedBankAccessFactory();
    }

    @Test
    public void create_successful() {
        // SETUP
        String testEmail = "email";
        String accessId = "accessId";

        // ACT
        DeletedBankAccess deletedBankAccess = deletedBankAccessFactory.createDeletedBankAccess(testEmail, accessId);

        // ASSERT
        Assert.assertNotNull(deletedBankAccess);
        Assert.assertEquals(testEmail, deletedBankAccess.getUserEmail());
        Assert.assertEquals(accessId, deletedBankAccess.getBankAccessId());
    }
}
