package de.fau.amos.virtualledger.android.model;

import java.util.Date;
public class SavingsAccount {

    private int id;
    private String name;
    private double goalbalance;
    private double currentbalance;
    private Date finaldate;

    public SavingsAccount() {}

    public SavingsAccount(int id, String name, double goalbalance, double currentbalance, Date finaldate) {
        this.id = id;
        this.name = name;
        this.goalbalance = goalbalance;
        this.currentbalance = currentbalance;
        this.finaldate = finaldate;
    }

    public int getId() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGoalbalance(double goalbalance) {
        this.goalbalance = goalbalance;
    }

    public void setCurrentbalance(double currentbalance) {
        this.currentbalance = currentbalance;
    }

    public void setFinaldate(Date finaldate) {
        this.finaldate = finaldate;
    }
}
