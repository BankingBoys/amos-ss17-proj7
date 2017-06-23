package de.fau.amos.virtualledger.server.savings;

import de.fau.amos.virtualledger.server.model.SavingsAccount;
import de.fau.amos.virtualledger.server.persistence.SavingsAccountRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class SavingsController {

    private SavingsAccountRepository savingsAccountRepository;

    @Inject
    public SavingsController(SavingsAccountRepository savingsAccountRepository) {
        this.savingsAccountRepository = savingsAccountRepository;
    }
    protected  SavingsController () { }

    public List<SavingsAccount> getSavingAccounts(String email) {

        return savingsAccountRepository.getSavingsAccountsByUserEmail(email);
    }

    public void addSavingAccount(String email, SavingsAccount savingsAccount) throws SavingsException {

        savingsAccountRepository.createSavingsAccount(email, savingsAccount);
    }
}

