package de.fau.amos.virtualledger.server.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;


public class SavingsAccountTest {

    private static final double DELTA = 0.01;

    @Test
    public void constructorGettersMatch() {
        // SETUP
        final int testId = 123;
        String name = "name";
        final double goalBalance = 123.45;
        final double currentBalance = 234.567;
        Date finalDate = new Date();

        // ACT
        SavingsAccount savingsAccount = new SavingsAccount(testId, name, goalBalance, currentBalance, finalDate);

        // ASSERT
        Assert.assertNotNull(savingsAccount);
        Assert.assertEquals(testId, savingsAccount.getId());
        Assert.assertEquals(name, savingsAccount.getName());
        Assert.assertEquals(goalBalance, savingsAccount.getGoalbalance(), DELTA);
        Assert.assertEquals(currentBalance, savingsAccount.getCurrentbalance(), DELTA);
        Assert.assertEquals(finalDate, savingsAccount.getFinaldate());
    }
}
