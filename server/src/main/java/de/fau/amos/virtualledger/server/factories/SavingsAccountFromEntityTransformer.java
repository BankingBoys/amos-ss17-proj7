package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccountIdentifier;
import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.dtos.SavingsAccount;
import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;
import de.fau.amos.virtualledger.server.model.BankAccountIdentifierEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountSubGoalEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountUserRelation;
import de.fau.amos.virtualledger.server.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SavingsAccountFromEntityTransformer {


    public SavingsAccount transformSavingAccountFromEntity(SavingsAccountEntity savingsAccountEntity, User currentUser) {

        SavingsAccount savingsAccount = new SavingsAccount(
                savingsAccountEntity.getName(),
                savingsAccountEntity.getGoalbalance(),
                savingsAccountEntity.getCurrentbalance(),
                savingsAccountEntity.getFinalGoalFinishedDate(),
                savingsAccountEntity.getFinalGoalFinishedDate());

        List<SavingsAccountSubGoal> savingsAccountSubGoals = transformSavingsAccountSubGoalIdentifierFromEntity(savingsAccountEntity.getSubGoals());
        savingsAccount.setSubGoals(savingsAccountSubGoals);

        List<BankAccountIdentifier> bankAccountIdentifiers = transformBankAccountIdentifierFromEntity(savingsAccountEntity.getUserRelations(), currentUser);
        savingsAccount.setAssignedBankAccounts(bankAccountIdentifiers);

        List<Contact> contacts = transformContactFromEntity(savingsAccountEntity.getUserRelations());
        savingsAccount.setAdditionalAssignedUsers(contacts);

        return savingsAccount;
    }

    public List<Contact> transformContactFromEntity(Set<SavingsAccountUserRelation> savingsAccountUserRelations) {
        List<Contact> contacts = new ArrayList<>();
        if (savingsAccountUserRelations == null) {
            return contacts;
        }

        for (SavingsAccountUserRelation savingsAccountUserRelation : savingsAccountUserRelations) {
            contacts.add(transformContactFromEntity(savingsAccountUserRelation.getUser()));
        }
        return contacts;
    }

    private Contact transformContactFromEntity(User user) {
        return new Contact(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public List<BankAccountIdentifier> transformBankAccountIdentifierFromEntity(Set<SavingsAccountUserRelation> userRelations, User currentUser) {

        List<BankAccountIdentifier> bankAccountIdentifiers = new ArrayList<>();
        if (userRelations == null) {
            return bankAccountIdentifiers;
        }

        for (SavingsAccountUserRelation userRelation : userRelations) {
            if (userRelation.getUser().getEmail().equals(currentUser.getEmail())) {
                bankAccountIdentifiers.addAll(transformBankAccountIdentifierFromEntity(userRelation.getBankAccountIdentifierEntityList()));
            }
        }
        return bankAccountIdentifiers;
    }

    private List<BankAccountIdentifier> transformBankAccountIdentifierFromEntity(List<BankAccountIdentifierEntity> bankAccountIdentifierEntityList) {
        List<BankAccountIdentifier> bankAccountIdentifiers = new ArrayList<>();
        if (bankAccountIdentifierEntityList == null) {
            return bankAccountIdentifiers;
        }

        for (BankAccountIdentifierEntity bankAccountIdentifierEntity : bankAccountIdentifierEntityList) {
            bankAccountIdentifiers.add(transformBankAccountIdentifierFromEntity(bankAccountIdentifierEntity));
        }
        return bankAccountIdentifiers;
    }

    public BankAccountIdentifier transformBankAccountIdentifierFromEntity(BankAccountIdentifierEntity bankAccountIdentifierEntity) {

        return new BankAccountIdentifier(
                bankAccountIdentifierEntity.getAccessid(),
                bankAccountIdentifierEntity.getAccountid()
        );
    }

    public List<SavingsAccountSubGoal> transformSavingsAccountSubGoalIdentifierFromEntity(Set<SavingsAccountSubGoalEntity> subGoalEntities) {

        List<SavingsAccountSubGoal> savingsAccountSubGoals = new ArrayList<>();
        if (subGoalEntities == null) {
            return savingsAccountSubGoals;
        }

        for (SavingsAccountSubGoalEntity savingsAccountSubGoalEntity : subGoalEntities) {
            savingsAccountSubGoals.add(transformSavingsAccountSubGoalIdentifierFromEntity(savingsAccountSubGoalEntity));
        }
        return savingsAccountSubGoals;
    }

    public SavingsAccountSubGoal transformSavingsAccountSubGoalIdentifierFromEntity(SavingsAccountSubGoalEntity savingsAccountSubGoalEntity) {

        return new SavingsAccountSubGoal(
                savingsAccountSubGoalEntity.getName(),
                savingsAccountSubGoalEntity.getAmount()
        );
    }
}
