package de.fau.amos.virtualledger.server.savings;

import de.fau.amos.virtualledger.server.model.SavingsAccount;
import de.fau.amos.virtualledger.server.persistence.SavingsAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Scope("request")
public class SavingsController {

    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
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

