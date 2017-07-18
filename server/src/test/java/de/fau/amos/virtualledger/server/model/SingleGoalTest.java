package de.fau.amos.virtualledger.server.model;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 16.07.2017.
 */
public class SingleGoalTest {
    @Test
    public void getterAndSetterTest() {
        //Setup
        SingleGoal singleGoal = new SingleGoal();
        final String testName = "testName";
        final double testBalance = 100.1;
        singleGoal = new SingleGoal(testName, testBalance);
        final String testName2 = "testName2";
        final double testBalance2 = 100000.4;

        //Act
        singleGoal.setAmount(testBalance2);
        singleGoal.setName(testName2);

        //Assert
        assertThat(singleGoal.getAmount()).isEqualTo(testBalance2);
        assertThat(singleGoal.getName()).isEqualTo(testName2);
    }
}
