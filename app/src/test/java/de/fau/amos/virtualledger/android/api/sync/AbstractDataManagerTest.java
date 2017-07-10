package de.fau.amos.virtualledger.android.api.sync;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.fau.amos.virtualledger.android.data.SyncStatus;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by sebastian on 10.07.17.
 */

public class AbstractDataManagerTest {


    @Test
    public void test_initialStatus_isNotInSync(){
        FullfilledDataManager component_under_test = new FullfilledDataManager(new StubbedSavingsProvider());

        Assertions.assertThat(component_under_test.getSyncStatus()).isEqualTo(SyncStatus.NOT_SYNCED);
    }
}
class FullfilledDataManager extends AbstractDataManager<SavingsAccount>{

    public FullfilledDataManager(DataProvider<SavingsAccount> dataProvider) {
        super(dataProvider);
    }
}
class StubbedSavingsProvider implements DataProvider<SavingsAccount>{

    private List<SavingsAccount> savings;
    private PublishSubject observable;
    StubbedSavingsProvider(SavingsAccount... savings){
        this.savings = new LinkedList<>(Arrays.asList(savings));
    }

    @Override
    public Observable<List<SavingsAccount>> get() {
        observable = PublishSubject.create();
        return this.observable;
    }

    public void fireOnNextEvent(){
        observable.onNext(this.savings);
        this.observable.onComplete();
    }

    @Override
    public Observable<Void> add(SavingsAccount newItem) {
        this.savings.add(newItem);
        return null;
    }

    public List<SavingsAccount> accounts(){
        return this.savings;
    }
}
