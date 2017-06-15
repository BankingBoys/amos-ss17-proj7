package de.fau.amos.virtualledger.android.views.calendar;

/**
 * Created by Georg on 15.06.2017.
 */

public class BankingDateInformation {

    private double amount;
    private double amountDelta;

    public BankingDateInformation(double amount, double amountDelta) {
        this.amount = amount;
        this.amountDelta = amountDelta;
    }

    public double getAmount() {
        return amount;
    }

    public double getAmountDelta() {
        return amountDelta;
    }
}
