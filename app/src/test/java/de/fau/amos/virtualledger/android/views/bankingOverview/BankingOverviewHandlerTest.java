package de.fau.amos.virtualledger.android.views.bankingOverview;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.BankingOverviewHandler;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Fragment.ExpandableBankFragment;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by Simon on 13.06.2017.
 */

public class BankingOverviewHandlerTest {


    @Test
    public void setAllAccountsCheckedTest() {
        BankingOverviewHandler component_under_test = BankingOverviewHandler.getInstance();
        HashMap<String, Boolean> map = new HashMap<>();
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";
        String test4 = "test4";
        map.put(test1, false);
        map.put(test2, false);
        map.put(test3, false);
        map.put(test4, false);
        component_under_test.setAllAccountsCheckedOrUnchecked(map, true);
        Assert.assertEquals(map.get("test1"), true);
        Assert.assertEquals(map.get("test2"), true);
        Assert.assertEquals(map.get("test3"), true);
        Assert.assertEquals(map.get("test4"), true);
    }

    @Test
    public void setAllAccountsUnCheckedTest() {
        BankingOverviewHandler component_under_test = BankingOverviewHandler.getInstance();
        HashMap<String, Boolean> map = new HashMap<>();
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";
        String test4 = "test4";
        map.put(test1, true);
        map.put(test2, true);
        map.put(test3, true);
        map.put(test4, true);
        component_under_test.setAllAccountsCheckedOrUnchecked(map,false);
        Assert.assertEquals(map.get("test1"), false);
        Assert.assertEquals(map.get("test2"), false);
        Assert.assertEquals(map.get("test3"), false);
        Assert.assertEquals(map.get("test4"), false);
    }

    @Test
    public void sortAccessesTest() {
        List<BankAccess> bankAccesses = new ArrayList<>();
        BankAccess bankAccess1 = new BankAccess("1","a","1","1");
        BankAccess bankAccess2 = new BankAccess("2","b","2","2");
        BankAccess bankAccess3 = new BankAccess("3","c","3","3");

        bankAccesses.add(bankAccess3);
        bankAccesses.add(bankAccess1);
        bankAccesses.add(bankAccess2);

        BankingOverviewHandler handler = BankingOverviewHandler.getInstance();
        bankAccesses = handler.sortAccesses(bankAccesses);

        Assert.assertEquals(bankAccess1, bankAccesses.get(0));
        Assert.assertEquals(bankAccess2, bankAccesses.get(1));
        Assert.assertEquals(bankAccess3, bankAccesses.get(2));

    }

    @Test
    public void sortAccountTest() {
        List<BankAccount> bankAccounts = new ArrayList<>();
        BankAccount bankAccount1 = new BankAccount("1", "a", 0);
        BankAccount bankAccount2 = new BankAccount("2", "b", 0);
        BankAccount bankAccount3 = new BankAccount("3", "c", 0);

        bankAccounts.add(bankAccount2);
        bankAccounts.add(bankAccount1);
        bankAccounts.add(bankAccount3);

        BankingOverviewHandler handler = BankingOverviewHandler.getInstance();
        handler.sortAccounts(bankAccounts);

        Assert.assertEquals(bankAccount1, bankAccounts.get(0));
        Assert.assertEquals(bankAccount2, bankAccounts.get(1));
        Assert.assertEquals(bankAccount3, bankAccounts.get(2));
    }

    @Test
    public void hasItemsCheckedFalseTest() {
        HashMap<String, Boolean> testMap = new HashMap<>();
        testMap.put("1", false);
        testMap.put("2", false);
        testMap.put("3", false);
        testMap.put("4", false);
        testMap.put("5", false);
        Assert.assertEquals(false, BankingOverviewHandler.hasItemsChecked(testMap));
    }

    @Test
    public void hasItemsCheckedTrueTest() {
        HashMap<String, Boolean> testMap = new HashMap<>();
        testMap.put("1", true);
        testMap.put("2", false);
        testMap.put("3", false);
        testMap.put("4", false);
        testMap.put("5", false);
        Assert.assertEquals(true, BankingOverviewHandler.hasItemsChecked(testMap));
    }

}
