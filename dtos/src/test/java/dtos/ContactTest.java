package dtos;

import org.junit.Before;
import org.junit.Test;

import de.fau.amos.virtualledger.dtos.Contact;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by Simon on 04.07.2017.
 */

public class ContactTest {
    private Contact componentUnderTest;

    @Before
    public void setUp() {
        componentUnderTest = new Contact();
        final String email = "testEmail";
        final String firstName = "testFirstName";
        final String lastName = "testLastName";
        componentUnderTest = new Contact(email, firstName, lastName);
    }

    @Test
    public void getterAndSetterTest() {
        final String email2 = "testEmail2";
        final String firstName2 = "testFirstName2";
        final String lastName2 = "testLastName2";
        componentUnderTest.setEmail(email2);
        assertThat(componentUnderTest.getEmail()).isEqualTo(email2);
        componentUnderTest.setFirstName(firstName2);
        assertThat(componentUnderTest.getFirstName()).isEqualTo(firstName2);
        componentUnderTest.setLastName(lastName2);
        assertThat(componentUnderTest.getLastName()).isEqualTo(lastName2);
    }

}
