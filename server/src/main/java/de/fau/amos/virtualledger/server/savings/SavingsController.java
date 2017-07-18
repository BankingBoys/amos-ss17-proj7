package de.fau.amos.virtualledger.server.savings;

import de.fau.amos.virtualledger.dtos.SavingsAccount;
import de.fau.amos.virtualledger.server.factories.SavingsAccountIntoEntityTransformer;
import de.fau.amos.virtualledger.server.model.SavingsAccountEntity;
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
    private SavingsAccountIntoEntityTransformer savingsAccountIntoEntityTransformer;

    @Autowired
    public SavingsController(UserRepository userRepository, SavingsAccountRepository savingsAccountRepository) {
        this.userRepository = userRepository;
        this.savingsAccountRepository = savingsAccountRepository;
    }

    protected SavingsController() {
    }

    public List<SavingsAccountEntity> getSavingAccounts(String email) {

        return savingsAccountRepository.findByUserEmailAndLoadUserRelations(email);
    }

    public void addSavingAccount(String email, SavingsAccount savingsAccount) {

        User user = userRepository.findOne(email);
        SavingsAccountEntity savingsAccountEntity = savingsAccountIntoEntityTransformer.transformSavingAccountIntoEntity(savingsAccount, user);
        savingsAccountRepository.save(savingsAccountEntity);
    }
}
