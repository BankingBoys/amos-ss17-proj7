package de.fau.amos.virtualledger.server.model;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by Simon on 25.07.2017.
 */
public class BankAccountIdentifierEntityTest {
    @Test
    public void setAndGetTest() {
        //Setup
        final String accessId = "accessId1";
        final String accessId2 = "accessId2";
        final String accountId = "accountId1";
        final String accountId2 = "accountId2";
        BankAccountIdentifierEntity entity = new BankAccountIdentifierEntity();

        //Act
        BankAccountIdentifierEntity entity2 = new BankAccountIdentifierEntity(accessId2, accountId2);
        entity.setAccessid(accessId);
        entity.setAccountid(accountId);

        //Assert
        assertThat(entity.getAccessid()).isEqualTo(accessId);
        assertThat(entity2.getAccessid()).isEqualTo(accessId2);

        assertThat(entity.getAccountid()).isEqualTo(accountId);
        assertThat(entity2.getAccountid()).isEqualTo(accountId2);
    }
}
