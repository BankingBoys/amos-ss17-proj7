package de.fau.amos.virtualledger.server.banking.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Created by Simon on 24.06.2017.
 */
public class BankAccountBalanceBankingModelTest {

    @Test
    public void constructorTest() {
        BankAccountBalanceBankingModel testModel = new BankAccountBalanceBankingModel();
        Assertions.assertThat(testModel).isNotNull();
    }
    
}
