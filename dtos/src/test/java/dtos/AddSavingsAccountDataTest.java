package dtos;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import de.fau.amos.virtualledger.dtos.AddSavingsAccountData;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 04.07.2017.
 */

public class AddSavingsAccountDataTest {

    private AddSavingsAccountData componentUnderTest;

    @Before
    public void setUp() {
        componentUnderTest = new AddSavingsAccountData();
    }

    @Test
    public void setterAndGetterTest() {
        final String name = "testName";
        final double balance = 50.1;
        final int testDateNr = 19930505;
        final Date date = new Date(testDateNr);
        componentUnderTest.setFinalDate(date);
        assertThat(componentUnderTest.getFinalDate()).isEqualTo(date);
        componentUnderTest.setName(name);
        assertThat(componentUnderTest.getName()).isEqualTo(name);
        componentUnderTest.setGoalBalance(balance);
        assertThat(componentUnderTest.getGoalBalance()).isEqualTo(balance);
    }

}
