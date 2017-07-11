package de.fau.amos.virtualledger.server.savings;

import java.util.List;

import de.fau.amos.virtualledger.server.model.User;
import de.fau.amos.virtualledger.server.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fau.amos.virtualledger.server.model.SavingsAccount;
import de.fau.amos.virtualledger.server.persistence.SavingsAccountRepository;

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

        return savingsAccountRepository.findByUserEmail(email);
    }

    public void addSavingAccount(String email, SavingsAccount savingsAccount) {

        User user = userRepository.findOne(email);
        savingsAccount.getUsers().add(user);
        savingsAccountRepository.save(savingsAccount);
    }
}
