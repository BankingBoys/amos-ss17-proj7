package de.fau.amos.virtualledger.server.model;


import javax.persistence.*;

@Entity
@Table(name="SavingsAccountUser")
public class SavingsAccountToUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private int idSavingsaccount;

    public SavingsAccountToUser(String email, int idSavingsaccount) {
        this.setEmail(email);
        this.setId_savingsaccount(idSavingsaccount);
    }

    public SavingsAccountToUser() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_savingsaccount() {
        return this.idSavingsaccount;
    }

    public void setId_savingsaccount(int id_savingsaccount) {
        this.idSavingsaccount = id_savingsaccount;
    }
}
