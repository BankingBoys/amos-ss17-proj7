package dtos;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import de.fau.amos.virtualledger.dtos.LoginData;

/**
 * Created by Simon on 21.06.2017.
 */

public class LoginDataTest {


    @Test
    public void testeToString() {
        LoginData componentUnderTest = new LoginData("testEmail", "testPw");
        Assertions.assertThat(componentUnderTest.toString()).isEqualTo("LoginData{testEmail}");
    }

}
