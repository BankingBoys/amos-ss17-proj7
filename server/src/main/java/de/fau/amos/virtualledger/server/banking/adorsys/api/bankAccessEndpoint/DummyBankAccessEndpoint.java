package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiDummy;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georg on 18.05.2017.
 */
@ApplicationScoped
@BankingApiDummy
public class DummyBankAccessEndpoint implements BankAccessEndpoint {

    protected List<BankAccessBankingModel> bankingModels = new ArrayList<BankAccessBankingModel>();
    private int number = 0;

    @Override
    public List<BankAccessBankingModel> getBankAccesses(String userId) throws BankingException {
        return bankingModels;
    }

    @Override
    public void addBankAccess(String userId, BankAccessBankingModel bankAccess)  throws BankingException {

        BankAccessBankingModel bankAccessBankingModel = new BankAccessBankingModel();
        bankAccessBankingModel.setId("TestID" + number + "_" + System.nanoTime());
        bankAccessBankingModel.setBankLogin(bankAccess.getBankLogin());
        bankAccessBankingModel.setBankCode(bankAccess.getBankCode());
        bankAccessBankingModel.setUserId(userId);
        bankAccessBankingModel.setBankName("TestBank" + number);
        bankAccessBankingModel.setPin(null);
        bankAccessBankingModel.setPassportState("testPassportState");

        this.bankingModels.add(bankAccessBankingModel);
        number ++;
    }


    public boolean existsBankAccess(String bankAccessId)
    {
        boolean result = false;

        for(BankAccessBankingModel bankAccessBankingModel: this.bankingModels)
        {
            if(bankAccessBankingModel.getId().equals(bankAccessId))
            {
                result = true;
                break;
            }
        }
        return result;
    }
}
