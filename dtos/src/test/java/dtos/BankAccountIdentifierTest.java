package dtos;

import org.junit.Test;

import de.fau.amos.virtualledger.dtos.BankAccountIdentifier;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by Simon on 25.07.2017.
 */

public class BankAccountIdentifierTest {
    @Test
    public void setAndGetTest() {
        //Setup
        final String accessID = "testAccess";
        final String accountID = "testAccount";
        final String accessID2 = "testAccess2";
        final String accountID2 = "testAccount2";

        //Act
        BankAccountIdentifier componentUnderTest = new BankAccountIdentifier(accessID, accountID);
        BankAccountIdentifier componentUnderTest2 = new BankAccountIdentifier();
        componentUnderTest2.setAccessid(accessID2);
        componentUnderTest2.setAccountid(accountID2);

        //Assert
        assertThat(componentUnderTest.getAccessid()).isEqualTo(accessID);
        assertThat(componentUnderTest.getAccountid()).isEqualTo(accountID);

        assertThat(componentUnderTest2.getAccessid()).isEqualTo(accessID2);
        assertThat(componentUnderTest2.getAccountid()).isEqualTo(accountID2);

    }
}
