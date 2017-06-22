package de.fau.amos.virtualledger.server.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;


public class SavingsAccountTest {

    @Test
    public void constructor_getters_match() {
        // SETUP
        int id = 123;
        String name = "name";
        double goalBalance = 123.45;
        double currentBalance = 234.567;
        Date finalDate = new Date();

        // ACT
        SavingsAccount savingsAccount = new SavingsAccount(id, name, goalBalance, currentBalance, finalDate);

        // ASSERT
        Assert.assertNotNull(savingsAccount);
        Assert.assertEquals(id, savingsAccount.id);
        Assert.assertEquals(name, savingsAccount.name);
        Assert.assertEquals(goalBalance, savingsAccount.goalbalance, 0.01);
        Assert.assertEquals(currentBalance, savingsAccount.currentbalance, 0.01);
        Assert.assertEquals(finalDate, savingsAccount.finaldate);
    }
}
