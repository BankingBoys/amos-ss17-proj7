package de.fau.amos.virtualledger.dtos;

public class SavingsAccountSubGoal {

    private String name;
    private Double amount;

    public SavingsAccountSubGoal() {
    }

    public SavingsAccountSubGoal(final String name, final Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }
}
