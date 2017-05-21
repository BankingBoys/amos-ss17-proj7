package de.fau.amos.virtualledger.server.bank;

/**
 * Created by ramimahfoud on 21/05/2017.
 */
public class InvalidBanksException  extends Exception{

    public InvalidBanksException(String message)
    {
        super(message);
    }
}
