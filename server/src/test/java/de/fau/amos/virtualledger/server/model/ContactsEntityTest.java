package de.fau.amos.virtualledger.server.model;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Simon on 10.07.2017.
 */
public class ContactsEntityTest {

    @Test
    public void getterAndSetterTest() {
        final User owner = new User();
        final User contact = new User();
        ContactsEntity componentUnderTest = new ContactsEntity();
        componentUnderTest.setContact(contact);
        componentUnderTest.setOwner(owner);

        assertThat(componentUnderTest.getContact()).isEqualTo(contact);
        assertThat(componentUnderTest.getOwner()).isEqualTo(owner);
    }
}
