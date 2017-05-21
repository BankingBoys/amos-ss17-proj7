package de.fau.amos.virtualledger.server.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

/**
 * Created by ramimahfoud on 20/05/2017.
 */

@Entity
@Table(name = "BankAccess")
public class BankAccess {


    private String bankName;
    private String bic;
    private String bankLogin;
    private String bankPassword;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;






    private int userId;

    public BankAccess(){}


    public String getBankPassword() {
        return bankPassword;
    }

    public void setBankPassword(String bankPassword) {
        this.bankPassword = bankPassword;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getBankLogin() {
        return bankLogin;
    }

    public void setBankLogin(String bankLogin) {
        this.bankLogin = bankLogin;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
