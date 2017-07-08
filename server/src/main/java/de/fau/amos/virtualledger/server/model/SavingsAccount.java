package de.fau.amos.virtualledger.server.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "savings_accounts")
public class SavingsAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double goalbalance;
    private double currentbalance;
    private Date finaldate;

    public SavingsAccount() {
    }

    public SavingsAccount(int id, String name, double goalbalance, double currentbalance, Date finaldate) {
        this.setId(id);
        this.setName(name);
        this.setGoalbalance(goalbalance);
        this.setCurrentbalance(currentbalance);
        this.setFinaldate(finaldate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGoalbalance() {
        return goalbalance;
    }

    public void setGoalbalance(double goalbalance) {
        this.goalbalance = goalbalance;
    }

    public double getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(double currentbalance) {
        this.currentbalance = currentbalance;
    }

    public Date getFinaldate() {
        return finaldate;
    }

    public void setFinaldate(Date finaldate) {
        this.finaldate = finaldate;
    }

}
