package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBalanceBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BankAccessFactoryTest {

    BankAccessFactory bankAccessFactory;

    @Before
    public void setup() { bankAccessFactory = new BankAccessFactory(); }

    @Test
    public void create_success() {
        // SETUP
        String accessId = "accessID";
        String bankName = "bankName";
        String bankCode = "bankCode";
        String bankLogin = "bankLogin";
        BankAccessBankingModel bankAccessBankingModel = new BankAccessBankingModel();
        bankAccessBankingModel.setBankName(bankName);
        bankAccessBankingModel.setBankCode(bankCode);
        bankAccessBankingModel.setBankLogin(bankLogin);
        bankAccessBankingModel.setId(accessId);

        // ACT
        BankAccess bankAccess = bankAccessFactory.createBankAccess(bankAccessBankingModel);

        // ASSERT
        Assert.assertNotNull(bankAccess);
        Assert.assertEquals(accessId, bankAccess.getId());
        Assert.assertEquals(bankCode, bankAccess.getBankcode());
        Assert.assertEquals(bankLogin, bankAccess.getBanklogin());
        Assert.assertEquals(bankName, bankAccess.getName());
    }

    @Test
    public void createList_success() {
        // SETUP
        String accessId = "accessID";
        String bankName = "bankName";
        String bankCode = "bankCode";
        String bankLogin = "bankLogin";
        BankAccessBankingModel bankAccessBankingModel = new BankAccessBankingModel();
        bankAccessBankingModel.setBankName(bankName);
        bankAccessBankingModel.setBankCode(bankCode);
        bankAccessBankingModel.setBankLogin(bankLogin);
        bankAccessBankingModel.setId(accessId);

        List<BankAccessBankingModel> bankAccessBankingModelList = new ArrayList<>();
        bankAccessBankingModelList.add(bankAccessBankingModel);

        // ACT
        List<BankAccess> bankAccesses = bankAccessFactory.createBankAccesses(bankAccessBankingModelList);

        // ASSERT
        Assert.assertNotNull(bankAccesses);
        Assert.assertEquals(bankAccesses.size(), 1);
        Assert.assertEquals(accessId, bankAccesses.get(0).getId());
        Assert.assertEquals(bankCode, bankAccesses.get(0).getBankcode());
        Assert.assertEquals(bankLogin, bankAccesses.get(0).getBanklogin());
        Assert.assertEquals(bankName, bankAccesses.get(0).getName());
    }
}
