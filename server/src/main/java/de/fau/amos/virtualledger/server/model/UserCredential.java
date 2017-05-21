package de.fau.amos.virtualledger.server.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.istack.logging.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents user credentials.
 */
@XmlRootElement
@Entity
@Table(name = "Users")
public class UserCredential {

    private static final String LAST_NAME_MUST_NOT_BE_EMPTY = "Last name must not be empty!";
	private static final String FIRST_NAME_MUST_NOT_BE_EMPTY = "First name must not be empty!";
	private static final String PASSWORD_MUST_NOT_BE_EMPTY = "Password must not be empty!";
	private String email;
    private String password;
    private String firstName;
    private String lastName;

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

    @XmlElement(name="email")
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
    @XmlElement(name="password")
    public void setPassword(String password) {

        if(password == null || ! isPasswordPatternValid(password))
        {
        	logger().warning(PASSWORD_MUST_NOT_BE_EMPTY);
            throw new IllegalArgumentException(PASSWORD_MUST_NOT_BE_EMPTY);
        }
        this.password = password;
    }

    /**
     *
     * @param firstName
     * @methodtype setter
     */
    @XmlElement(name="firstname")
    public void setFirstName(String firstName) {

        if(firstName == null || ! this.isFirstNamePatternValid(firstName))
        {
        	logger().warning(FIRST_NAME_MUST_NOT_BE_EMPTY);
            throw new IllegalArgumentException(FIRST_NAME_MUST_NOT_BE_EMPTY);
        }
        this.firstName = firstName;
    }

	private Logger logger() {
		return Logger.getLogger(UserCredential.class);
	}

    /**
     *
     * @return
     * @methodtype getter
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param lastName
     * @methodtype setter
     */
    @XmlElement(name="lastname")
    public void setLastName(String lastName) {

        if(lastName == null || ! this.isLastNamePatternValid(lastName))
        {
        	logger().warning(LAST_NAME_MUST_NOT_BE_EMPTY);
            throw new IllegalArgumentException(LAST_NAME_MUST_NOT_BE_EMPTY);
        }
        this.lastName = lastName;
    }

    /**
     *
     * @return lastName
     * @methodtype getter
     */
    public String getLastName() {
        return lastName;
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
    	return super.toString()+"{"+this.getFirstName()+", "+this.getLastName()+"}";
    }
}
