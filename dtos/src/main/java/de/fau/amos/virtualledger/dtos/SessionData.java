package de.fau.amos.virtualledger.dtos;

import java.io.Serializable;

/**
 *
 */
public class SessionData implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
     * @param email
     * @param token
     */
    public SessionData(String email, String token)
    {
        this.email = email;
        this.sessionid = token;
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
