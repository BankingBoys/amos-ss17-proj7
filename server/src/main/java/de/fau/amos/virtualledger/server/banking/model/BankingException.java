package de.fau.amos.virtualledger.server.banking.model;

/**
 * Created by Georg on 22.05.2017.
 */
public class BankingException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public BankingException(String s) {
        super(s);
    }
}
