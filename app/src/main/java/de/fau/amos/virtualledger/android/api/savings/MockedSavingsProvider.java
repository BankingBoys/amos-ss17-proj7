package de.fau.amos.virtualledger.android.api.savings;

import java.util.Date;

import de.fau.amos.virtualledger.android.api.sync.AbstractMockedDataProvider;
import de.fau.amos.virtualledger.android.model.SavingsAccount;


public class MockedSavingsProvider extends AbstractMockedDataProvider {
    public MockedSavingsProvider(){
        super(new SavingsAccount("1", "dummy1", 100.00, 12.23, new Date(), new Date()));
    }
}
