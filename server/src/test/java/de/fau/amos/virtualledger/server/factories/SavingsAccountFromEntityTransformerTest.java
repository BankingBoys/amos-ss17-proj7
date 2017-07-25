package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccountIdentifier;
import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;
import de.fau.amos.virtualledger.server.model.BankAccountIdentifierEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountSubGoalEntity;
import de.fau.amos.virtualledger.server.model.SavingsAccountUserRelation;
import de.fau.amos.virtualledger.server.model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Georg on 25.07.2017.
 */
public class SavingsAccountFromEntityTransformerTest {
    private final double delta = 0.01;
    private final String bankAccountIdPrefix = "account";
    private final String bankAccessIdPrefix = "access";
    private final String subGoalNamePrefix = "subGoalName";
    private final double subGoalAmount = 100.00;
    private final String userEmailPostfix = "e@mail.de";
    private final String userFirstNamePrefix = "firstName";
    private final String userLastNamePrefix = "lastName";

    @Test
    public void transformSavingAccountFromEntity() throws Exception {
    }

    @Test
    public void transformSavingAccountFromEntity1() throws Exception {
    }

    @Test
    public void transformContactsFromEntity() throws Exception {
        // SETUP
        Set<SavingsAccountUserRelation> relationEntitySet = new HashSet<>();
        final int numberOfUsers = 3;
        User mainUser = generateUserEntity(0);
        relationEntitySet.add(new SavingsAccountUserRelation(mainUser));
        for (int i = 1; i < numberOfUsers; i++) {
            User user = generateUserEntity(i);
            relationEntitySet.add(new SavingsAccountUserRelation(user));
        }

        SavingsAccountFromEntityTransformer transformer = new SavingsAccountFromEntityTransformer();

        // ACT
        List<Contact> resultContactList = transformer.transformContactFromEntity(relationEntitySet, mainUser);

        // ASSERT
        Assert.assertNotNull(resultContactList);
        Assert.assertEquals(resultContactList.size(), numberOfUsers - 1);
    }

    @Test
    public void transformContactFromEntityInputNull() throws Exception {
        // SETUP
        Set<SavingsAccountUserRelation> relationEntitySet = null;

        SavingsAccountFromEntityTransformer transformer = new SavingsAccountFromEntityTransformer();

        // ACT
        List<Contact> resultContactList = transformer.transformContactFromEntity(relationEntitySet, null);

        // ASSERT
        Assert.assertNotNull(resultContactList);
        Assert.assertEquals(resultContactList.size(), 0);
    }

    @Test
    public void transformContactFromEntity() throws Exception {
        // SETUP
        User user = generateUserEntity(1);

        SavingsAccountFromEntityTransformer transformer = new SavingsAccountFromEntityTransformer();

        // ACT
        Contact resultContact = transformer.transformContactFromEntity(user);

        // ASSERT
        Assert.assertNotNull(resultContact);
        Assert.assertEquals(resultContact.getEmail(), user.getEmail());
        Assert.assertEquals(resultContact.getFirstName(), user.getFirstName());
        Assert.assertEquals(resultContact.getLastName(), user.getLastName());
    }

    @Test
    public void transformBankAccountIdentifiersFromEntity() throws Exception {
        // SETUP
        List<BankAccountIdentifierEntity> identifierEntityList = new ArrayList<>();
        final int numberIdentifiers = 3;
        for (int i = 0; i < numberIdentifiers; i++) {
            identifierEntityList.add(generateBankAccountIdentifier(i));
        }

        SavingsAccountFromEntityTransformer transformer = new SavingsAccountFromEntityTransformer();

        // ACT
        List<BankAccountIdentifier> resultIdentifierList = transformer.transformBankAccountIdentifierFromEntity(identifierEntityList);

        // ASSERT
        Assert.assertNotNull(resultIdentifierList);
        Assert.assertEquals(resultIdentifierList.size(), numberIdentifiers);
    }

    @Test
    public void transformBankAccountIdentifiersInputNull() throws Exception {
        // SETUP
        List<BankAccountIdentifierEntity> identifierEntityList = null;

        SavingsAccountFromEntityTransformer transformer = new SavingsAccountFromEntityTransformer();

        // ACT
        List<BankAccountIdentifier> resultIdentifierList = transformer.transformBankAccountIdentifierFromEntity(identifierEntityList);

        // ASSERT
        Assert.assertNotNull(resultIdentifierList);
        Assert.assertEquals(resultIdentifierList.size(), 0);
    }

    @Test
    public void transformBankAccountIdentifierFromEntity() throws Exception {
        // SETUP
        BankAccountIdentifierEntity identifierEntity = generateBankAccountIdentifier(1);

        SavingsAccountFromEntityTransformer transformer = new SavingsAccountFromEntityTransformer();

        // ACT
        BankAccountIdentifier resultIdentifier = transformer.transformBankAccountIdentifierFromEntity(identifierEntity);

        // ASSERT
        Assert.assertNotNull(resultIdentifier);
        Assert.assertEquals(resultIdentifier.getAccessid(), identifierEntity.getAccessid());
        Assert.assertEquals(resultIdentifier.getAccountid(), identifierEntity.getAccountid());
    }

    @Test
    public void transformSavingsAccountSubGoalIdentifiersFromEntity() throws Exception {
        // SETUP
        Set<SavingsAccountSubGoalEntity> subGoalEntityList = new HashSet<>();
        final int numberSubGoals = 3;
        for (int i = 0; i < numberSubGoals; i++) {
            SavingsAccountSubGoalEntity subGoalEntity = generateSubGoalEntity(i);
            subGoalEntityList.add(subGoalEntity);
        }

        SavingsAccountFromEntityTransformer transformer = new SavingsAccountFromEntityTransformer();

        // ACT
        List<SavingsAccountSubGoal> resultSubGoalList = transformer.transformSavingsAccountSubGoalIdentifierFromEntity(subGoalEntityList);

        // ASSERT
        Assert.assertNotNull(resultSubGoalList);
        Assert.assertEquals(resultSubGoalList.size(), numberSubGoals);
    }

    @Test
    public void transformSavingsAccountSubGoalIdentifiersFromEntityListNull() throws Exception {
        // SETUP
        Set<SavingsAccountSubGoalEntity> subGoalEntitySet = null;

        SavingsAccountFromEntityTransformer transformer = new SavingsAccountFromEntityTransformer();

        // ACT
        List<SavingsAccountSubGoal> resultSubGoalList = transformer.transformSavingsAccountSubGoalIdentifierFromEntity(subGoalEntitySet);

        // ASSERT
        Assert.assertNotNull(resultSubGoalList);
        Assert.assertEquals(resultSubGoalList.size(), 0);
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

    private User generateUserEntity(int dummyId) {
        return new User(dummyId + userEmailPostfix, userFirstNamePrefix + dummyId, userLastNamePrefix + dummyId);
    }

    private BankAccountIdentifierEntity generateBankAccountIdentifier(int dummyId) {
        return new BankAccountIdentifierEntity(bankAccessIdPrefix + dummyId, bankAccountIdPrefix + dummyId);
    }

    private SavingsAccountSubGoalEntity generateSubGoalEntity(int dummyId) {

        String subGoalName = subGoalNamePrefix + dummyId;
        return new SavingsAccountSubGoalEntity(subGoalName, subGoalAmount);
    }


}
