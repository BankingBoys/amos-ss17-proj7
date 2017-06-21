package de.fau.amos.virtualledger.android.data;

import org.junit.Assert;
import org.junit.Test;


public class BankingSyncFailedExceptionTest {

    @Test
    public void constructor_empty() {
        // SETUP

        // ACT
        BankingSyncFailedException ex = new BankingSyncFailedException();

        // ASSERT
        Assert.assertNotNull(ex);
    }

    @Test
    public void constructor_string() {
        // SETUP

        // ACT
        BankingSyncFailedException ex = new BankingSyncFailedException("test");

        // ASSERT
        Assert.assertNotNull(ex);
    }

    @Test
    public void constructor_throwable() {
        // SETUP

        // ACT
        BankingSyncFailedException ex = new BankingSyncFailedException(new Throwable());

        // ASSERT
        Assert.assertNotNull(ex);
    }

    @Test
    public void constructor_stringAndThrowable() {
        // SETUP

        // ACT
        BankingSyncFailedException ex = new BankingSyncFailedException("test", new Throwable());

        // ASSERT
        Assert.assertNotNull(ex);
    }
}
