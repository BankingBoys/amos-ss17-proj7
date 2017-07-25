package dtos;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccountIdentifier;
import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.dtos.SavingsAccount;
import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;

import static org.assertj.core.api.Assertions.assertThat;

public class SavingsAccountTest {

    @Test
    public void constructorGettersMatch() {
        // SETUP
        final Integer id = 123;
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
    public void setterAndGetterTest() {
        final Integer id = 12345;
        String name = "name2";
        final double goalBalance = 123.4555;
        final double currentBalance = 234.56755;
        Date finalDate = new Date();
        String name2 = "name3";
        final double goalBalance2 = 123.4554445;
        final double currentBalance2 = 234.56754445;
        final long dateTime = 123123;
        Date finalDate2 = new Date(dateTime);
        Date finalGoalFinishedDate = new Date();


        SavingsAccount savingsAccount = new SavingsAccount();
        SavingsAccount savingsAccount2 = new SavingsAccount(name2, goalBalance2, currentBalance2, finalDate2, finalGoalFinishedDate);

        savingsAccount.setId(id);
        assertThat(savingsAccount.getId()).isEqualTo(id);

        savingsAccount.setName(name);
        assertThat(savingsAccount.getName()).isEqualTo(name);
        assertThat(savingsAccount2.getName()).isEqualTo(name2);

        savingsAccount.setGoalbalance(goalBalance);
        assertThat(savingsAccount.getGoalbalance()).isEqualTo(goalBalance);
        assertThat(savingsAccount2.getGoalbalance()).isEqualTo(goalBalance2);

        savingsAccount.setCurrentbalance(currentBalance);
        assertThat(savingsAccount.getCurrentbalance()).isEqualTo(currentBalance);
        assertThat(savingsAccount2.getCurrentbalance()).isEqualTo(currentBalance2);

        savingsAccount.setFinaldate(finalDate);
        assertThat(savingsAccount.getFinaldate()).isEqualTo(finalDate);
        assertThat(savingsAccount2.getFinaldate()).isEqualTo(finalDate2);

        assertThat(savingsAccount2.getFinalGoalFinishedDate()).isEqualTo(finalGoalFinishedDate);
    }

    @Test
    public void testListSetterAndGetter() {
        //Setup
        SavingsAccount savingsAccount = new SavingsAccount();
        final String email = "1";
        final String email2 = "2";
        final String subgoalName = "name1";
        final double subgoalAmount = 123;
        final String subgoalName2 = "name2";
        final double subgoalAmount2 = 1234;
        final String accessID = "testAccess";
        final String accountID = "testAccount";
        final String accessID2 = "testAccess2";
        final String accountID2 = "testAccount2";
        Contact contact = new Contact(email);
        Contact contact1 = new Contact(email2);
        BankAccountIdentifier identifier = new BankAccountIdentifier(accessID, accountID);
        BankAccountIdentifier identifier2 = new BankAccountIdentifier(accessID2, accountID2);
        List<BankAccountIdentifier> bankAccountIdentifiers = new ArrayList<>();
        bankAccountIdentifiers.add(identifier);
        bankAccountIdentifiers.add(identifier2);
        SavingsAccountSubGoal subGoal = new SavingsAccountSubGoal(subgoalName, subgoalAmount);
        SavingsAccountSubGoal subGoal2 = new SavingsAccountSubGoal(subgoalName2, subgoalAmount2);
        List<SavingsAccountSubGoal> savingsAccountSubGoals = new ArrayList<>();
        savingsAccountSubGoals.add(subGoal);
        savingsAccountSubGoals.add(subGoal2);
        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact);
        contactList.add(contact1);

        //Act
        savingsAccount.setAssignedBankAccounts(bankAccountIdentifiers);
        savingsAccount.setSubGoals(savingsAccountSubGoals);
        savingsAccount.setAdditionalAssignedUsers(contactList);

        //Assert
        assertThat(savingsAccount.getAssignedBankAccounts()).isEqualTo(bankAccountIdentifiers);
        assertThat(savingsAccount.getSubGoals()).isEqualTo(savingsAccountSubGoals);
        assertThat(savingsAccount.getAdditionalAssignedUsers()).isEqualTo(contactList);

    }

    @Test
    public void testeGetDaysLeft() throws Exception {
        // SETUP
        final Integer id = 123;
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

    public SavingsAccountMocked(Integer id, String name, double goalbalance, double currentbalance, Date finaldate) {
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
