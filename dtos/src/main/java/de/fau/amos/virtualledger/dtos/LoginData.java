package de.fau.amos.virtualledger.dtos;

/**
 *
 */
public class LoginData {

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
     * @methodtype constructor
     */
    public LoginData() {
    }

    /**
     *
     * @methodtype constructor
     * @param email
     * @param password
     */
    public LoginData(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    @Override
    public String toString() {
        return super.toString() + "{" + this.getEmail() + "}";
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
