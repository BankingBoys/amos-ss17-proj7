package de.fau.amos.virtualledger.server.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 25.07.2017.
 */
public class SavingsAccountUserRelationTest {
    @Test
    public void getAndSetTest() {
        //Setup
        final int iD = 1245;

        User user = new User();
        User user2 = new User();
        User user3 = new User();

        BankAccountIdentifierEntity identifierEntity = new BankAccountIdentifierEntity();
        BankAccountIdentifierEntity identifierEntity2 = new BankAccountIdentifierEntity();
        BankAccountIdentifierEntity identifierEntity3 = new BankAccountIdentifierEntity();
        BankAccountIdentifierEntity identifierEntity4 = new BankAccountIdentifierEntity();
        List<BankAccountIdentifierEntity> bankAccountIdentifierEntities = new ArrayList<>();
        List<BankAccountIdentifierEntity> bankAccountIdentifierEntities3 = new ArrayList<>();
        bankAccountIdentifierEntities.add(identifierEntity);
        bankAccountIdentifierEntities.add(identifierEntity2);
        bankAccountIdentifierEntities3.add(identifierEntity3);
        bankAccountIdentifierEntities3.add(identifierEntity4);

        SavingsAccountUserRelation relation = new SavingsAccountUserRelation();

        //Act
        SavingsAccountUserRelation relation2 = new SavingsAccountUserRelation(user2);
        SavingsAccountUserRelation relation3 = new SavingsAccountUserRelation(user3, bankAccountIdentifierEntities3);
        relation.setId(iD);
        relation.setUser(user);
        relation.setBankAccountIdentifierEntityList(bankAccountIdentifierEntities);

        //Assert
        assertThat(relation.getId()).isEqualTo(iD);
        assertThat(relation.getUser()).isEqualTo(user);
        assertThat(relation.getBankAccountIdentifierEntityList()).isEqualTo(bankAccountIdentifierEntities);

        assertThat(relation2.getUser()).isEqualTo(user2);

        assertThat(relation3.getUser()).isEqualTo(user3);
        assertThat(relation3.getBankAccountIdentifierEntityList()).isEqualTo(bankAccountIdentifierEntities3);
    }
}
