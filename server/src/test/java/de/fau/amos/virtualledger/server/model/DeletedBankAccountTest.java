package de.fau.amos.virtualledger.server.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 25.07.2017.
 */
public class DeletedBankAccountTest {
    @Test
    public void setAndGetTest() {
        //Setup
        final DeletedBankAccountId bankAccountId = new DeletedBankAccountId();
        final DeletedBankAccountId bankAccountId2 = new DeletedBankAccountId();
        final DeletedBankAccountId bankAccountId3 = new DeletedBankAccountId();

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

        DeletedBankAccount deletedBankAccount = new DeletedBankAccount();

        //Act
        DeletedBankAccount deletedBankAccount2 = new DeletedBankAccount(bankAccountId2, users2);
        DeletedBankAccount deletedBankAccount3 = new DeletedBankAccount(bankAccountId3);
        deletedBankAccount.setUsers(users);
        deletedBankAccount.setId(bankAccountId);

        //Assert
        assertThat(deletedBankAccount.getId()).isEqualTo(bankAccountId);
        assertThat(deletedBankAccount2.getId()).isEqualTo(bankAccountId2);
        assertThat(deletedBankAccount3.getId()).isEqualTo(bankAccountId3);

        assertThat(deletedBankAccount.getUsers()).isEqualTo(users);
        assertThat(deletedBankAccount2.getUsers()).isEqualTo(users2);
    }
}
