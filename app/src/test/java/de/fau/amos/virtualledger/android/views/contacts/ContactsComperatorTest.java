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
        ContactsComparator component_under_test = new ContactsComparator();
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

    @Test
    public void testCompareFirstStringLexicographicallyLess() {
        ContactsComparator component_under_test = new ContactsComparator();
        Contact contact1 = new Contact();
        Contact contact2 = new Contact();
        final String testFirstName = "Albert";
        final String testLastName = "Quack";
        final String testFirstName2 = "Daniel";
        final String testLastName2 = "Düsentrieb";
        contact1.setFirstName(testFirstName);
        contact1.setLastName(testLastName);
        contact2.setFirstName(testFirstName2);
        contact2.setLastName(testLastName2);
        final int referencedValue = 0;

        assertThat(component_under_test.compare(contact1, contact2)).isLessThan(referencedValue);
    }

    @Test
    public void testCompareFirstStringLexicographicallyGreater() {
        ContactsComparator component_under_test = new ContactsComparator();
        Contact contact1 = new Contact();
        Contact contact2 = new Contact();
        final String testFirstName = "Gundula";
        final String testLastName = "Gause";
        final String testFirstName2 = "Daisy";
        final String testLastName2 = "Duck";
        contact1.setFirstName(testFirstName);
        contact1.setLastName(testLastName);
        contact2.setFirstName(testFirstName2);
        contact2.setLastName(testLastName2);
        final int referencedValue = 0;

        assertThat(component_under_test.compare(contact1, contact2)).isGreaterThan(referencedValue);
    }

}
