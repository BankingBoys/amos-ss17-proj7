package de.fau.amos.virtualledger.server.banking.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.api.BankingApiDummy;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;

import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georg on 18.05.2017.
 */
@RequestScoped @BankingApiDummy
public class DummyBankAccessEndpoint implements BankAccessEndpoint {


    @Override
    public List<BankAccessBankingModel> getBankAccesses(String userId) {
        List<BankAccessBankingModel> dummyData = new ArrayList<BankAccessBankingModel>();
        dummyData.add(this.generateDummyBankAccessModel(1));
        dummyData.add(this.generateDummyBankAccessModel(2));
        return dummyData;
    }

    /**
     * generates a BankAccessBankingModel with dummy data
     * @param id that makes multiple dummies different via a postfix in every attribute
     * @return
     */
    private BankAccessBankingModel generateDummyBankAccessModel(int id)
    {
        BankAccessBankingModel dummyModel = new BankAccessBankingModel();
        dummyModel.setBankName("Dummy Bank " + id);
        dummyModel.setBankLogin("Dummy login " + id);
        dummyModel.setBankCode("Dummy code " + id);
        dummyModel.setPassportState("Dummy passport state " + id);
        dummyModel.setId("Dummy1 " + id);
        dummyModel.setUserId("DummyUser " + id);
        dummyModel.setPin(null);
        return dummyModel;
    }

}
