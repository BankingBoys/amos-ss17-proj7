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
}
