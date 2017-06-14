package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

/**
 * Created by sebastian on 11.06.17.
 */

public enum FilterByName {
    LAST12MONTHS("Last 12 months") {
        public TransactionFilter getFilter() {
            return new Last12Months();
        }
    }, LAST6MONTHS("Last 6 months") {
        public TransactionFilter getFilter() {
            return new Last6Months();
        }
    }, LAST4WEEKS("Last 4 weeks") {
        public TransactionFilter getFilter() {
            return new Last4Weeks();
        }
    }, SPECIFY("Specify") {
        public TransactionFilter getFilter() {
            return null;
        }
    }, LAST_MONTH("Actual month") {
        public TransactionFilter getFilter() {
            return new ByActualMonth();
        }
    };

    private String uiName;

    private FilterByName(String uiName) {
        this.uiName = uiName;
    }

    public static TransactionFilter getTransactionFilterByUIName(String name) {
        for (FilterByName item : FilterByName.values()) {
            if (item.uiName.equals(name)) {
                return item.getFilter();
            }
        }
        return null;
    }

    public abstract TransactionFilter getFilter();

}
