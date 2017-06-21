package de.fau.amos.virtualledger.dtos;

import java.util.Date;
public class SavingsAccount {

    private String id;
    private String name;
    private double goalbalance;
    private double currentbalance;
    private Date finaldate;

    public SavingsAccount() {}

    public SavingsAccount(String id, String name, double goalbalance, double currentbalance, Date finaldate) {
        this.id = id;
        this.name = name;
        this.goalbalance = goalbalance;
        this.currentbalance = currentbalance;
        this.finaldate = finaldate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGoalbalance() {
        return goalbalance;
    }

    public double getCurrentbalance() {
        return currentbalance;
    }

    public Date getFinaldate() {
        return finaldate;
    }
}
