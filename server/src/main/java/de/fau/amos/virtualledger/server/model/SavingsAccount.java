package de.fau.amos.virtualledger.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany
    private Set<User> users = new HashSet<>();

    public SavingsAccount() {
    }

    public SavingsAccount(int id, String name, double goalbalance, double currentbalance, Date finaldate) {
        this.setId(id);
        this.setName(name);
        this.setGoalbalance(goalbalance);
        this.setCurrentbalance(currentbalance);
        this.setFinaldate(finaldate);
    }

    public SavingsAccount(String name, double goalbalance, double currentbalance, Date finaldate, Set<User> users) {
        this.name = name;
        this.goalbalance = goalbalance;
        this.currentbalance = currentbalance;
        this.finaldate = finaldate;
        this.users = users;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
