package de.fau.amos.virtualledger.server.model;


import javax.persistence.Entity;
import javax.persistence.Table;

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

}
