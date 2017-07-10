package de.fau.amos.virtualledger.android.data;

import java.io.IOException;

public class SyncFailedException extends IOException {
    public SyncFailedException() {
        super();
    }

    SyncFailedException(final String message) {
        super(message);
    }

    public SyncFailedException(final Throwable cause) {
        super(cause);
    }

    SyncFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
