package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccountIdentifier;
import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;
import de.fau.amos.virtualledger.server.model.BankAccountIdentifierEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountSubGoalEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SavingsAccountEntityTransformerTest {

    private final String savingsAccountSubGoalTestName = "subGoal";
    private final double savingsAccountSubGoalTestAmount = 123.22;
    private final String accessId = "access";
    private final String accountId = "account";
    private final double delta = 0.01;

    private SavingsAccountEntityTransformer transformer = new SavingsAccountEntityTransformer();

    /*   public SavingsAccountEntity transformSavingAccountIntoEntity(SavingsAccount savingsAccount, User currentUser) {

       public User transformContactIntoEntity(Contact contact) {

       public List<BankAccountIdentifierEntity> transformBankAccountIdentifierIntoEntity(List<BankAccountIdentifier> assignedBankAccounts) {
*/
    @Test
    public void transformBankAccountIdentifierIntoEntity() {
        // SETUP
        BankAccountIdentifier accountIdentifier = new BankAccountIdentifier(accessId, accountId);

        // ACT
        BankAccountIdentifierEntity result = transformer.transformBankAccountIdentifierIntoEntity(accountIdentifier);

        // ASSERT
        assertNotNull(result);
        assertEquals(result.getAccessid(), accessId);
        assertEquals(result.getAccountid(), accountId);
    }

    @Test
    public void transformSavingsAccountSubGoalListIdentifierIntoEntity() {

        // SETUP
        List<SavingsAccountSubGoal> subGoals = new ArrayList<SavingsAccountSubGoal>();
        SavingsAccountSubGoal savingsAccountSubGoal = new SavingsAccountSubGoal(savingsAccountSubGoalTestName, savingsAccountSubGoalTestAmount);
        subGoals.add(savingsAccountSubGoal);

        // ACT
        Set<SavingsAccountSubGoalEntity> result = transformer.transformSavingsAccountSubGoalIdentifierIntoEntity(subGoals);

        // ASSERT
        assertNotNull(result);
        assertEquals(result.size(), subGoals.size());
    }

    @Test
    public void transformSavingsAccountSubGoalListIdentifierIntoEntityInputNull() {

        // SETUP
        List<SavingsAccountSubGoal> subGoals = null;

        // ACT
        Set<SavingsAccountSubGoalEntity> result = transformer.transformSavingsAccountSubGoalIdentifierIntoEntity(subGoals);

        // ASSERT
        assertNotNull(result);
    }

    @Test
    public void transformSavingsAccountSubGoalIdentifierIntoEntity() {

        // SETUP
        SavingsAccountSubGoal savingsAccountSubGoal = new SavingsAccountSubGoal(savingsAccountSubGoalTestName, savingsAccountSubGoalTestAmount);

        // ACT
        SavingsAccountSubGoalEntity result = transformer.transformSavingsAccountSubGoalIdentifierIntoEntity(savingsAccountSubGoal);

        // ASSERT
        assertNotNull(result);
        assertEquals(result.getName(), savingsAccountSubGoalTestName);
        assertEquals(result.getAmount(), savingsAccountSubGoalTestAmount, delta);
    }


}
