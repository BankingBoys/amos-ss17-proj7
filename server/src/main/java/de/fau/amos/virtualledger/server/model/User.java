package de.fau.amos.virtualledger.server.model;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Class that represents user information.
 */
@Entity
@Table(name = "users")
public class User {

    @javax.persistence.Id
    private String email;

    private String firstName;
    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<SavingsAccount> savingsAccounts;

    public User(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<SavingsAccount> getSavingsAccounts() {
        return savingsAccounts;
    }

    public void setSavingsAccounts(Set<SavingsAccount> savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
    }
}
