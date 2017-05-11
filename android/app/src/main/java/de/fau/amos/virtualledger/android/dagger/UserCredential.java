package de.fau.amos.virtualledger.android.dagger;

/**
 * Created by Georg on 10.05.2017.
 */

public class UserCredential {

    private String email;
    private String password;
    private String firstname;
    private String lastname;

    public UserCredential(String email, String password, String firstname, String lastname)
    {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
