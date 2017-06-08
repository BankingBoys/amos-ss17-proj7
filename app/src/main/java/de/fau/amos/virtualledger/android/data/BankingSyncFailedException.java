package de.fau.amos.virtualledger.android.data;

import java.io.IOException;

class BankingSyncFailedException extends IOException {
    BankingSyncFailedException() {
        super();
    }

    BankingSyncFailedException(final String message) {
        super(message);
    }

    BankingSyncFailedException(final Throwable cause) {
        super(cause);
    }

    BankingSyncFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
