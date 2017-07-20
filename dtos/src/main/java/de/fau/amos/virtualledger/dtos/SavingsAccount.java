package de.fau.amos.virtualledger.dtos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SavingsAccount {

    private String id;
    private String name;
    private double goalbalance;
    private double currentbalance;
    private Date finaldate;
    private Date finalGoalFinishedDate;
    private List<Contact> additionalAssignedUsers = new ArrayList<>();
    private List<SavingsAccountSubGoal> subGoals = new ArrayList<>();
    private List<BankAccountIdentifier> assignedBankAccounts = new ArrayList<>();

    public SavingsAccount() {
    }

    public SavingsAccount(String name, double goalbalance, double currentbalance, Date finaldate, Date finalGoalFinishedDate) {
        this.name = name;
        this.goalbalance = goalbalance;
        this.currentbalance = currentbalance;
        this.finaldate = finaldate;
        this.finalGoalFinishedDate = finalGoalFinishedDate;
    }

    public SavingsAccount(String id, String name, double goalbalance, double currentbalance, Date finaldate, Date finalGoalFinishedDate) {
        this.id = id;
        this.name = name;
        this.goalbalance = goalbalance;
        this.currentbalance = currentbalance;
        this.finaldate = finaldate;
        this.finalGoalFinishedDate = finalGoalFinishedDate;
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

    public Date getFinalGoalFinishedDate() {
        return this.finalGoalFinishedDate;
    }

    public void setFinalGoalFinishedDate(Date newGoalFinishedDate) {
        this.finalGoalFinishedDate = newGoalFinishedDate;
    }

    public int daysLeft() {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        cal1.setTime(this.finaldate);
        cal2.setTime(this.today());

        return daysBetween(cal1.getTime(), cal2.getTime());
    }

    private int daysBetween(Date d1, Date d2) {
        final int thousand = 1000;
        final int min = 60;
        final int hrs = 24;
        final int sec = 60;
        return (int) ((d1.getTime() - d2.getTime()) / (thousand * sec * min * hrs));
    }


    protected Date today() {
        return new Date();
    }

    public double getCurrentbalance() {
        return currentbalance;
    }

    public Date getFinaldate() {
        return finaldate;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return "SavingsAccount[" + super.hashCode() + "]:{name="
                + this.getName() + ", amount=" + this.getGoalbalance() + "}";
    }

    public void setAdditionalAssignedUsers(Collection<Contact> contacts) {
        this.additionalAssignedUsers.clear();
        this.additionalAssignedUsers.addAll(contacts);
    }

    public List<Contact> getAdditionalAssignedUsers() {
        return this.additionalAssignedUsers;
    }


    public List<SavingsAccountSubGoal> getSubGoals() {
        return subGoals;
    }

    public void setSubGoals(final List<SavingsAccountSubGoal> subGoals) {
        this.subGoals.clear();
        this.subGoals.addAll(subGoals);
    }

    public void setAssignedBankAccounts(Collection<BankAccountIdentifier> bankAccounts) {
        this.assignedBankAccounts.clear();
        this.assignedBankAccounts.addAll(bankAccounts);
    }

    public List<BankAccountIdentifier> getAssignedBankAccounts() {
        return this.assignedBankAccounts;
    }
}
