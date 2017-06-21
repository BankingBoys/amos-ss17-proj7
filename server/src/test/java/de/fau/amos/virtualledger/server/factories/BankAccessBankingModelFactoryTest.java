package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Simon on 21.06.2017.
 */
public class BankAccessBankingModelFactoryTest {

    @Test
    public void createTest () {
        BankAccessBankingModelFactory bankAccessBankingModelFactory = new BankAccessBankingModelFactory();
        BankAccessCredential credential = new BankAccessCredential("test", "test", "test");
        BankAccessBankingModel testModel = bankAccessBankingModelFactory.createBankAccessBankingModel("1", credential);
        Assert.assertNotNull(testModel);
    }
}
