package de.fau.amos.virtualledger.android.api.sync;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by sebastian on 09.07.17.
 */

public class AbstractMockedDataProvider<T> implements DataProvider<T> {
    private static final int DELAY_TIME_MILLISECONDS = 300;
    private List<T> providedItems;

    public AbstractMockedDataProvider(T... items){
        this.providedItems = new LinkedList<>(Arrays.asList(items));
    }

    @Override
    public Observable<List<T>> get() {
        final PublishSubject observable = PublishSubject.create();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Wait until subject is subscribed
                    Thread.sleep(DELAY_TIME_MILLISECONDS);
                } catch (Exception e) {

                }
                // publish accounts to subject
                observable.onNext(AbstractMockedDataProvider.this.providedItems);
                observable.onComplete();
            }
        });
        th.start();

        return observable;
    }

    @Override
    public Observable<Void> add(T newItem) {
        this.providedItems.add(newItem);
        final PublishSubject observable = PublishSubject.create();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Wait until subject is subscribed
                    Thread.sleep(DELAY_TIME_MILLISECONDS);
                } catch (Exception e) {

                }
                // publish accounts to subject
                observable.onNext("Item was added! Dummy Implementation");
                observable.onComplete();
            }
        });
        th.start();

        return observable;
    }
}
