package de.fau.amos.virtualledger.android.views.calendar;

import de.fau.amos.virtualledger.android.views.shared.transactionList.BankTransactionSupplier;
import de.fau.amos.virtualledger.android.views.shared.transactionList.Transaction;

public class BankingDateInformation {

    private double amount;
    private BankTransactionSupplier transactionSupplier;

    public BankingDateInformation(double amount, BankTransactionSupplier transactionSupplier) {
        this.amount = amount;
        this.transactionSupplier = transactionSupplier;
    }

    public double getAmount() {
        return amount;
    }

    public double getAmountDelta() {
        double amountDelta = 0.0;
        for (Transaction transaction : this.transactionSupplier.getAllTransactions()) {
            amountDelta += transaction.booking().getAmount();
        }
        return amountDelta;
    }

    public BankTransactionSupplier getTransactionSuppllier() {
        return this.transactionSupplier;
    }
}
