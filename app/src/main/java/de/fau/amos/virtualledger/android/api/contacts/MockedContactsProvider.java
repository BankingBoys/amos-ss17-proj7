package de.fau.amos.virtualledger.android.api.contacts;

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.android.model.Contact;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Simon on 01.07.2017.
 */

public class MockedContactsProvider implements ContactsProvider {

    public static final int DELAY_TIME_MILLISECONDS = 300;

    @Override
    public Observable<List<Contact>> getContacts() {

        final List<Contact> contactsList = new ArrayList<>();

        Contact contact= new Contact("dummyEmail", "Gustav", "Gans");
        Contact contact2 = new Contact("dummyEmail2", "Tick", "Gans");
        Contact contact3 = new Contact("dummyEmail3", "Donald", "Duck");
        Contact contact4 = new Contact("dummyEmail4", "Daniel", "Düsentrieb");
        Contact contact5 = new Contact("dummyEmail5", "Gundula", "Gause");
        Contact contact6 = new Contact("dummyEmail6", "Primus", "vonQuack");
        contactsList.add(contact);
        contactsList.add(contact2);
        contactsList.add(contact3);
        contactsList.add(contact4);
        contactsList.add(contact5);
        contactsList.add(contact6);


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
                observable.onNext(contactsList);
                observable.onComplete();
            }
        });
        th.start();

        return observable;
    }

    @Override
    public Observable<String> addContacts(Contact savingsAccount) {
        return null;
    }
}
