package de.fau.amos.virtualledger.android.model;

/**
 * Created by Georg on 26.06.2017.
 */

public class OidcLoginData {

    public String client_id;
    public String username;
    public String password;
    public String grant_type;

    public OidcLoginData(String client_id, String username, String password, String grant_type) {
        this.client_id = client_id;
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
    }

    public OidcLoginData() {
    }
}
