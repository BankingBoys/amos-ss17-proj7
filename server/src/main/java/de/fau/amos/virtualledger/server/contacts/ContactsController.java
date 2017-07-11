package de.fau.amos.virtualledger.server.contacts;

import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.server.model.ContactsEntity;
import de.fau.amos.virtualledger.server.model.User;
import de.fau.amos.virtualledger.server.persistence.ContactsRepository;
import de.fau.amos.virtualledger.server.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@Component
public class ContactsController {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ContactsRepository contactsRepository;
    private final UserRepository userRepository;

    @Autowired
    public ContactsController(final ContactsRepository contactsRepository, final UserRepository userRepository) {
        this.contactsRepository = contactsRepository;
        this.userRepository = userRepository;
    }

    public List<Contact> getContactsByEmail(final String email) throws UserNotFoundException {
        User user = userRepository.findOne(email);
        if (user == null) {
            throw new UserNotFoundException("User could not be found");
        }
        List<ContactsEntity> contacts = contactsRepository.findAllByOwner(user);
        return createContactFromEntities(contacts);
    }

    private List<Contact> createContactFromEntities(List<ContactsEntity> contactsEntity) {
        List<Contact> contactList = new ArrayList<>();
        for (ContactsEntity c : contactsEntity) {
            Contact contact = new Contact(c.getContact().getEmail(), c.getContact().getFirstName(),
                    c.getContact().getLastName());
            contactList.add(contact);
        }
        return contactList;
    }

    public void addContact(final Contact contact, final String userEmail) {
        User user = userRepository.findOne(userEmail);
        /*assertUserNotNull(user);*/
        User userContact = userRepository.findOne(contact.getEmail());
/*        assertUserNotNull(userContact);*/
        ContactsEntity addedContact = new ContactsEntity();
        addedContact.setOwner(user);
        addedContact.setContact(userContact);
        contactsRepository.save(addedContact);
    }

/*    private void assertUserNotNull(User user) throws UserNotFoundException {
        if(user == null) {
            throw new UserNotFoundException("User/Contact could not be found");
        }
    }*/
}
