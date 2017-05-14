package de.fau.amos.virtualledger.dtos;

/**
 * Created by Simon on 14.05.2017.
 */

public class LogoutApiModel {
    private String email;

    /**
     *
     * @param email
     * @methodtype constructor
     */
    public LogoutApiModel(String email) {
        this.email = email;
    }

    /**
     *
     * @return email
     * @methodtype getter
     */
    public String getemail() {
        return email;
    }

    /**
     *
     * @param email
     * @methodtype setter
     */
    public void setemail(String email) {
        this.email = email;
    }
}
