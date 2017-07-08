package de.fau.amos.virtualledger.dtos;

import java.util.Date;

public class AddSavingsAccountData {
    private String name;
    private double goalBalance;
    private Date finalDate;
    private Date goalFinishedDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGoalBalance() {
        return goalBalance;
    }

    public void setGoalBalance(double goalBalance) {
        this.goalBalance = goalBalance;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public Date getGoalFinishedDate() {
        return this.goalFinishedDate;
    }

    public void setGoalFinishedDate(Date newGoalFinishedDate) {
        this.goalFinishedDate = newGoalFinishedDate;
    }

}
