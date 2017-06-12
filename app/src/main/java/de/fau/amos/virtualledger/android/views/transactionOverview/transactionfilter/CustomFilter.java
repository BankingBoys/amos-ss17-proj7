package de.fau.amos.virtualledger.android.views.transactionOverview.transactionfilter;

import java.util.Date;

import de.fau.amos.virtualledger.android.views.transactionOverview.Transaction;

/**
 * Created by sebastian on 11.06.17.
 */

public class CustomFilter implements TransactionFilter{
    private Date startDate;
    private Date endDate;
    public CustomFilter(Date startDate, Date endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean shouldBeRemoved(Transaction t) {
        if(this.startDate.after(t.booking().getDate())){
            return true;
        }
        if(this.endDate.before(t.booking().getDate())){
            return true;
        }
        return false;
    }
}
