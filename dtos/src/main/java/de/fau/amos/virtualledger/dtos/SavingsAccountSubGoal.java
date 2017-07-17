package de.fau.amos.virtualledger.dtos;

public class SavingsAccountSubGoal {

    private String name;
    private String amount;

    public SavingsAccountSubGoal() {
    }

    public SavingsAccountSubGoal(final String name, final String amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }
}
