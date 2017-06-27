package de.fau.amos.virtualledger.server.model;


import javax.persistence.*;

@Entity
@Table(name="SavingsAccountUser")
public class SavingsAccountToUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String email;
    public int id_savingsaccount;

    public SavingsAccountToUser(String email, int id_savingsaccount) {
        this.email = email;
        this.id_savingsaccount = id_savingsaccount;
    }

    public SavingsAccountToUser() {
    }
}
