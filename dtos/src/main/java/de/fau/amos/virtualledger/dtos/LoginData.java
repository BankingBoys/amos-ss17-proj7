package de.fau.amos.virtualledger.dtos;

public class LoginData {

    public String email;
    public String password;

    public LoginData() {
    }

    public LoginData(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
