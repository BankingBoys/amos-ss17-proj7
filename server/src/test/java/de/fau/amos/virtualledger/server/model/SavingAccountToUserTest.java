package de.fau.amos.virtualledger.server.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Created by Simon on 27.06.2017.
 */
public class SavingAccountToUserTest {

    @Test
    public void constructorTest() {
        SavingsAccountToUser testAccount = new SavingsAccountToUser();
        Assertions.assertThat(testAccount).isNotNull();
    }

    @Test
    public void constructorTest2() {
        String testEmail = "email@email.de";
        int savingsAccount = 0;
        SavingsAccountToUser testAccount = new SavingsAccountToUser(testEmail, savingsAccount);
        Assertions.assertThat(testAccount).isNotNull();
        Assertions.assertThat(testAccount.getEmail()).isEqualTo(testEmail);
        Assertions.assertThat(testAccount.getId_savingsaccount()).isEqualTo(savingsAccount);
    }
}
