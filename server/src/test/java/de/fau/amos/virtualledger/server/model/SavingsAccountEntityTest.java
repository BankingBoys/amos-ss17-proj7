package de.fau.amos.virtualledger.server.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;


public class SavingsAccountEntityTest {

    private static final double DELTA = 0.01;

    @Test
    public void constructorGettersMatch() {
        // SETUP
        final String name = "name";
        final double goalBalance = 123.45;
        final double currentBalance = 234.567;
        Date finalDate = new Date();
        Date finalGoalFinishedDate = new Date();
        final String goalName = "testGoal";
        final double singleGoalBalance = 123123.1;

        // ACT
        SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity(name, goalBalance, currentBalance, finalDate, finalGoalFinishedDate);

        // ASSERT
        Assert.assertNotNull(savingsAccountEntity);
        Assert.assertEquals(name, savingsAccountEntity.getName());
        Assert.assertEquals(goalBalance, savingsAccountEntity.getGoalbalance(), DELTA);
        Assert.assertEquals(currentBalance, savingsAccountEntity.getCurrentbalance(), DELTA);
        Assert.assertEquals(finalDate, savingsAccountEntity.getFinaldate());
    }
}
