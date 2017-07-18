package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;
import de.fau.amos.virtualledger.server.model.SavingsAccountSubGoalEntity;
import org.springframework.stereotype.Component;

@Component
public class SavingsAccountEntityTransformer {


    public SavingsAccountSubGoalEntity transformSavingsAccountSubGoalIdentifierIntoEntity(SavingsAccountSubGoal savingsAccountSubGoal) {

        return new SavingsAccountSubGoalEntity(
                savingsAccountSubGoal.getName(),
                savingsAccountSubGoal.getAmount()
        );
    }
}
