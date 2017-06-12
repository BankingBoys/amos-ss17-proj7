package dtos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fau.amos.virtualledger.dtos.BankAccountSync;

/**
 * Created by Simon on 12.06.2017.
 */

public class BankAccountSyncTest {

    private String bankaccessid = "TestAccessId";
    private String bankaccountid = "TestAccountID";
    private String pin = "TestPin";
    BankAccountSync syncTest;

    /**
     *
     */
    @Before
    public void setUp() {
        syncTest = new BankAccountSync(bankaccessid, bankaccountid, pin);
    }

    /**
     *
     */
    @Test
    public void constructorTest() {
        BankAccountSync syncTest2 = new BankAccountSync(bankaccessid, bankaccountid, pin);
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
