package de.fau.amos.virtualledger.android.data;

import org.junit.Assert;
import org.junit.Test;


public class SyncFailedExceptionTest {

    @Test
    public void constructor_empty() {
        // SETUP

        // ACT
        SyncFailedException ex = new SyncFailedException();

        // ASSERT
        Assert.assertNotNull(ex);
    }

    @Test
    public void constructor_string() {
        // SETUP

        // ACT
        SyncFailedException ex = new SyncFailedException("test");

        // ASSERT
        Assert.assertNotNull(ex);
    }

    @Test
    public void constructor_throwable() {
        // SETUP

        // ACT
        SyncFailedException ex = new SyncFailedException(new Throwable());

        // ASSERT
        Assert.assertNotNull(ex);
    }

    @Test
    public void constructor_stringAndThrowable() {
        // SETUP

        // ACT
        SyncFailedException ex = new SyncFailedException("test", new Throwable());

        // ASSERT
        Assert.assertNotNull(ex);
    }
}
