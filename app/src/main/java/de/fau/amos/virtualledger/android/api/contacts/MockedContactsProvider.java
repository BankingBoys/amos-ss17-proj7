package de.fau.amos.virtualledger.android.api.contacts;

import de.fau.amos.virtualledger.android.api.sync.AbstractMockedDataProvider;
import de.fau.amos.virtualledger.dtos.Contact;

/**
 * Created by Simon on 01.07.2017.
 */

public class MockedContactsProvider extends AbstractMockedDataProvider<Contact> {
    public MockedContactsProvider() {
        super(new Contact("dummyEmail", "Gustav", "Gans"),//
                new Contact("dummyEmail2", "Tick", "Gans"),//
                new Contact("dummyEmail3", "Donald", "Duck"),//
                new Contact("dummyEmail4", "Daniel", "Düsentrieb"),//
                new Contact("dummyEmail5", "Gundula", "Gause"),//
                new Contact("dummyEmail6", "Primus", "vonQuack"));
    }
}
