package de.fau.amos.virtualledger.server.banking.model;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Simon on 24.06.2017.
 */
public class BankAccountBalanceBankingModelTest {

    BankAccountBalanceBankingModel testModel;

    @Before
    public void setUP() {
        testModel = new BankAccountBalanceBankingModel();
    }

    @Test
    public void constructorTest() {
        Assertions.assertThat(testModel).isNotNull();
    }

    @Test
    public void setAndGetAvailableHbciBalance() {
        double testBalance = 5;
        testModel.setAvailableHbciBalance(testBalance);
        Assertions.assertThat(testModel.getAvailableHbciBalance()).isEqualTo(testBalance);
    }

    @Test
    public void setAndGetReadyHbciBalance() {
        double testBalance = 6;
        testModel.setReadyHbciBalance(testBalance);
        Assertions.assertThat(testModel.getReadyHbciBalance()).isEqualTo(testBalance);
    }

    @Test
    public void setAndGetUnreadyHbciBalance() {
        double testBalance = 7;
        testModel.setUnreadyHbciBalance(testBalance);
        Assertions.assertThat(testModel.getUnreadyHbciBalance()).isEqualTo(testBalance);
    }
}
