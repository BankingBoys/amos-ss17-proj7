package de.fau.amos.virtualledger.server.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class SavingsAccountTest {

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
        SingleGoal testSingleGoal = new SingleGoal(goalName, singleGoalBalance);
        Set<SingleGoal> singleGoalSet = new HashSet<>();
        singleGoalSet.add(testSingleGoal);

        // ACT
        SavingsAccount savingsAccount = new SavingsAccount(testId, name, goalBalance, currentBalance, finalDate, singleGoalSet);

        // ASSERT
        Assert.assertNotNull(savingsAccount);
        Assert.assertEquals(testId, savingsAccount.getId());
        Assert.assertEquals(name, savingsAccount.getName());
        Assert.assertEquals(goalBalance, savingsAccount.getGoalbalance(), DELTA);
        Assert.assertEquals(currentBalance, savingsAccount.getCurrentbalance(), DELTA);
        Assert.assertEquals(finalDate, savingsAccount.getFinaldate());
    }
}
