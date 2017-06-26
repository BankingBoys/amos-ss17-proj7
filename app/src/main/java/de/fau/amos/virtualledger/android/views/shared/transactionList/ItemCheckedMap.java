package de.fau.amos.virtualledger.android.views.shared.transactionList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter.Filter;

/**
 * Created by sebastian on 17.06.17.
 */

public class ItemCheckedMap implements Filter<Transaction> {
    private Map<String, Boolean> map;

    public ItemCheckedMap(HashMap<String, Boolean> map) {
        this.map = map;
    }

    public boolean hasItemsChecked() {
        Boolean ret = false;
        Iterator iterator = this.map.entrySet().iterator();
        while (iterator.hasNext() && !ret) {
            Map.Entry entry = (Map.Entry) iterator.next();
            ret = (Boolean) entry.getValue();
        }
        return ret;
    }

    public boolean shouldBePresented(String bankAccountID) {
        return this.map.containsKey(bankAccountID) && this.map.get(bankAccountID).booleanValue();
    }

    public void update(Map<String, Boolean> map) {
        this.map = map;
    }


    @Override
    public boolean shouldBeRemoved(Transaction t) {
        if (this.map.isEmpty()) {
            return false;
        }
        boolean shouldStay = shouldBePresented(t.bankAccountID());
        return !shouldStay;
    }
}
