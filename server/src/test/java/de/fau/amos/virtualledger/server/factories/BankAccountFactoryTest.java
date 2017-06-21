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

    BankAccountFactory bankAccountFactory;

    @Before
    public void setup() { bankAccountFactory = new BankAccountFactory(); }

    @Test
    public void create_success() {
        // SETUP
        double amount = 123.22;
        BankAccountBalanceBankingModel bankAccountBalanceBankingModel = new BankAccountBalanceBankingModel();
        bankAccountBalanceBankingModel.setReadyHbciBalance(amount);

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
        Assert.assertEquals(amount, bankAccount.getBalance(), 0.01);
    }

    @Test
    public void createList_success() {
        // SETUP
        double amount = 123.22;
        BankAccountBalanceBankingModel bankAccountBalanceBankingModel = new BankAccountBalanceBankingModel();
        bankAccountBalanceBankingModel.setReadyHbciBalance(amount);

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
        Assert.assertEquals(bankAccounts.size(), 1);
        Assert.assertEquals(accountId, bankAccounts.get(0).getBankid());
        Assert.assertEquals(type, bankAccounts.get(0).getName());
        Assert.assertEquals(amount, bankAccounts.get(0).getBalance(), 0.01);
    }
}
