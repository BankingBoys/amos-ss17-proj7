package de.fau.amos.virtualledger.android.views.calendar;

import java.util.List;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;

public class BankingDateInformation {

    private double amount;
    private List<Transaction> transactionList;

    public BankingDateInformation(double amount, List<Transaction> transactionList) {
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

    public List<Transaction> getTransactions() {
        return transactionList;
    }
}
