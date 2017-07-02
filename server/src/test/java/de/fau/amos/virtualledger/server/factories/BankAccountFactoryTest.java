package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBalanceBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georg on 21.06.2017.
 */
public class BankAccountFactoryTest {

    private BankAccountFactory bankAccountFactory;
    private static final double DELTA = 0.01;

    @Before
    public void setup() {
        bankAccountFactory = new BankAccountFactory();
    }

    @Test
    public void createSuccess() {
        // SETUP
        final double testAmount = 123.22;
        BankAccountBalanceBankingModel bankAccountBalanceBankingModel = new BankAccountBalanceBankingModel();
        bankAccountBalanceBankingModel.setReadyHbciBalance(testAmount);

        String accessId = "accessID";
        String accountId = "accountId";
        String type = "type";
        BankAccountBankingModel bankAccountBankingModel = new BankAccountBankingModel();
        bankAccountBankingModel.setBankAccessId(accessId);
        bankAccountBankingModel.setId(accountId);
        bankAccountBankingModel.setTypeHbciAccount(type);
        bankAccountBankingModel.setBankAccountBalance(bankAccountBalanceBankingModel);

        // ACT
        BankAccount bankAccount = bankAccountFactory.createBankAccount(bankAccountBankingModel);

        // ASSERT
        Assert.assertNotNull(bankAccount);
        Assert.assertEquals(accountId, bankAccount.getBankid());
        Assert.assertEquals(type, bankAccount.getName());
        Assert.assertEquals(testAmount, bankAccount.getBalance(), DELTA);
    }

    @Test
    public void createListSuccess() {
        // SETUP
        final double testAmount = 123.22;
        final int bankAccountActualSize = 1;
        BankAccountBalanceBankingModel bankAccountBalanceBankingModel = new BankAccountBalanceBankingModel();
        bankAccountBalanceBankingModel.setReadyHbciBalance(testAmount);

        String accessId = "accessID";
        String accountId = "accountId";
        String type = "type";
        BankAccountBankingModel bankAccountBankingModel = new BankAccountBankingModel();
        bankAccountBankingModel.setBankAccessId(accessId);
        bankAccountBankingModel.setId(accountId);
        bankAccountBankingModel.setTypeHbciAccount(type);
        bankAccountBankingModel.setBankAccountBalance(bankAccountBalanceBankingModel);

        List<BankAccountBankingModel> bankAccountBankingModelList = new ArrayList<>();
        bankAccountBankingModelList.add(bankAccountBankingModel);

        // ACT
        List<BankAccount> bankAccounts = bankAccountFactory.createBankAccounts(bankAccountBankingModelList);

        // ASSERT
        Assert.assertNotNull(bankAccounts);
        Assert.assertEquals(bankAccounts.size(), bankAccountActualSize);
        Assert.assertEquals(accountId, bankAccounts.get(0).getBankid());
        Assert.assertEquals(type, bankAccounts.get(0).getName());
        Assert.assertEquals(testAmount, bankAccounts.get(0).getBalance(), DELTA);
    }
}
