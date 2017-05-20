package de.fau.amos.virtualledger.server.auth;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.server.banking.api.BankingApiFacade;
import de.fau.amos.virtualledger.server.model.UserCredential;
import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 * Controller / Service class for authentication
 */
@RequestScoped
public class AuthenticationController {

    /**
     *
     */
    @Inject
    private UserCredentialRepository userCredentialRepository;

    @Inject
    private SessionIdGenerator sessionIdGenerator;

    @Inject
    private BankingApiFacade bankingApiFacade;

    /**
     * register a new user, if attributes are null or don't follow the specific pattern, an exception is thrown
     * @param credential
     * @return
     * @throws VirtualLedgerAuthenticationException
     */
    public String register(UserCredential credential) throws VirtualLedgerAuthenticationException {
        if (credential == null || credential.getEmail() == null || credential.getPassword() == null || credential.getFirstName() == null || credential.getLastName() == null) { // if not null, matches the pattern -> specified in model class
            throw new VirtualLedgerAuthenticationException("Please check your inserts! At least one was not formatted correctly!");
        }
        if (this.userCredentialRepository.existsUserCredentialEmail(credential.getEmail())) {
            throw new VirtualLedgerAuthenticationException("There already exists an account with this Email address.");
        }

        this.bankingApiFacade.createUser(credential.getEmail());
        this.userCredentialRepository.createUserCredential(credential);

        return "You were registered! " + credential.getEmail();
    }

    public SessionData login(final LoginData loginData) throws InvalidCredentialsException {
        final boolean valid = userCredentialRepository.checkLogin(loginData);
        if(!valid) {
            throw new InvalidCredentialsException();
        }
        final String sessionId = sessionIdGenerator.generate();
        userCredentialRepository.persistSessionId(loginData.email, sessionId);

        final SessionData result = new SessionData();
        result.setEmail(loginData.email);
        result.setSessionid(sessionId);
        return result;
    }

    public void logout(final String email)
    {
        userCredentialRepository.deleteSessionIdsByEmail(email);
    }
}
