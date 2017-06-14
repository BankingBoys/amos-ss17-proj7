package de.fau.amos.virtualledger.android.views.bankingOverview;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Fragment.ExpandableBankFragment;

/**
 * Created by Simon on 13.06.2017.
 */

public class ExpandableBankFragmentTest {


    @Test
    public void setAllAccountsCheckedTest() {
        MocketBankingOverview component_under_test = new MocketBankingOverview();
        HashMap<String, Boolean> map = new HashMap<>();
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";
        String test4 = "test4";
        map.put(test1, false);
        map.put(test2, false);
        map.put(test3, false);
        map.put(test4, false);
        component_under_test.setMappingCheckBoxes(map);
        component_under_test.setAllAccountsCheckedOrUnchecked(true);
        Assert.assertEquals(map.get("test1"), true);
        Assert.assertEquals(map.get("test2"), true);
        Assert.assertEquals(map.get("test3"), true);
        Assert.assertEquals(map.get("test4"), true);
    }

    @Test
    public void setAllAccountsUnCheckedTest() {
        MocketBankingOverview component_under_test = new MocketBankingOverview();
        HashMap<String, Boolean> map = new HashMap<>();
        String test1 = "test1";
        String test2 = "test2";
        String test3 = "test3";
        String test4 = "test4";
        map.put(test1, true);
        map.put(test2, true);
        map.put(test3, true);
        map.put(test4, true);
        component_under_test.setMappingCheckBoxes(map);
        component_under_test.setAllAccountsCheckedOrUnchecked(false);
        Assert.assertEquals(map.get("test1"), false);
        Assert.assertEquals(map.get("test2"), false);
        Assert.assertEquals(map.get("test3"), false);
        Assert.assertEquals(map.get("test4"), false);
    }



    class MocketBankingOverview extends ExpandableBankFragment{
        public String toString(){
            return "test";
        }
    }

}
