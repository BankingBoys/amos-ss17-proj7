package de.fau.amos.virtualledger.android.views.contacts;

import org.junit.Test;

import de.fau.amos.virtualledger.dtos.Contact;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 08.07.2017.
 */

public class ContactsComperatorTest {

    @Test
    public void testCompareWithEqualNamesReturnZero() {
        ContactsComperator component_under_test = new ContactsComperator();
        Contact contact1 = new Contact();
        Contact contact2 = new Contact();
        final String testFirstName = "Albert";
        final String testLastName = "Quack";
        contact1.setFirstName(testFirstName);
        contact1.setLastName(testLastName);
        contact2.setFirstName(testFirstName);
        contact2.setLastName(testLastName);
        final int expectedValue = 0;

        assertThat(component_under_test.compare(contact1, contact2)).isEqualTo(expectedValue);
    }

}
