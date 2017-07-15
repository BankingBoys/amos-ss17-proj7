package de.fau.amos.virtualledger.server.savings;

import de.fau.amos.virtualledger.server.model.BankAccountIdentifier;
import de.fau.amos.virtualledger.server.model.SavingsAccount;
import de.fau.amos.virtualledger.server.model.SavingsAccountUserRelation;
import de.fau.amos.virtualledger.server.model.User;
import de.fau.amos.virtualledger.server.persistence.SavingsAccountRepository;
import de.fau.amos.virtualledger.server.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class SavingsController {

    private SavingsAccountRepository savingsAccountRepository;
    private UserRepository userRepository;

    @Autowired
    public SavingsController(UserRepository userRepository, SavingsAccountRepository savingsAccountRepository) {
        this.userRepository = userRepository;
        this.savingsAccountRepository = savingsAccountRepository;
    }

    protected SavingsController() {
    }

    public List<SavingsAccount> getSavingAccounts(String email) {

        return savingsAccountRepository.findByUserEmailAndLoadUserRelations(email);
    }

    public void addSavingAccount(String email, SavingsAccount savingsAccount, List<BankAccountIdentifier> bankAccountIdentifierList) {

        User user = userRepository.findOne(email);
        SavingsAccountUserRelation savingsAccountUserRelation = new SavingsAccountUserRelation(user, bankAccountIdentifierList);
        savingsAccount.getUserRelations().add(savingsAccountUserRelation);
        savingsAccountRepository.save(savingsAccount);
    }
}
