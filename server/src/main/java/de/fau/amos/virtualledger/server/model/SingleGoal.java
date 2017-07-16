package de.fau.amos.virtualledger.server.model;

/**
 * Created by Simon on 16.07.2017.
 */
public class SingleGoal {

    private String name;
    private double balance;

    public SingleGoal(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public SingleGoal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
