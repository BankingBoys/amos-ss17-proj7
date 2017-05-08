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

    public String getPassword() {
        return password;
    }

    @XmlElement(name="password")
    public void setPassword(String password) {

        if(password == null || ! isPasswordPatternValid(password))
        {
            throw new IllegalArgumentException("Password must not be empty!");
        }
        this.password = password;
    }

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String email;
    private String password;

    public UserCredential() {}

    private boolean isEmailPatternValid(String email)
    {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordPatternValid(String password)
    {
        return ! password.isEmpty();
    }
}
