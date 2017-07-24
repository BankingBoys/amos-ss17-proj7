package de.fau.amos.virtualledger.android.views.savings;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.fau.amos.virtualledger.dtos.SavingsAccount;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sebastian on 29.06.17.
 */

public class SavingsComparatorTest {

    @Test
    public void teste_compare_withEqualSavings_shouldReturnZero() {
        SavingsComparator component_under_test = new SavingsComparator();
        SavingsAccount account = new SavingsAccount(123, "some anme", 10, 0, any(), any());

        assertThat(component_under_test.compare(account, account)).isEqualTo(0);
    }

    @NonNull
    private Date any() {
        return new Date();
    }

    @Test
    public void teste_compare_firstDateLaterThenSecond_shouldReturnOne() throws Exception {
        SavingsComparator component_under_test = new SavingsComparator();
        SavingsAccount earlyAccount = new SavingsAccount(123, "some anme", 10, 0, toDate("10/10/16"), any());
        SavingsAccount lateAccount = new SavingsAccount(123, "some anme", 10, 0, toDate("10/10/17"), any());

        assertThat(component_under_test.compare(lateAccount, earlyAccount)).isEqualTo(1);
    }

    @Test
    public void teste_compare_firstDateBeforeThenSecond_shouldReturnMinusOne() throws Exception {
        SavingsComparator component_under_test = new SavingsComparator();
        SavingsAccount earlyAccount = new SavingsAccount(123, "some anme", 10, 0, toDate("10/10/16"), any());
        SavingsAccount lateAccount = new SavingsAccount(123, "some anme", 10, 0, toDate("10/10/17"), any());

        assertThat(component_under_test.compare(earlyAccount, lateAccount)).isEqualTo(-1);
    }


    private Date toDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(date);
    }
}
