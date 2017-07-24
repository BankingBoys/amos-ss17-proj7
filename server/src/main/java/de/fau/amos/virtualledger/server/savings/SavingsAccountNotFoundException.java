package de.fau.amos.virtualledger.server.savings;

/**
 * Created by Simon on 24.07.2017.
 */
public class SavingsAccountNotFoundException extends Exception {

    public SavingsAccountNotFoundException() {
    }

    public SavingsAccountNotFoundException(String message) {
        super(message);
    }

    public SavingsAccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SavingsAccountNotFoundException(Throwable cause) {
        super(cause);
    }

    public SavingsAccountNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
