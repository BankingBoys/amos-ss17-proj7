package de.fau.amos.virtualledger.server.auth;

/**
 * Exception class for Authentication errors
 */
public class VirtualLedgerAuthenticationException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VirtualLedgerAuthenticationException(String message)
    {
        super(message);
    }
}
