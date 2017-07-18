package de.fau.amos.virtualledger.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Simon on 16.07.2017.
 */
@Entity
@Table(name = "savings_account_sub_goals")
public class SavingsAccountSubGoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double amount;

    public SavingsAccountSubGoalEntity(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public SavingsAccountSubGoalEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
