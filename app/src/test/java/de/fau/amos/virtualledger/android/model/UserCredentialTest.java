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
    public void constructorTest() {
        Assertions.assertThat(testCredential).isNotNull();
    }
}
