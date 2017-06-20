package de.fau.amos.virtualledger.android.views.bankingOverview.expandableList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by Simon on 15.06.2017.
 */

public class BankingOverviewHandler {

    private static BankingOverviewHandler bankingOverview = null;

    private BankingOverviewHandler() {}

    public static synchronized BankingOverviewHandler getInstance() {
        if(bankingOverview == null) {
            bankingOverview = new BankingOverviewHandler();
        }
        return bankingOverview;
    }

    /**
     *
     */
    public List<BankAccess> sortAccesses(List<BankAccess> bankAccessList) {
        Collections.sort(bankAccessList, BankAccess.sortBankAccessByName);
        return bankAccessList;
    }

    /**
     *
     */
    public List<BankAccount> sortAccounts(List<BankAccount> accounts) {
        Collections.sort(accounts, BankAccount.sortBankAccountByName);
        return accounts;
    }

    /**
     *
     */
    public HashMap<String, Boolean> setAllAccountsCheckedOrUnchecked(HashMap<String, Boolean> mappingCheckBoxes, boolean checked) {
        Iterator iterator = mappingCheckBoxes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            entry.setValue(checked);
        }
        return mappingCheckBoxes;
    }

    /**
     *
     */
    public static boolean hasItemsChecked(HashMap<String, Boolean> map) {
        Boolean ret = false;
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext() && !ret) {
            Map.Entry entry = (Map.Entry) iterator.next();
            ret = (Boolean) entry.getValue();
        }
        return ret;
    }
}
