package de.fau.amos.virtualledger.server.model;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class SavingsAccountEntityTest {

    private static final double DELTA = 0.01;

    @Test
    public void constructorGettersMatch() {
        // SETUP
        final String name = "name";
        final double goalBalance = 123.45;
        final double currentBalance = 234.567;
        Date finalDate = new Date();
        Date finalGoalFinishedDate = new Date();
        final String goalName = "testGoal";
        final double singleGoalBalance = 123123.1;
        //Setup2
        final int iD2 = 123;
        final String name2 = "name2";
        final double goalBalance2 = 123;
        final double currentBalance2 = 1234;
        Date finalDate2 = new Date();
        Date finalGoalFinishedDate2 = new Date();
        final String testName = "testName";
        final double testBalance = 100.1;
        SavingsAccountSubGoalEntity savingsAccountSubGoalEntity = new SavingsAccountSubGoalEntity(testName, testBalance);
        Set<SavingsAccountSubGoalEntity> subGoals = new HashSet<>();
        subGoals.add(savingsAccountSubGoalEntity);
        //Setup3
        final String name3 = "name3";
        final double goalBalance3 = 123666;
        final double currentBalance3 = 12345455;
        Date finalDate3 = new Date();
        Date finalGoalFinishedDate3 = new Date();
        SavingsAccountUserRelation userRelation = new SavingsAccountUserRelation();
        Set<SavingsAccountUserRelation> savingsAccountUserRelations = new HashSet<>();
        savingsAccountUserRelations.add(userRelation);
        //Setup4
        final int iD4 = 12355;
        final String name4 = "name4";
        final double goalBalance4 = 124555213;
        final double currentBalance4 = 1515125;
        Date finalDate4 = new Date();
        Date finalGoalFinishedDate4 = new Date();
        SavingsAccountEntity savingsAccountEntity4 = new SavingsAccountEntity();

        // ACT
        savingsAccountEntity4.setId(iD4);
        savingsAccountEntity4.setName(name4);
        savingsAccountEntity4.setGoalbalance(goalBalance4);
        savingsAccountEntity4.setCurrentbalance(currentBalance4);
        savingsAccountEntity4.setFinaldate(finalDate4);
        savingsAccountEntity4.setFinalGoalFinishedDate(finalGoalFinishedDate4);

        SavingsAccountEntity savingsAccountEntity = new SavingsAccountEntity(name, goalBalance, currentBalance, finalDate, finalGoalFinishedDate);
        SavingsAccountEntity savingsAccountEntity2 = new SavingsAccountEntity(iD2, name2, goalBalance2, currentBalance2, finalDate2, finalGoalFinishedDate2, subGoals);
        SavingsAccountEntity savingsAccountEntity3 = new SavingsAccountEntity(name3, goalBalance3, currentBalance3, finalDate3, finalGoalFinishedDate3, savingsAccountUserRelations);

        // ASSERT
        assertThat(savingsAccountEntity.getName()).isEqualTo(name);
        assertThat(savingsAccountEntity.getGoalbalance()).isEqualTo(goalBalance);
        assertThat(savingsAccountEntity.getCurrentbalance()).isEqualTo(currentBalance);
        assertThat(savingsAccountEntity.getFinaldate()).isEqualTo(finalDate);
        assertThat(savingsAccountEntity.getFinalGoalFinishedDate()).isEqualTo(finalGoalFinishedDate);

        assertThat(savingsAccountEntity2.getId()).isEqualTo(iD2);
        assertThat(savingsAccountEntity2.getName()).isEqualTo(name2);
        assertThat(savingsAccountEntity2.getGoalbalance()).isEqualTo(goalBalance2);
        assertThat(savingsAccountEntity2.getCurrentbalance()).isEqualTo(currentBalance2);
        assertThat(savingsAccountEntity2.getFinaldate()).isEqualTo(finalDate2);
        assertThat(savingsAccountEntity2.getSubGoals()).isEqualTo(subGoals);

        assertThat(savingsAccountEntity3.getName()).isEqualTo(name3);
        assertThat(savingsAccountEntity3.getGoalbalance()).isEqualTo(goalBalance3);
        assertThat(savingsAccountEntity3.getCurrentbalance()).isEqualTo(currentBalance3);
        assertThat(savingsAccountEntity3.getFinaldate()).isEqualTo(finalDate3);
        assertThat(savingsAccountEntity3.getUserRelations()).isEqualTo(savingsAccountUserRelations);

        assertThat(savingsAccountEntity4.getId()).isEqualTo(iD4);
        assertThat(savingsAccountEntity4.getName()).isEqualTo(name4);
        assertThat(savingsAccountEntity4.getGoalbalance()).isEqualTo(goalBalance4);
        assertThat(savingsAccountEntity4.getCurrentbalance()).isEqualTo(currentBalance4);
        assertThat(savingsAccountEntity4.getFinaldate()).isEqualTo(finalDate4);
        assertThat(savingsAccountEntity4.getFinalGoalFinishedDate()).isEqualTo(finalGoalFinishedDate4);


    }
}
