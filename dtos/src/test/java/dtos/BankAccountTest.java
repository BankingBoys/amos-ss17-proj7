package dtos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.dtos.BankAccountComparator;

/**
 * Created by Simon on 30.05.2017.
 */

public class BankAccountTest {

    private List<BankAccount> testAccountList;
    private BankAccount account1;
    private BankAccount account2;
    private BankAccount account3;
    private BankAccount account4;
    private static final double ACCOUNT_BALANCE_1 = 100;

    /**
     *
     */
    @Before
    public void setUp() {
        account1 = new BankAccount("123", "ghiTest", ACCOUNT_BALANCE_1);
        testAccountList = new ArrayList<>();
    }

    /**
     *
     */
    @Test
    public void constructorTest() {
        Assert.assertNotNull(account1);
    }

    /**
     *
     */
    @Test
    public void setAndGetBankIdTest() {
        String test = "testId";
        account1.setBankid(test);
        Assert.assertEquals(test, account1.getBankid());
    }

    /**
     *
     */
    @Test
    public void setAndGetBankNameTest() {
        String testName = "testName";
        account1.setName(testName);
        Assert.assertEquals(testName, account1.getName());
    }

    /**
     *
     */
    @Test
    public void setAndGetBankBalanceTest() {
        final double testBalance = 100123;
        account1.setBalance(testBalance);
        Assert.assertEquals(testBalance, account1.getBalance(), 0);
    }

    /**
     *
     */
    @Test
    public void comparatorTest() {
        final double testAmount = 0;
        account2 = new BankAccount("456", "abcTest", testAmount);
        account3 = new BankAccount("789", "jklTest", testAmount);
        account4 = new BankAccount("123", "defTest", testAmount);

        testAccountList.add(account1);
        testAccountList.add(account2);
        testAccountList.add(account3);
        testAccountList.add(account4);

        Collections.sort(testAccountList, new BankAccountComparator());

        final int lastPosition = 3;
        final int firstPosition = 0;
        final int secondPosition = 1;
        final int thirdPosition = 2;

        Assert.assertEquals(account2, testAccountList.get(firstPosition));
        Assert.assertEquals(account4, testAccountList.get(secondPosition));
        Assert.assertEquals(account1, testAccountList.get(thirdPosition));
        Assert.assertEquals(account3, testAccountList.get(lastPosition));
    }


}
