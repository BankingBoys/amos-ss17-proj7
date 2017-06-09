package de.fau.amos.virtualledger.android.data;

import java.io.IOException;

public class BankingAddFailedException extends IOException {
    BankingAddFailedException() {
        super();
    }

    BankingAddFailedException(final String message) {
        super(message);
    }

    BankingAddFailedException(final Throwable cause) {
        super(cause);
    }

    BankingAddFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
