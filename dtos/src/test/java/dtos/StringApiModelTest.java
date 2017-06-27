package dtos;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import de.fau.amos.virtualledger.dtos.StringApiModel;

/**
 * Created by Simon on 27.06.2017.
 */

public class StringApiModelTest {
    @Test
    public void setAndGetString() {
        StringApiModel testModel = new StringApiModel();
        String testString = "test";
        testModel.setData(testString);
        Assertions.assertThat(testModel.getData()).isEqualTo(testString);
    }
}
