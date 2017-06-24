package de.fau.amos.virtualledger.server.banking.model;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Simon on 24.06.2017.
 */
public class BankAccountBankingModelTest {

    BankAccountBankingModel testModel;

    @Before
    public void setUp() {
        testModel = new BankAccountBankingModel();
    }

    @Test
    public void setAndGetBankAccessId() {
        String testString = "testId";
        testModel.setBankAccessId(testString);
        Assertions.assertThat(testModel.getBankAccessId()).isEqualTo(testString);
    }
}
