package de.fau.amos.virtualledger.android.views.shared.transactionList;

import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sebastian on 17.06.17.
 */

public class ItemCheckedMapTest {

    @Test
    public void teste_hasItemsChecked_withEmptyMap_shouldReturnFalse() {
        ItemCheckedMap component_under_test = new ItemCheckedMap(new HashMap<String, Boolean>());

        assertThat(component_under_test.hasItemsChecked()).isFalse();
    }

    @Test
    public void teste_hasItemsChecked_withFilledMapAndNothingChecked_shouldReturnFalse() {
        HashMap<String, Boolean> data = new HashMap<>();
        data.put("test1", false);
        ItemCheckedMap component_under_test = new ItemCheckedMap(data);

        assertThat(component_under_test.hasItemsChecked()).isFalse();
    }

    @Test
    public void teste_hasItemsChecked_withMapwithCheckedItem_shouldReturnTrue() {
        HashMap<String, Boolean> data = new HashMap<>();
        data.put("test1", true);
        ItemCheckedMap component_under_test = new ItemCheckedMap(data);

        assertThat(component_under_test.hasItemsChecked()).isTrue();
    }

    @Test
    public void teste_shouldBePresented_withNoKeyInMap_shouldReturnFalse() {
        HashMap<String, Boolean> data = new HashMap<>();
        data.put("test2", true);
        ItemCheckedMap component_under_test = new ItemCheckedMap(data);

        assertThat(component_under_test.shouldBePresented("test1")).isFalse();
    }

    @Test
    public void teste_shouldBePresented_withKeyInMapButValueIsFalse_shouldReturnFalse() {
        HashMap<String, Boolean> data = new HashMap<>();
        data.put("test1", false);
        ItemCheckedMap component_under_test = new ItemCheckedMap(data);

        assertThat(component_under_test.shouldBePresented("test1")).isFalse();
    }

    @Test
    public void teste_shouldBePresented_withWithKeyInMapAndValueTrue_shouldReturnTrue() {
        HashMap<String, Boolean> data = new HashMap<>();
        data.put("test1", true);
        ItemCheckedMap component_under_test = new ItemCheckedMap(data);

        assertThat(component_under_test.shouldBePresented("test1")).isTrue();
    }
}
