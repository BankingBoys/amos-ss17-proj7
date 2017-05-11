package de.fau.amos.virtualledger.server.auth;

import de.fau.amos.virtualledger.server.model.UserCredential;
import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.AuthenticationException;

/**
 * Created by Georg on 11.05.2017.
 */
@RequestScoped
public class AuthenticationController {

    @Inject
    UserCredentialRepository userCredentialRepository;

    public String register(UserCredential credential) throws VirtualLedgerAuthenticationException {
        if (credential == null || credential.getEmail() == null || credential.getPassword() == null || credential.getFirstName() == null || credential.getLastName() == null) { // if not null, matches the pattern -> specified in model class
            throw new VirtualLedgerAuthenticationException("Please check your inserts! At least one was not formatted correctly!");
        }
        if (this.userCredentialRepository.existsUserCredentialEmail(credential.getEmail())) {
            throw new VirtualLedgerAuthenticationException("There already exists an account with this Email address.");
        }

        this.userCredentialRepository.createUserCredential(credential);

        return "You were registered! " + credential.getEmail();
    }
}
