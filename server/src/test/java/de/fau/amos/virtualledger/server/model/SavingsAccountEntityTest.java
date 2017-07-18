package de.fau.amos.virtualledger.server.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class SavingsAccountEntityTest {

    private static final double DELTA = 0.01;

    @Test
    public void constructorGettersMatch() {
        // SETUP
        final int testId = 123;
        final String name = "name";
        final double goalBalance = 123.45;
        final double currentBalance = 234.567;
        Date finalDate = new Date();
        final String goalName = "testGoal";
        final double singleGoalBalance = 123123.1;
        SavingsAccountSubGoalEntity testSavingsAccountSubGoalEntity = new SavingsAccountSubGoalEntity(goalName, singleGoalBalance);
        Set<SavingsAccountSubGoalEntity> savingsAccountSubGoalEntitySet = new HashSet<>();
        savingsAccountSubGoalEntitySet.add(testSavingsAccountSubGoalEntity);

        // ACT
        SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity(testId, name, goalBalance, currentBalance, finalDate, savingsAccountSubGoalEntitySet);

        // ASSERT
        Assert.assertNotNull(savingsAccountEntity);
        Assert.assertEquals(testId, savingsAccountEntity.getId());
        Assert.assertEquals(name, savingsAccountEntity.getName());
        Assert.assertEquals(goalBalance, savingsAccountEntity.getGoalbalance(), DELTA);
        Assert.assertEquals(currentBalance, savingsAccountEntity.getCurrentbalance(), DELTA);
        Assert.assertEquals(finalDate, savingsAccountEntity.getFinaldate());
    }
}
