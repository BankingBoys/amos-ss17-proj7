package de.fau.amos.virtualledger.server.model;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "savings_accounts")
public class SavingsAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double goalbalance;
    private double currentbalance;
    private Date finaldate;

    @OneToMany
    @Cascade(CascadeType.ALL)
    private Set<SingleGoal> singleGoals = new HashSet<>();

    @ManyToMany
    @Cascade(CascadeType.ALL)
    private Set<SavingsAccountUserRelation> userRelations = new HashSet<>();

    public SavingsAccountEntity() {
    }

    public SavingsAccountEntity(int id, String name, double goalbalance, double currentbalance, Date finaldate, Set<SingleGoal> singleGoals) {
        this.setId(id);
        this.setName(name);
        this.setGoalbalance(goalbalance);
        this.setCurrentbalance(currentbalance);
        this.setFinaldate(finaldate);
        this.setSingleGoals(singleGoals);
    }

    public SavingsAccountEntity(String name, double goalbalance, double currentbalance, Date finaldate, Set<SavingsAccountUserRelation> userRelations) {
        this.name = name;
        this.goalbalance = goalbalance;
        this.currentbalance = currentbalance;
        this.finaldate = finaldate;
        this.userRelations = userRelations;
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

    public Set<SavingsAccountUserRelation> getUserRelations() {
        return userRelations;
    }

    public void setUserRelations(Set<SavingsAccountUserRelation> userRelations) {
        this.userRelations = userRelations;
    }

    public Set<SingleGoal> getSingleGoals() {
        return singleGoals;
    }

    public void setSingleGoals(Set<SingleGoal> singleGoals) {
        this.singleGoals = singleGoals;
    }
}
