package de.fau.amos.virtualledger.android.views.calendar;

import java.util.List;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;
import hirondelle.date4j.DateTime;

public class BankingDateInformation {

    private DateTime dateTime;
    private double amount;
    private List<Transaction> transactionList;

    public BankingDateInformation(DateTime dateTime, double amount, List<Transaction> transactionList) {
        this.dateTime = dateTime;
        this.amount = amount;
        this.transactionList = transactionList;
    }

    public double getAmount() {
        return amount;
    }

    public double getAmountDelta() {
        double amountDelta = 0.0;
        for (Transaction transaction : this.transactionList) {
            amountDelta += transaction.booking().getAmount();
        }
        return amountDelta;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<Transaction> getTransactions() {
        return transactionList;
    }
}
