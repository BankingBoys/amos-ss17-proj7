package de.fau.amos.virtualledger.server.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 25.07.2017.
 */
public class DeletedBankAccessTest {
    @Test
    public void setAndGetTest() {
        //Setup
        final String bankAccessId = "Id1";
        final String bankAccessId2 = "Id2";
        final String bankAccessId3 = "Id3";
        User user = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        Set<User> users = new HashSet<>();
        users.add(user);
        users.add(user2);
        Set<User> users2 = new HashSet<>();
        users2.add(user3);
        users2.add(user4);
        DeletedBankAccess deletedBankAccess3 = new DeletedBankAccess();

        //Act
        DeletedBankAccess deletedBankAccess = new DeletedBankAccess(bankAccessId, users);
        DeletedBankAccess deletedBankAccess2 = new DeletedBankAccess(bankAccessId2);
        deletedBankAccess3.setUsers(users2);
        deletedBankAccess3.setBankAccessId(bankAccessId3);

        //Assert
        assertThat(deletedBankAccess.getBankAccessId()).isEqualTo(bankAccessId);
        assertThat(deletedBankAccess2.getBankAccessId()).isEqualTo(bankAccessId2);
        assertThat(deletedBankAccess3.getBankAccessId()).isEqualTo(bankAccessId3);

        assertThat(deletedBankAccess.getUsers()).isEqualTo(users);
        assertThat(deletedBankAccess3.getUsers()).isEqualTo(users2);
    }
}
