package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccountIdentifier;
import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.dtos.SavingsAccount;
import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;
import de.fau.amos.virtualledger.server.model.BankAccountIdentifierEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountSubGoalEntity;
import de.fau.amos.virtualledger.server.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class SavingsAccountIntoEntityTransformerTest {

    private final String savingsAccountSubGoalTestName = "subGoal";
    private final double savingsAccountSubGoalTestAmount = 123.22;
    private final String accessId = "access";
    private final String accountId = "account";
    private final String userEmail = "userEmail";
    private final String userFirstName = "userFirstName";
    private final String userLastName = "userLastName";
    private final String savingsAccountId = "savingsId";
    private final String savingsAccountName = "saving";
    private final double savingsAccountGoal = 1221;
    private final double savingsAccountCurrent = 456;
    private final Date savingsAccountFinalDate = new Date();
    private final Date savingsAccountfinalGoalFinishedDate = new Date();
    private final double delta = 0.01;

    private SavingsAccountIntoEntityTransformer transformer = new SavingsAccountIntoEntityTransformer();

    @Test
    public void transformSavingAccountIntoEntity() {

        // SETUP
        SavingsAccount savingsAccount = new SavingsAccount(savingsAccountId, savingsAccountName, savingsAccountGoal, savingsAccountCurrent, savingsAccountFinalDate, savingsAccountfinalGoalFinishedDate);
        User currentUser = new User(userEmail, userFirstName, userLastName);

        // ACT
        SavingsAccountEntity result = transformer.transformSavingAccountIntoEntity(savingsAccount, currentUser);

        // ASSERT
        assertNotNull(result);
        assertEquals(result.getName(), savingsAccount.getName());
        assertEquals(result.getCurrentbalance(), savingsAccount.getCurrentbalance(), delta);
        assertEquals(result.getGoalbalance(), savingsAccount.getGoalbalance(), delta);
        assertEquals(result.getFinaldate().getTime(), savingsAccount.getFinaldate().getTime());
        assertEquals(result.getFinalGoalFinishedDate().getTime(), savingsAccount.getFinalGoalFinishedDate().getTime());
        assertEquals(result.getSubGoals().size(), 0);
        assertEquals(result.getUserRelations().size(), 1);
    }

    @Test
    public void transformContactIntoEntity() {

        // SETUP
        Contact contact = new Contact(userEmail, userFirstName, userLastName);

        // ACT
        User result = transformer.transformContactIntoEntity(contact);

        // ASSERT
        assertNotNull(result);
        assertEquals(userEmail, result.getEmail());
        assertEquals(userFirstName, result.getFirstName());
        assertEquals(userLastName, result.getLastName());
    }

    @Test
    public void transformBankAccountIdentifierListIntoEntity() {
        // SETUP
        List<BankAccountIdentifier> identifiers = new ArrayList<>();
        BankAccountIdentifier accountIdentifier = new BankAccountIdentifier(accessId, accountId);
        identifiers.add(accountIdentifier);

        // ACT
        List<BankAccountIdentifierEntity> result = transformer.transformBankAccountIdentifierIntoEntity(identifiers);

        // ASSERT
        assertNotNull(result);
        assertEquals(result.size(), identifiers.size());
    }

    @Test
    public void transformBankAccountIdentifierIntoEntityInputNull() {

        // SETUP
        List<BankAccountIdentifier> identifiers = null;

        // ACT
        List<BankAccountIdentifierEntity> result = transformer.transformBankAccountIdentifierIntoEntity(identifiers);

        // ASSERT
        assertNotNull(result);
    }


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
