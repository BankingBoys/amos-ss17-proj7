package dtos;

import org.junit.Test;
import org.junit.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by Simon on 30.05.2017.
 */

public class BankAccessTest {

    private List<BankAccount> testAccountList;
    private BankAccount account1;
    private BankAccount account2;
    private BankAccess testAccess;
    private double accountBalance1 = 100;
    private double accountBalance2 = 300;

    /**
     *
     */
    @Before
    public void setUp() {
        account1 = new BankAccount("123", "test1", accountBalance1);
        account2 = new BankAccount("456", "test2", accountBalance2);
        testAccountList = new ArrayList<>();
        testAccountList.add(account1);
        testAccountList.add(account2);
        testAccess = new BankAccess("123","defName", "1231312", "123", testAccountList);
    }

    /**
     *
     */
    @Test
    public void constructorTest() {
        Assert.assertNotNull(testAccess);
    }

    /**
     *
     */
    @Test
    public void setAndGetIdTest() {
        String testId = "testID123";
        testAccess.setId(testId);
        Assert.assertEquals(testId, testAccess.getId());
    }

    /**
     *
     */
    @Test
    public void getAndSetNameTest() {
        String testName = "testName123";
        testAccess.setName(testName);
        Assert.assertEquals(testName, testAccess.getName());
    }

    /**
     *
     */
    @Test
    public void getBalanceTest() {
        Assert.assertEquals(accountBalance1+accountBalance2, testAccess.getBalance(), 0);
    }

    /**
     *
     */
    @Test
    public void setAndGetBankAccountsTest() {
        List<BankAccount> test = new ArrayList<BankAccount>();
        test.add(new BankAccount("test1234","testaccount1", 0));
        test.add(new BankAccount("test456","testaccount2", 0));
        testAccess.setBankaccounts(test);
        Assert.assertEquals(test, testAccess.getBankaccounts());
    }

    /**
     *
     */
    @Test
    public void setAndGetBankCodeTest() {
        String testBankCode = "testBankCode";
        testAccess.setBankcode(testBankCode);
        Assert.assertEquals(testBankCode, testAccess.getBankcode());
    }

    /**
     *
     */
    @Test
    public void setAndGetBankLoginTest() {
        String testBankLogin = "testBankLogin";
        testAccess.setBanklogin(testBankLogin);
        Assert.assertEquals(testBankLogin, testAccess.getBanklogin());
    }

    /**
     *
     */
    @Test
    public void comparatorTest() {
        BankAccess testAccess2 = new BankAccess("456","abcName", "12", "56", testAccountList);
        BankAccess testAccess3 = new BankAccess("789","jklName", "34", "12", testAccountList);
        BankAccess testAccess4 = new BankAccess("101112","ghiName", "56", "62", testAccountList);

        List<BankAccess> listAccesses = new ArrayList<>();
        listAccesses.add(testAccess);
        listAccesses.add(testAccess3);
        listAccesses.add(testAccess2);
        listAccesses.add(testAccess4);

        Collections.sort(listAccesses, BankAccess.sortBankAccessByName);

        Assert.assertEquals(testAccess2, listAccesses.get(0));
        Assert.assertEquals(testAccess, listAccesses.get(1));
        Assert.assertEquals(testAccess4, listAccesses.get(2));
        Assert.assertEquals(testAccess3, listAccesses.get(3));
    }

}
