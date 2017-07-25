package de.fau.amos.virtualledger.server.model;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 25.07.2017.
 */
public class DeletedBankAccountIdTest {
    @Test
    public void getAndSetTest() {
        //Setup
        final String bankAccessId = "AccessId1";
        final String bankAccessId2 = "AccessId2";
        final String bankAccountId = "AccountId1";
        final String bankAccountId2 = "AccountId2";
        DeletedBankAccountId deletedBankAccountId = new DeletedBankAccountId();

        //Act
        DeletedBankAccountId deletedBankAccountId2 = new DeletedBankAccountId(bankAccessId2, bankAccountId2);
        deletedBankAccountId.setBankAccessId(bankAccessId);
        deletedBankAccountId.setBankAccountId(bankAccountId);

        //Assert
        assertThat(deletedBankAccountId.getBankAccessId()).isEqualTo(bankAccessId);
        assertThat(deletedBankAccountId2.getBankAccessId()).isEqualTo(bankAccessId2);

        assertThat(deletedBankAccountId.getBankAccountId()).isEqualTo(bankAccountId);
        assertThat(deletedBankAccountId2.getBankAccountId()).isEqualTo(bankAccountId2);
    }

}
