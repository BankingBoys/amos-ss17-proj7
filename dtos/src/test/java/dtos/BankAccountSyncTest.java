package dtos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fau.amos.virtualledger.dtos.BankAccountSync;

/**
 * Created by Simon on 12.06.2017.
 */

public class BankAccountSyncTest {

    private static final String BANKACCESSID = "TestAccessId";
    private static final String BANKACCOUNTID = "TestAccountID";
    private static final String PIN = "TestPin";
    private BankAccountSync syncTest;

    /**
     *
     */
    @Before
    public void setUp() {
        syncTest = new BankAccountSync(BANKACCESSID, BANKACCOUNTID, PIN);
    }

    /**
     *
     */
    @Test
    public void constructorTest() {
        BankAccountSync syncTest2 = new BankAccountSync(BANKACCESSID, BANKACCOUNTID, PIN);
        Assert.assertNotNull(syncTest2);
    }

    /**
     *
     */
    @Test
    public void setAndGetBankAccessId() {
        String newBankAccessId = "newBankAccessId";
        syncTest.setBankaccessid(newBankAccessId);
        Assert.assertEquals(newBankAccessId, syncTest.getBankaccessid());
    }

    /**
     *
     */
    @Test
    public void setAndGetBankAccountId() {
        String newBankAccountId = "newBankAccountId";
        syncTest.setBankaccountid(newBankAccountId);
        Assert.assertEquals(newBankAccountId, syncTest.getBankaccountid());
    }

    /**
     *
     */
    @Test
    public void setAndGetPin() {
        String newPin = "newPin";
        syncTest.setPin(newPin);
        Assert.assertEquals(newPin, syncTest.getPin());
    }
}
