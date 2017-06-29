package de.fau.amos.virtualledger.android.views.savings;

import java.util.Comparator;

import de.fau.amos.virtualledger.android.model.SavingsAccount;

/**
 * Created by sebastian on 29.06.17.
 */

public class SavingsComparator implements Comparator<SavingsAccount>{
    @Override
    public int compare(SavingsAccount t1, SavingsAccount t2) {
        return t1.getFinaldate().compareTo(t2.getFinaldate());
    }
}
