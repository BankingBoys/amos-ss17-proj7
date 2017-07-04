package de.fau.amos.virtualledger.server.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Georg on 07.05.2017.
 */
public class UserCredentialTest {

    @Test
    public void setEmailCorrectInputNoException() {
        //arrange
        UserCredential credential = new UserCredential();
        String[] validCredentials = new String[] {"mkyong@yahoo.com",
                "mkyong-100@yahoo.com", "mkyong.100@yahoo.com",
                "mkyong111@mkyong.com", "mkyong-100@mkyong.net",
                "mkyong.100@mkyong.com.au", "mkyong@1.com",
                "mkyong@gmail.com.com", "mkyong+100@gmail.com",
                "mkyong-100@yahoo-test.com" };

        //act
        try {
            for (String validCredential : validCredentials) {
                credential.setEmail(validCredential);
            }
        } catch (IllegalArgumentException ex) { //assert
            Assert.fail("IllegalArgumentException was thrown");
        }
    }

    @Test
    public void setEmailIncorrectInputException() {
        //arrange
        UserCredential credential = new UserCredential();
        String[] validCredentials = new String[] {"mkyong", "mkyong@.com.my",
                "mkyong123@gmail.a", "mkyong123@.com", "mkyong123@.com.com",
                ".mkyong@mkyong.com", "mkyong()*@gmail.com", "mkyong@%*.com",
                "mkyong..2002@gmail.com", "mkyong.@gmail.com",
                "mkyong@mkyong@gmail.com", "mkyong@gmail.com.1a",
                "", null };

        //act
        for (String validCredential : validCredentials) {

            try {
                credential.setEmail(validCredential);
                Assert.fail("No IllegalArgumentException was thrown at credential " + validCredential);
            } catch (IllegalArgumentException ex) { //assert
                // expected
            }
        }
    }
}
