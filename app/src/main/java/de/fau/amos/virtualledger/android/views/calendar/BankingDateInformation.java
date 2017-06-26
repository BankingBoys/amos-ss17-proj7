package de.fau.amos.virtualledger.android.views.calendar;

import de.fau.amos.virtualledger.android.views.shared.transactionList.Supplier;
import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;

public class BankingDateInformation {

    private double amount;
    private Supplier<Transaction> transactionSupplier;

    public BankingDateInformation(double amount, Supplier<Transaction> transactionSupplier) {
        this.amount = amount;
        this.transactionSupplier = transactionSupplier;
    }

    public double getAmount() {
        return amount;
    }

    public double getAmountDelta() {
        double amountDelta = 0.0;
        for (Transaction transaction : this.transactionSupplier.getAll()) {
            amountDelta += transaction.booking().getAmount();
        }
        return amountDelta;
    }

    public Supplier<Transaction> getTransactionSuppllier() {
        return this.transactionSupplier;
    }
}
