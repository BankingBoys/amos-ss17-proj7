package de.fau.amos.virtualledger.android.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public int daysLeft(){
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();

        cal1.setTime(this.finaldate);
        cal2.setTime(new Date());

        return daysBetween(cal1.getTime(), cal2.getTime());
    }

    private int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
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
