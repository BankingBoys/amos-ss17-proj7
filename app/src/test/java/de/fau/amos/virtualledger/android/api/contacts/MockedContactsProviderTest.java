package de.fau.amos.virtualledger.android.api.contacts;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import de.fau.amos.virtualledger.android.model.Contact;
import io.reactivex.Observable;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 02.07.2017.
 */

public class MockedContactsProviderTest {

    private MockedContactsProvider mockedContactsProvider;

    @Before
    public void setUp() {
        mockedContactsProvider = new MockedContactsProvider();
    }

    @Test
    public void getContactsTest() {
        Observable<List<Contact>> testObservable = mockedContactsProvider.getContacts();
        assertThat(testObservable).isNotNull();
    }
}
