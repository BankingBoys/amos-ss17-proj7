package de.fau.amos.virtualledger.android.model;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by Simon on 01.07.2017.
 */


public class ContactTest {
    private Contact contact;
    private String email = "testEmail";
    private String firstName = "testFirstName";
    private String lastName = "testLastName";

    @Before
    public void setUp() {
        contact = new Contact(email, firstName, lastName);
    }

    @Test
    public void constructorTest() {
        assertThat(contact).isNotNull();
    }

    @Test
    public void setterAndGetterTest() {
        String email2 = "testEmail2";
        String firstName2 = "testFirstName2";
        String lastName2 = "testLastName2";

        contact.setEmail(email2);
        contact.setFirstName(firstName2);
        contact.setLastName(lastName2);

        assertThat(contact.getEmail()).isEqualTo(email2);
        assertThat(contact.getFirstName()).isEqualTo(firstName2);
        assertThat(contact.getLastName()).isEqualTo(lastName2);
    }

}
