package de.fau.amos.virtualledger.server.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents user credentials.
 */
@XmlRootElement
@Entity
@Table(name = "Users")
public class UserCredential {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String LAST_NAME_MUST_NOT_BE_EMPTY = "Last name must not be empty!";
	private static final String FIRST_NAME_MUST_NOT_BE_EMPTY = "First name must not be empty!";
	private static final String PASSWORD_MUST_NOT_BE_EMPTY = "Password must not be empty!";
	private String email;
    private String password;
    private String firstname;
    private String lastname;

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public UserCredential() {}

    /**
     *
     * @return
     * @methodtype getter
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        if(email == null || ! this.isEmailPatternValid(email))
        {
            throw new IllegalArgumentException("Email doesn't match the Email-Pattern");
        }
        this.email = email;
    }

    /**
     *
     * @return password
     * @methodtype getter
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * @methodtype setter
     */
    public void setPassword(String password) {

        if(password == null || ! isPasswordPatternValid(password))
        {
        	logger.warn(PASSWORD_MUST_NOT_BE_EMPTY);
            throw new IllegalArgumentException(PASSWORD_MUST_NOT_BE_EMPTY);
        }
        this.password = password;
    }

    /**
     *
     * @param firstname
     * @methodtype setter
     */
    public void setFirstname(String firstname) {

        if(firstname == null || ! this.isFirstNamePatternValid(firstname))
        {
        	logger.warn(FIRST_NAME_MUST_NOT_BE_EMPTY);
            throw new IllegalArgumentException(FIRST_NAME_MUST_NOT_BE_EMPTY);
        }
        this.firstname = firstname;
    }


    /**
     *
     * @return
     * @methodtype getter
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     *
     * @param lastname
     * @methodtype setter
     */
    public void setLastname(String lastname) {

        if(lastname == null || ! this.isLastNamePatternValid(lastname))
        {
            logger.warn(LAST_NAME_MUST_NOT_BE_EMPTY);
            throw new IllegalArgumentException(LAST_NAME_MUST_NOT_BE_EMPTY);
        }
        this.lastname = lastname;
    }

    /**
     *
     * @return lastname
     * @methodtype getter
     */
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * @return id
     * @methodtype getter
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * @methodtype setter
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * validates the pattern of an email address
     * @param email
     * @return boolean
     * @methodtype assertion
     */
    private boolean isEmailPatternValid(String email)
    {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * validates the pattern of a password
     * @param password
     * @return boolean
     * @methodtype assertion
     */
    private boolean isPasswordPatternValid(String password)
    {
        return ! password.isEmpty();
    }

    /**
     *
     * @param firstName
     * @return boolean
     * @methodtype assertion
     */
    private boolean isFirstNamePatternValid(String firstName)
    {
        return ! firstName.isEmpty();
    }

    /**
     *
     * @param lastName
     * @return boolean
     * @methodtype assertion
     */
    private boolean isLastNamePatternValid(String lastName)
    {
        return ! lastName.isEmpty();
    }
    
    @Override
    public String toString() {
    	return super.toString()+"{"+this.getFirstname()+", "+this.getLastname()+"}";
    }
}
