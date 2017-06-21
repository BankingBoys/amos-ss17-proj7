package de.fau.amos.virtualledger.dtos;

import java.util.Date;

/**
 * Created by Georg on 21.06.2017.
 */

public class SavingsAccount {

    private String id;
    private String name;
    private double goalBalance;
    private double currentBalance;
    private Date finalDate;

    public SavingsAccount(String id, String name, double goalBalance, double currentBalance, Date finalDate) {
        this.id = id;
        this.name = name;
        this.goalBalance = goalBalance;
        this.currentBalance = currentBalance;
        this.finalDate = finalDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGoalBalance() {
        return goalBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public Date getFinalDate() {
        return finalDate;
    }
}
