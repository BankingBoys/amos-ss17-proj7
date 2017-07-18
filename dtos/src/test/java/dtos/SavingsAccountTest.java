package dtos;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.fau.amos.virtualledger.dtos.SavingsAccount;

import static org.assertj.core.api.Assertions.assertThat;

public class SavingsAccountTest {

    @Test
    public void constructorGettersMatch() {
        // SETUP
        String id = "123";
        String name = "name";
        final double goalBalance = 123.45;
        final double currentBalance = 234.567;
        Date finalDate = new Date();

        // ACT
        SavingsAccount savingsAccount = new SavingsAccount(id, name, goalBalance, currentBalance, finalDate, new Date());

        // ASSERT
        assertThat(savingsAccount.getId()).isEqualTo(id);
        assertThat(savingsAccount.getName()).isEqualTo(name);
        assertThat(savingsAccount.getGoalbalance()).isEqualTo(goalBalance);
        assertThat(savingsAccount.getCurrentbalance()).isEqualTo(currentBalance);
        assertThat(savingsAccount.getFinaldate()).isEqualTo(finalDate);
    }

    @Test
    public void constructorTest() {
        SavingsAccount savingsAccount = new SavingsAccount();
        assertThat(savingsAccount).isNotNull();
    }

    @Test
    public void setterAndGetterTest() {
        String id = "12345";
        String name = "name2";
        final double goalBalance = 123.4555;
        final double currentBalance = 234.56755;
        Date finalDate = new Date();

        SavingsAccount savingsAccount = new SavingsAccount();

        savingsAccount.setId(id);
        assertThat(savingsAccount.getId()).isEqualTo(id);

        savingsAccount.setName(name);
        assertThat(savingsAccount.getName()).isEqualTo(name);

        savingsAccount.setGoalbalance(goalBalance);
        assertThat(savingsAccount.getGoalbalance()).isEqualTo(goalBalance);

        savingsAccount.setCurrentbalance(currentBalance);
        assertThat(savingsAccount.getCurrentbalance()).isEqualTo(currentBalance);

        savingsAccount.setFinaldate(finalDate);
        assertThat(savingsAccount.getFinaldate()).isEqualTo(finalDate);
    }


    @Test
    public void testeGetDaysLeft() throws Exception {
        // SETUP
        String id = "123";
        String name = "name";
        final double goalBalance = 123.45;
        final double currentBalance = 234.567;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sdf.parse("15/01/2017");
        SavingsAccount savingsAccount = new SavingsAccountMocked(id, name, goalBalance, currentBalance, d);


        //Act & Assert
        final int daysleft = 5;
        assertThat(savingsAccount.daysLeft()).isEqualTo(daysleft);
    }


}

class SavingsAccountMocked extends SavingsAccount {

    public SavingsAccountMocked(String id, String name, double goalbalance, double currentbalance, Date finaldate) {
        super(id, name, goalbalance, currentbalance, finaldate, new Date());
    }

    @Override
    protected Date today() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sdf.parse("10/01/2017");
        } catch (Exception e) {

        }
        return null;
    }
}
