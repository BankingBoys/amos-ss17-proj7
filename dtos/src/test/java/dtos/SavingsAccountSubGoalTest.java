package dtos;

import org.junit.Test;

import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by Simon on 25.07.2017.
 */

public class SavingsAccountSubGoalTest {
    @Test
    public void setAndGetTest() {
        final String name = "name";
        final double amount = 123;
        final String name2 = "name2";
        final double amount2 = 1234;

        //Act
        SavingsAccountSubGoal componentUnderTest = new SavingsAccountSubGoal(name, amount);
        SavingsAccountSubGoal componentUnderTest2 = new SavingsAccountSubGoal();
        componentUnderTest2.setAmount(amount2);
        componentUnderTest2.setName(name2);

        //Assert
        assertThat(componentUnderTest.getName()).isEqualTo(name);
        assertThat(componentUnderTest.getAmount()).isEqualTo(amount);

        assertThat(componentUnderTest2.getName()).isEqualTo(name2);
        assertThat(componentUnderTest2.getAmount()).isEqualTo(amount2);
    }
}
