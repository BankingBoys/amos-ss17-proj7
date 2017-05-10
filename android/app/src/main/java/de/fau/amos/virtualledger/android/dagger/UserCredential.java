package de.fau.amos.virtualledger.android.dagger;

/**
 * Created by Georg on 10.05.2017.
 */

public class UserCredential {

    private String email;
    private String password;

    public UserCredential(String email, String password)
    {
        this.email = email;
        this.password = password;
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

}
