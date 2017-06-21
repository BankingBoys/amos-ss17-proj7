package dtos;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import de.fau.amos.virtualledger.dtos.SavingsAccount;

/**
 * Created by Georg on 21.06.2017.
 */

public class SavingsAccountTest {

    @Test
    public void constructor_getters_match() {
        // SETUP
        String id = "id";
        String name = "name";
        double goalBalance = 123.45;
        double currentBalance = 234.567;
        Date finalDate = new Date();

        // ACT
        SavingsAccount savingsAccount = new SavingsAccount(id, name, goalBalance, currentBalance, finalDate);

        // ASSERT
        Assert.assertNotNull(savingsAccount);
        Assert.assertEquals(id, savingsAccount.getId());
        Assert.assertEquals(name, savingsAccount.getName());
        Assert.assertEquals(goalBalance, savingsAccount.getGoalBalance(), 0.01);
        Assert.assertEquals(currentBalance, savingsAccount.getCurrentBalance(), 0.01);
        Assert.assertEquals(finalDate, savingsAccount.getFinalDate());
    }
}
