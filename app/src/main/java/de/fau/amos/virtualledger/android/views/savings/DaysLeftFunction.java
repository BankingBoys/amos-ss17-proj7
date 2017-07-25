package de.fau.amos.virtualledger.android.views.savings;

import de.fau.amos.virtualledger.dtos.SavingsAccount;

/**
 * Created by sebastian on 20.07.17.
 */

public class DaysLeftFunction {


    public String apply(SavingsAccount account) {
        int daysLeft = account.daysLeft();
        if (daysLeft > 1) {
            return daysLeft + " days left";
        } else if (daysLeft == 1) {
            return "one day left";
        } else {
            return "done";
        }
    }
}
