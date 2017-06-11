package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterByNameTest {

    @Test
    public void teste_withLast12Months_shouldReturnFilter() throws InterruptedException {
        assertThat(FilterByName.getTransactionFilterByUIName("Last 12 months")).isNotNull();
    }

    @Test
    public void teste_withLast6Months_shouldReturnFilter() throws InterruptedException {
        assertThat(FilterByName.getTransactionFilterByUIName("Last 6 months")).isNotNull();
    }

    @Test
    public void teste_withLast4Weeks_shouldReturnFilter() throws InterruptedException {
        assertThat(FilterByName.getTransactionFilterByUIName("Last 4 weeks")).isNotNull();
    }

    @Test
    public void teste_withSpecify_shouldReturnNull() throws InterruptedException {
        assertThat(FilterByName.getTransactionFilterByUIName("Specify")).isNull();
    }

    @Test
    public void teste_withUnknownName_shouldReturnNull() throws InterruptedException {
        assertThat(FilterByName.getTransactionFilterByUIName("Specsafkjshfoiuahify")).isNull();
    }


}
