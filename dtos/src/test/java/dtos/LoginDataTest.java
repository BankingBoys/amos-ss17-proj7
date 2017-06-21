package dtos;

import org.junit.Assert;
import org.junit.Test;

import de.fau.amos.virtualledger.dtos.LoginData;

/**
 * Created by Simon on 21.06.2017.
 */

public class LoginDataTest {

    LoginData testData = new LoginData("testEmail", "testPw");

    @Test
    public void constructorTest() {
        Assert.assertNotNull(testData);
    }

    @Test
    public void toStringTest() {
        String test = testData.toString();
        Assert.assertNotEquals(test, "");
    }

}
