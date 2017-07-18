package de.fau.amos.virtualledger.server.model;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 16.07.2017.
 */
public class SavingsAccountSubGoalEntityTest {
    @Test
    public void getterAndSetterTest() {
        //Setup
        SavingsAccountSubGoalEntity savingsAccountSubGoalEntity = new SavingsAccountSubGoalEntity();
        final String testName = "testName";
        final double testBalance = 100.1;
        savingsAccountSubGoalEntity = new SavingsAccountSubGoalEntity(testName, testBalance);
        final String testName2 = "testName2";
        final double testBalance2 = 100000.4;

        //Act
        savingsAccountSubGoalEntity.setAmount(testBalance2);
        savingsAccountSubGoalEntity.setName(testName2);

        //Assert
        assertThat(savingsAccountSubGoalEntity.getAmount()).isEqualTo(testBalance2);
        assertThat(savingsAccountSubGoalEntity.getName()).isEqualTo(testName2);
    }
}
