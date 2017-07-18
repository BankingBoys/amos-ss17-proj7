package de.fau.amos.virtualledger.server.savings;

import de.fau.amos.virtualledger.server.model.BankAccountIdentifier;
import de.fau.amos.virtualledger.server.model.SavingsAccountEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountUserRelation;
import de.fau.amos.virtualledger.server.model.User;
import de.fau.amos.virtualledger.server.persistence.SavingsAccountRepository;
import de.fau.amos.virtualledger.server.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public List<SavingsAccountEntity> getSavingAccounts(String email) {

        return savingsAccountRepository.findByUserEmailAndLoadUserRelations(email);
    }

    public void addSavingAccount(String email, SavingsAccountEntity savingsAccountEntity, List<BankAccountIdentifier> bankAccountIdentifierList, List<String> usersEmails) {

        User user = userRepository.findOne(email);
        SavingsAccountUserRelation savingsAccountUserRelation = new SavingsAccountUserRelation(user, bankAccountIdentifierList);
        savingsAccountEntity.getUserRelations().add(savingsAccountUserRelation);
        for (String e : usersEmails) {
            SavingsAccountUserRelation savingsAccountParticipatingUserRelation = new SavingsAccountUserRelation(userRepository.findOne(e), new ArrayList<>());
            savingsAccountEntity.getUserRelations().add(savingsAccountParticipatingUserRelation);
        }
        savingsAccountRepository.save(savingsAccountEntity);
    }
}
