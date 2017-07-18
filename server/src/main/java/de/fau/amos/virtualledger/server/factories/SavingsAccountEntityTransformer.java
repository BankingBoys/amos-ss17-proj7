package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;
import de.fau.amos.virtualledger.server.model.SavingsAccountSubGoalEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SavingsAccountEntityTransformer {


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
