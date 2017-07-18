package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccountIdentifier;
import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;
import de.fau.amos.virtualledger.server.model.BankAccountIdentifierEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountSubGoalEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SavingsAccountEntityTransformer {


    public List<BankAccountIdentifierEntity> transformBankAccountIdentifierIntoEntity(List<BankAccountIdentifier> assignedBankAccounts) {

        List<BankAccountIdentifierEntity> bankAccountIdentifierEntities = new ArrayList<>();
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
