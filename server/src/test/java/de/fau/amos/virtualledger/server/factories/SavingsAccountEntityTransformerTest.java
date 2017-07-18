package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;
import de.fau.amos.virtualledger.server.model.SavingsAccountSubGoalEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SavingsAccountEntityTransformerTest {

    private final String savingsAccountSubGoalTestName = "subGoal";
    private final double savingsAccountSubGoalTestAmount = 123.22;
    private final double delta = 0.01;

    private SavingsAccountEntityTransformer transformer = new SavingsAccountEntityTransformer();

    /*   public SavingsAccountEntity transformSavingAccountIntoEntity(SavingsAccount savingsAccount, User currentUser) {

       public User transformContactIntoEntity(Contact contact) {

       public List<BankAccountIdentifierEntity> transformBankAccountIdentifierIntoEntity(List<BankAccountIdentifier> assignedBankAccounts) {

       public BankAccountIdentifierEntity transformBankAccountIdentifierIntoEntity(BankAccountIdentifier bankAccountIdentifier) {

       public Set<SavingsAccountSubGoalEntity> transformSavingsAccountSubGoalIdentifierIntoEntity(List<SavingsAccountSubGoal> subGoals) {
   */
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
