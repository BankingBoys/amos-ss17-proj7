package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;
import de.fau.amos.virtualledger.server.model.SavingsAccountSubGoalEntity;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Georg on 25.07.2017.
 */
public class SavingsAccountFromEntityTransformerTest {
    private final double delta = 0.01;
    private final String subGoalNamePrefix = "subGoalName";
    private final double subGoalAmount = 100.00;

    @Test
    public void transformSavingAccountFromEntity() throws Exception {
    }

    @Test
    public void transformSavingAccountFromEntity1() throws Exception {
    }

    @Test
    public void transformContactFromEntity() throws Exception {
    }

    @Test
    public void transformBankAccountIdentifierFromEntity() throws Exception {
    }

    @Test
    public void transformBankAccountIdentifierFromEntity1() throws Exception {
    }

    @Test
    public void transformSavingsAccountSubGoalIdentifiersFromEntity() throws Exception {
    }

    @Test
    public void transformSavingsAccountSubGoalIdentifierFromEntity() throws Exception {
        // SETUP
        SavingsAccountSubGoalEntity subGoalEntity = generateSubGoalEntity(1);

        SavingsAccountFromEntityTransformer transformer = new SavingsAccountFromEntityTransformer();

        // ACT
        SavingsAccountSubGoal resultSubGoal = transformer.transformSavingsAccountSubGoalIdentifierFromEntity(subGoalEntity);

        // ASSERT
        Assert.assertNotNull(resultSubGoal);
        Assert.assertEquals(resultSubGoal.getName(), subGoalEntity.getName());
        Assert.assertEquals(resultSubGoal.getAmount(), subGoalEntity.getAmount(), delta);
    }

    private SavingsAccountSubGoalEntity generateSubGoalEntity(int dummyId) {

        String subGoalName = subGoalNamePrefix + dummyId;
        return new SavingsAccountSubGoalEntity(subGoalName, subGoalAmount);
    }

}
