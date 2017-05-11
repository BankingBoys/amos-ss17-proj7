package de.fau.amos.virtualledger.android.model;

/**
 * UserCredential model class
 */
public class UserCredential {

    /**
     *
     */
    private String email;
    /**
     *
     */
    private String password;
    /**
     *
     */
    private String firstname;
    /**
     *
     */
    private String lastname;

    /**
     *
     * @param email
     * @param password
     * @param firstname
     * @param lastname
     */
    public UserCredential(String email, String password, String firstname, String lastname)
    {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @param firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     *
     * @return
     */
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
