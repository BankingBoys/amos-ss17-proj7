package de.fau.amos.virtualledger.server.auth;

import de.fau.amos.virtualledger.server.model.UserCredential;
import de.fau.amos.virtualledger.server.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller / Service class for authentication
 */
@Component
public class AuthenticationController {

    private UserRepository userRepository;

    @Autowired
    public AuthenticationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    protected AuthenticationController() {
    }

    /**
     * register a new user, if attributes are null or don't follow the specific
     * pattern, an exception is thrown
     * 
     * @param credential
     * @return
     * @throws VirtualLedgerAuthenticationException
     */
    public String register(UserCredential credential) throws VirtualLedgerAuthenticationException {
        if (credential == null || credential.getEmail() == null || credential.getPassword() == null
                || credential.getFirstname() == null || credential.getLastname() == null) {
            throw new VirtualLedgerAuthenticationException(
                    "Please check your inserts! At least one was not formatted correctly!");
        }
        if (this.userRepository.existsUserWithEmail(credential.getEmail())) {
            throw new VirtualLedgerAuthenticationException("There already exists an account with this Email address.");
        }
        this.userRepository.createUser(credential);

        return "You were registered! " + credential.getEmail();
    }
}
