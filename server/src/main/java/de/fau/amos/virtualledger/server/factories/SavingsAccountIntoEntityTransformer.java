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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SavingsAccountIntoEntityTransformer {


    public SavingsAccountEntity transformSavingAccountIntoEntity(SavingsAccount savingsAccount, User currentUser) {

        SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity(
                savingsAccount.getName(),
                savingsAccount.getGoalbalance(),
                savingsAccount.getCurrentbalance(),
                savingsAccount.getFinalGoalFinishedDate(),
                savingsAccount.getFinalGoalFinishedDate());

        Set<SavingsAccountSubGoalEntity> savingsAccountSubGoalEntities = transformSavingsAccountSubGoalIdentifierIntoEntity(savingsAccount.getSubGoals());
        savingsAccountEntity.setSubGoals(savingsAccountSubGoalEntities);

        List<BankAccountIdentifierEntity> bankAccountIdentifierEntities = transformBankAccountIdentifierIntoEntity(savingsAccount.getAssignedBankAccounts());
        Set<SavingsAccountUserRelation> savingsAccountUserRelations = new HashSet<>();
        savingsAccountUserRelations.add(new SavingsAccountUserRelation(currentUser, bankAccountIdentifierEntities));

        for (Contact contact : savingsAccount.getAdditionalAssignedUsers()) {
            savingsAccountUserRelations.add(new SavingsAccountUserRelation(transformContactIntoEntity(contact)));
        }

        savingsAccountEntity.setUserRelations(savingsAccountUserRelations);
        return savingsAccountEntity;
    }

    public User transformContactIntoEntity(Contact contact) {
        return new User(contact.getEmail(), contact.getFirstName(), contact.getLastName());
    }

    public List<BankAccountIdentifierEntity> transformBankAccountIdentifierIntoEntity(List<BankAccountIdentifier> assignedBankAccounts) {

        List<BankAccountIdentifierEntity> bankAccountIdentifierEntities = new ArrayList<>();
        if (assignedBankAccounts == null) {
            return bankAccountIdentifierEntities;
        }

        for (BankAccountIdentifier bankAccountIdentifier : assignedBankAccounts) {
            bankAccountIdentifierEntities.add(transformBankAccountIdentifierIntoEntity(bankAccountIdentifier));
        }
        return bankAccountIdentifierEntities;
    }

    public BankAccountIdentifierEntity transformBankAccountIdentifierIntoEntity(BankAccountIdentifier bankAccountIdentifier) {

        return new BankAccountIdentifierEntity(
                bankAccountIdentifier.getAccessid(),
                bankAccountIdentifier.getAccountid()
        );
    }

    public Set<SavingsAccountSubGoalEntity> transformSavingsAccountSubGoalIdentifierIntoEntity(List<SavingsAccountSubGoal> subGoals) {

        Set<SavingsAccountSubGoalEntity> savingsAccountSubGoalEntities = new HashSet<>();
        if (subGoals == null) {
            return savingsAccountSubGoalEntities;
        }

        for (SavingsAccountSubGoal savingsAccountSubGoal : subGoals) {
            savingsAccountSubGoalEntities.add(transformSavingsAccountSubGoalIdentifierIntoEntity(savingsAccountSubGoal));
        }
        return savingsAccountSubGoalEntities;
    }

    public SavingsAccountSubGoalEntity transformSavingsAccountSubGoalIdentifierIntoEntity(SavingsAccountSubGoal savingsAccountSubGoal) {

        return new SavingsAccountSubGoalEntity(
                savingsAccountSubGoal.getName(),
                savingsAccountSubGoal.getAmount()
        );
    }
}
