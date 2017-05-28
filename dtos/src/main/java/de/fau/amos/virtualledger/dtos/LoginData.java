package de.fau.amos.virtualledger.dtos;

/**
 *
 */
public class LoginData {

    /**
     *
     */
    public String email;
    /**
     *
     */
    public String password;

    /**
     *
     * @methodtype constructor
     */
    public LoginData() {}

    /**
     *
     * @methodtype constructor
     * @param email
     * @param password
     */
    public LoginData(String email, String password){
        this.email = email;
        this.password = password;
	}
    
    @Override
    public String toString() {
    	return super.toString()+"{"+this.email+"}";
    }

}
