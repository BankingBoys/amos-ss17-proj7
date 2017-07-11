package de.fau.amos.virtualledger.server.contacts;

/**
 * Created by Simon on 10.07.2017.
 */
public class ContactAlreadyExistsException extends Exception {
    public ContactAlreadyExistsException() {
    }

    public ContactAlreadyExistsException(String message) {
        super(message);
    }

    public ContactAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public ContactAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
