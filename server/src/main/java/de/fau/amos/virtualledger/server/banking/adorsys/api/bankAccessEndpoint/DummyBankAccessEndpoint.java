package de.fau.amos.virtualledger.server.banking.adorsys.api.bankAccessEndpoint;

import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that manages dummy data, but only for the test user.
 * So it isn't distinguished which user sends the request.
 * Make sure only the test user accesses this class.
 */
@Component
@Qualifier("dummy")
public class DummyBankAccessEndpoint implements BankAccessEndpoint {

    @Inject
    private DummyBankAccessEndpointRepository bankAccessEndpointRepository;

    private List<BankAccessBankingModel> bankingModels = new ArrayList<BankAccessBankingModel>();
    private int number = 0;

    public DummyBankAccessEndpoint(DummyBankAccessEndpointRepository bankAccessEndpointRepository) {
        this.bankAccessEndpointRepository = bankAccessEndpointRepository;
    }

    public DummyBankAccessEndpoint() {
    }

    @Override
    public List<BankAccessBankingModel> getBankAccesses(String userId) throws BankingException {
        return bankAccessEndpointRepository.findAll();
    }

    @Override
    public void addBankAccess(String userId, BankAccessBankingModel bankAccess) throws BankingException {

        BankAccessBankingModel bankAccessBankingModel = new BankAccessBankingModel();
        bankAccessBankingModel.setId("TestID" + number + "_" + System.nanoTime());
        bankAccessBankingModel.setBankLogin(bankAccess.getBankLogin());
        bankAccessBankingModel.setBankCode(bankAccess.getBankCode());
        bankAccessBankingModel.setUserId(userId);
        bankAccessBankingModel.setBankName("TestBank" + number);
        bankAccessBankingModel.setPin(null);
        bankAccessBankingModel.setPassportState("testPassportState");

        bankAccessEndpointRepository.save(bankAccessBankingModel);
        number++;
    }

    public boolean existsBankAccess(String bankAccessId) {
        return bankAccessEndpointRepository.exists(bankAccessId);
    }
}
