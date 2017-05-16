package de.fau.amos.virtualledger.server.auth;

/**
 * Exception class for Authentication errors
 */
public class VirtualLedgerAuthenticationException extends Exception {

    public VirtualLedgerAuthenticationException(String message)
    {
        super(message);
    }
}
