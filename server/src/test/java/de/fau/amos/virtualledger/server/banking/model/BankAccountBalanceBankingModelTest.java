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
}
