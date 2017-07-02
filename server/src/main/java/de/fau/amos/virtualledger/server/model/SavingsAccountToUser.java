package de.fau.amos.virtualledger.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SavingsAccountUser")
public class SavingsAccountToUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private int idSavingsaccount;

    public SavingsAccountToUser(String email, int idSavingsaccount) {
        this.setEmail(email);
        this.setIdSavingsaccount(idSavingsaccount);
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

    public int getIdSavingsaccount() {
        return this.idSavingsaccount;
    }

    public void setIdSavingsaccount(int idSavingsaccount) {
        this.idSavingsaccount = idSavingsaccount;
    }
}
