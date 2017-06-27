package de.fau.amos.virtualledger.server.banking.model;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Simon on 21.06.2017.
 */
public class BankAccessBankingModelTest {

    BankAccessBankingModel model;

    @Before
    public void setUp() {
        model = new BankAccessBankingModel();
    }

    @Test
    public void constructorTest() {
        Assert.assertNotNull(model);
    }

    @Test
    public void setAndGetBankCode() {
        String bankCode = "1";
        model.setBankCode(bankCode);
        Assert.assertEquals(bankCode, model.getBankCode());
    }

    @Test
    public void setAndGetBankName() {
        String bankName = "testName";
        model.setBankName(bankName);
        Assert.assertEquals(bankName, model.getBankName());
    }

    @Test
    public void setAndGetBankLogin() {
        String bankLogin = "12345";
        model.setBankLogin(bankLogin);
        Assert.assertEquals(bankLogin, model.getBankLogin());
    }

    @Test
    public void setAndGetBankPassportState() {
        String passPortState = "testPassportState";
        model.setPassportState(passPortState );
        Assert.assertEquals(passPortState , model.getPassportState());
    }

    @Test
    public void setAndGetId() {
        String testId = "testId";
        model.setId(testId);
        Assert.assertEquals(testId, model.getId());
    }

    @Test
    public void setAndGetUserId() {
        String userId = "userId";
        model.setUserId(userId);
        Assert.assertEquals(userId, model.getUserId());
    }

    @Test
    public void setAndGetPin() {
        String testPin = "testPin";
        model.setPin(testPin);
        Assert.assertEquals(testPin, model.getPin());
    }
}
