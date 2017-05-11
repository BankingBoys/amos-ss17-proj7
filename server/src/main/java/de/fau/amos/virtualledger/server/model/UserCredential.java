package de.fau.amos.virtualledger.server.model;


import jdk.nashorn.internal.objects.annotations.Setter;
import org.eclipse.persistence.annotations.PrimaryKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Georg on 07.05.2017.
 */
@XmlRootElement
@Entity
@Table(name = "Users")
public class UserCredential {

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
            throw new IllegalArgumentException("Password must not be empty!");
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
            throw new IllegalArgumentException("First name must not be empty!");
        }
        this.firstName = firstName;
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
            throw new IllegalArgumentException("Last name must not be empty!");
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
     *
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
     *
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
}
