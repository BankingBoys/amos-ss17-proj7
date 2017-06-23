package de.fau.amos.virtualledger.server.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="SavingsAccounts")
public class SavingsAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String name;
    public double goalbalance;
    public double currentbalance;
    public Date finaldate;

    public SavingsAccount() {}

    public SavingsAccount(int id, String name, double goalbalance, double currentbalance, Date finaldate) {
        this.id = id;
        this.name = name;
        this.goalbalance = goalbalance;
        this.currentbalance = currentbalance;
        this.finaldate = finaldate;
    }
}
