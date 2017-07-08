package de.fau.amos.virtualledger.server.auth;

class KeycloakException extends RuntimeException {
    KeycloakException() {
        super();
    }

    KeycloakException(final String message) {
        super(message);
    }

    KeycloakException(final Throwable cause) {
        super(cause);
    }

    KeycloakException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
