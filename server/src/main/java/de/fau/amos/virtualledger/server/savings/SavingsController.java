package de.fau.amos.virtualledger.server.savings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fau.amos.virtualledger.server.model.SavingsAccount;
import de.fau.amos.virtualledger.server.persistence.SavingsAccountRepository;

@Component

public class SavingsController {

    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    public SavingsController(SavingsAccountRepository savingsAccountRepository) {
        this.savingsAccountRepository = savingsAccountRepository;
    }

    protected SavingsController() {
    }

    public List<SavingsAccount> getSavingAccounts(String email) {

        return savingsAccountRepository.getSavingsAccountsByUserEmail(email);
    }

    public void addSavingAccount(String email, SavingsAccount savingsAccount) {

        savingsAccountRepository.createSavingsAccount(email, savingsAccount);
    }
}
