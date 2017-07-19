package de.fau.amos.virtualledger.android.model;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Simon on 27.06.2017.
 */

public class UserCredentialTest {
    String testEmail1 = "testEmail1";
    String testPw1 = "testPW1";
    String testFirstName1 = "testFirstName1";
    String testLastName1 = "testLastName1";

    UserCredential testCredential;

    @Before
    public void setUp() {
        testCredential = new UserCredential(testEmail1, testPw1, testFirstName1, testLastName1);
    }

    @Test
    public void setAndGetEmailTest() {
        String testEmail2 = "testEmail2";
        testCredential.setEmail(testEmail2);
        Assertions.assertThat(testCredential.getEmail()).isEqualTo(testEmail2);
    }

    @Test
    public void setAndGetPwTest() {
        String testPw2 = "testPW2";
        testCredential.setPassword(testPw2);
        Assertions.assertThat(testCredential.getPassword()).isEqualTo(testPw2);
    }

    @Test
    public void setAndGetFirstNameTest() {
        String testFirstName2 = "testFirstName2";
        testCredential.setFirstname(testFirstName2);
        Assertions.assertThat(testCredential.getFirstname()).isEqualTo(testFirstName2);
    }

    @Test
    public void setAndGetLastNameTest() {
        String testLastName2 = "testLastName2";
        testCredential.setLastname(testLastName2);
        Assertions.assertThat(testCredential.getLastname()).isEqualTo(testLastName2);
    }


}
