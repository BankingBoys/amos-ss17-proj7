package de.fau.amos.virtualledger.android.api.auth;

/**
 * Created by Georg on 26.06.2017.
 */

public class OidcData {

    public String access_token;
    public String refresh_token;
    public String token_type;
    public String id_token;
    public String session_state;

    public int expires_in;
    public int refresh_expires_in;
}
