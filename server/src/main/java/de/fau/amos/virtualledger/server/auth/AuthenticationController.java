package de.fau.amos.virtualledger.server.auth;

import de.fau.amos.virtualledger.server.model.User;
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
     * @param user
     * @return
     * @throws VirtualLedgerAuthenticationException
     */
    public String register(User user) throws VirtualLedgerAuthenticationException {
        if (user == null || user.getEmail() == null
                || user.getFirstName() == null || user.getLastName() == null) {
            throw new VirtualLedgerAuthenticationException(
                    "Please check your inserts! At least one was not formatted correctly!");
        }
        if (this.userRepository.existsUserWithEmail(user.getEmail())) {
            throw new VirtualLedgerAuthenticationException("There already exists an account with this Email address.");
        }
        this.userRepository.createUser(user);

        return "You were registered! " + user.getEmail();
    }
}
