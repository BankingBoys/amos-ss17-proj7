package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccessCredential;
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
        bankAccessBankingModelFactory.createBankAccessBankingModel("1", credential);
        Assert.assertNotNull(bankAccessBankingModelFactory);
    }
}
