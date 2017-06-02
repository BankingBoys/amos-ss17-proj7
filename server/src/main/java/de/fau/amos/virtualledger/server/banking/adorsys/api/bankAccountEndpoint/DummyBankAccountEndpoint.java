package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccountEndpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiDummy;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBalanceBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.banking.model.BookingModel;

/**
 * Created by Georg on 18.05.2017.
 */
@ApplicationScoped
@BankingApiDummy
public class DummyBankAccountEndpoint implements BankAccountEndpoint {

    Map<String, List<BankAccountBankingModel>> bankAccountMap = new HashMap<String, List<BankAccountBankingModel>>();
    int number = 0;


    @Override
    public List<BankAccountBankingModel> getBankAccounts(String userId, String bankingAccessId) throws BankingException {

        if(!bankAccountMap.containsKey(bankingAccessId))
        {
            this.generateDummyBankAccountModels(bankingAccessId);
        }
        return bankAccountMap.get(bankingAccessId);
    }

    @Override
    public List<BookingModel> syncBankAccount(String userId, String bankAccessId, String bankAccountId, String pin) throws BankingException {
        // nothing to do here yet (maybe TODO generate new transactions on sync??)
        return null;
    }


    /**
     *
     * generates a BankAccountBankingModel with dummy data
     * @param bankingAccessId
     * @return
     */
    private BankAccountBankingModel generateDummyBankAccountModel(String bankingAccessId)
    {
        BankAccountBankingModel bankAccountBankingModel = new BankAccountBankingModel();
        BankAccountBalanceBankingModel bankAccountBalanceBankingModel = new BankAccountBalanceBankingModel();
        bankAccountBalanceBankingModel.setAvailableHbciBalance(500.00);
        bankAccountBalanceBankingModel.setReadyHbciBalance(500.00);

        String id_postfix = number++ + "_" + System.nanoTime();

        bankAccountBankingModel.setBankAccountBalance(bankAccountBalanceBankingModel);
        bankAccountBankingModel.setCountryHbciAccount("DE");
        bankAccountBankingModel.setBlzHbciAccount("TestBLZ");
        bankAccountBankingModel.setNumberHbciAccount("TestHbciAccountNummer " + id_postfix);
        bankAccountBankingModel.setTypeHbciAccount("TestKonto " + id_postfix);
        bankAccountBankingModel.setCurrencyHbciAccount("EUR");
        bankAccountBankingModel.setNameHbciAccount("TestUser");
        bankAccountBankingModel.setBicHbciAccount("TestBIC");
        bankAccountBankingModel.setIbanHbciAccount("TestIBAN");
        bankAccountBankingModel.setId("TestID" + id_postfix);
        bankAccountBankingModel.setBankAccessId(bankingAccessId);

        return bankAccountBankingModel;
    }

    /**
     *
     * generates a few BankAccountBankingModel and inserts them into bankAccountMap
     * @param bankingAccessId
     * @return
     */
    private void generateDummyBankAccountModels(String bankingAccessId)
    {
        List<BankAccountBankingModel> bankAccountBankingModelList = new ArrayList<BankAccountBankingModel>();
        for(int i = 0; i < 5; ++i)
        {
            BankAccountBankingModel bankAccountBankingModel = this.generateDummyBankAccountModel(bankingAccessId);
            bankAccountBankingModelList.add(bankAccountBankingModel);
        }

        this.bankAccountMap.put(bankingAccessId, bankAccountBankingModelList);
    }
}
