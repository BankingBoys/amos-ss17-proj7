package dtos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.fau.amos.virtualledger.dtos.SessionData;

/**
 * Created by Simon on 12.06.2017.
 */

public class SessionDataTest {

    private String email = "testEmail";
    private String sessionId = "testID";
    private SessionData testData;

    @Before
    public void setUp() {
        testData = new SessionData(email, sessionId);
    }

    /**
     *
     */
    @Test
    public void constructorTest() {
        SessionData data = new SessionData(email, sessionId);
        Assert.assertNotNull(data);
    }

    /**
     *
     */
    @Test
    public void setAndGetEmailTest() {
        String newTestEmail = "testEmail2";
        testData.setEmail(newTestEmail);
        Assert.assertEquals(newTestEmail, testData.getEmail());
    }

    /**
     *
     */
    @Test
    public void setAndGetSessionId() {
        String newSessionId = "sessionId2";
        testData.setSessionid(newSessionId);
        Assert.assertEquals(newSessionId, testData.getSessionid());
    }
}
