package de.fau.amos.virtualledger.dtos;

/**
 *
 */
public class SessionData {
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String sessionid;

    /**
     *
     */
    public SessionData() { }

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
    public String getSessionid() {
        return sessionid;
    }

    /**
     *
     * @param sessionid
     */
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
