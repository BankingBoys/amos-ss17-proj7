package de.fau.amos.virtualledger.android.model;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(savingsAccount.getId()).isEqualTo(id);
        assertThat(savingsAccount.getName()).isEqualTo(name);
        assertThat(savingsAccount.getGoalbalance()).isEqualTo(goalBalance);
        assertThat(savingsAccount.getCurrentbalance()).isEqualTo(currentBalance);
        assertThat(savingsAccount.getFinaldate()).isEqualTo(finalDate);
    }


    @Test
    public void teste_getDaysLeft() throws Exception {
        // SETUP
        int id = 123;
        String name = "name";
        double goalBalance = 123.45;
        double currentBalance = 234.567;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sdf.parse("15/01/2017");
        SavingsAccount savingsAccount = new SavingsAccountMocked(id, name, goalBalance, currentBalance, d);


        //Act & Assert
        assertThat(savingsAccount.daysLeft()).isEqualTo(5);
    }


}

class SavingsAccountMocked extends SavingsAccount {

    public SavingsAccountMocked(int id, String name, double goalbalance, double currentbalance, Date finaldate) {
        super(id, name, goalbalance, currentbalance, finaldate);
    }

    @Override
    Date today() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sdf.parse("10/01/2017");
        } catch (Exception e) {

        }
        return null;
    }
}