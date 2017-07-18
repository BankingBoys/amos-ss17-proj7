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
    @SuppressWarnings("unused")
    private static final String TAG = "ContactsController";

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

    public void addContact(final Contact contact, final String userEmail) throws UserNotFoundException, ContactAlreadyExistsException {
        User owner = userRepository.findOne(userEmail);
        assertUserNotNull(owner);
        User userContact = userRepository.findOne(contact.getEmail());
        assertUserNotNull(userContact);
        assertContactDoesNotExist(owner, userContact);
        ContactsEntity addedContact = new ContactsEntity();
        addedContact.setOwner(owner);
        addedContact.setContact(userContact);
        contactsRepository.save(addedContact);
    }

    public void deleteContact(final String contactEmail, final String userEmail) throws UserNotFoundException, ContactNotFoundException {
        User owner = userRepository.findOne(userEmail);
        LOGGER.info(TAG + "User email:" + userEmail);
        assertUserNotNull(owner);
        User userContact = userRepository.findOne(contactEmail);
        LOGGER.info(TAG + "User Contact " + contactEmail);
        assertUserNotNull(userContact); //TODO: find out why contact is not found
        assertContactDoesExist(owner, userContact);
        ContactsEntity contactToDelete = contactsRepository.findEntityByOwnerAndContact(owner, userContact);
        contactsRepository.delete(contactToDelete);
    }

    private void assertUserNotNull(User user) throws UserNotFoundException {
        if (user == null) {
            LOGGER.info(TAG + "User could not be found");
            throw new UserNotFoundException("User/Contact could not be found");
        }
    }

    private void assertContactDoesNotExist(User owner, User contact) throws ContactAlreadyExistsException {
        boolean exists = contactsRepository.existsContactwithEmail(owner, contact);
        if (exists) {
            LOGGER.info(TAG + "Contact was already added before");
            throw new ContactAlreadyExistsException("Contact was already added before");
        }
    }

    private void assertContactDoesExist(User owner, User contact) throws  ContactNotFoundException {
        if (!contactsRepository.existsContactwithEmail(owner, contact)) {
            LOGGER.info(TAG + "Contact not found");
            throw new ContactNotFoundException("Contact is not in the database!");
        }
    }
}
