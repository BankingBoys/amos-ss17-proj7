package de.fau.amos.virtualledger.server.savings;

import de.fau.amos.virtualledger.dtos.SavingsAccount;
import de.fau.amos.virtualledger.server.contacts.UserNotFoundException;
import de.fau.amos.virtualledger.server.factories.SavingsAccountFromEntityTransformer;
import de.fau.amos.virtualledger.server.factories.SavingsAccountIntoEntityTransformer;
import de.fau.amos.virtualledger.server.model.SavingsAccountEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountUserRelation;
import de.fau.amos.virtualledger.server.model.User;
import de.fau.amos.virtualledger.server.persistence.SavingsAccountRepository;
import de.fau.amos.virtualledger.server.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;

@Component

public class SavingsController {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @SuppressWarnings("unused")
    private static final String TAG = "SavingsController";

    private SavingsAccountRepository savingsAccountRepository;
    private UserRepository userRepository;

    @Autowired
    private SavingsAccountIntoEntityTransformer savingsAccountIntoEntityTransformer;

    @Autowired
    private SavingsAccountFromEntityTransformer savingsAccountFromEntityTransformer;

    @Autowired
    public SavingsController(UserRepository userRepository, SavingsAccountRepository savingsAccountRepository) {
        this.userRepository = userRepository;
        this.savingsAccountRepository = savingsAccountRepository;
    }

    protected SavingsController() {
    }

    public List<SavingsAccount> getSavingAccounts(String email) {
        List<SavingsAccountEntity> savingsAccountEntityList = savingsAccountRepository.findByUserEmail(email);
        for (SavingsAccountEntity savingsAccountEntity : savingsAccountEntityList) {
            // workarount because somehow the eager fetching did not work as planned
            Set<SavingsAccountUserRelation> relations = savingsAccountRepository.findUserRelationsBySaving(savingsAccountEntity);
            savingsAccountEntity.setUserRelations(relations);
        }
        User user = userRepository.findOne(email);

        return savingsAccountFromEntityTransformer.transformSavingAccountFromEntity(savingsAccountEntityList, user);
    }

    public void addSavingAccount(String email, SavingsAccount savingsAccount) {

        User user = userRepository.findOne(email);
        SavingsAccountEntity savingsAccountEntity = savingsAccountIntoEntityTransformer.transformSavingAccountIntoEntity(savingsAccount, user);
        savingsAccountRepository.save(savingsAccountEntity);
    }

    public void deleteSavingAccount(final String userEmail, final Integer accountId) throws UserNotFoundException, SavingsAccountNotFoundException {
        User owner = userRepository.findOne(userEmail);
        assertUserNotNull(owner);

        assertSavingAccountExists(accountId);
        savingsAccountRepository.delete(accountId);

    }

    private void assertUserNotNull(User user) throws UserNotFoundException {
        if (user == null) {
            LOGGER.info(TAG + "User could not be found");
            throw new UserNotFoundException("User/Contact could not be found");
        }
    }

    private void assertSavingAccountExists(final Integer accountId) throws SavingsAccountNotFoundException {
        SavingsAccountEntity entity = savingsAccountRepository.findOne(accountId);
        if (entity == null) {
            throw new SavingsAccountNotFoundException("Saving Account with Id: " + accountId + "could not be found");
        }
    }

}
