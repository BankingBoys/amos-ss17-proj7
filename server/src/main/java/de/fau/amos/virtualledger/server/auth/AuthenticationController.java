package de.fau.amos.virtualledger.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiFacade;
import de.fau.amos.virtualledger.server.model.UserCredential;
import de.fau.amos.virtualledger.server.persistence.UserCredentialRepository;

/**
 * Controller / Service class for authentication
 */
@Component

public class AuthenticationController {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private SessionIdGenerator sessionIdGenerator;

    @Autowired
    private BankingApiFacade bankingApiFacade;

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
                || credential.getFirstname() == null || credential.getLastname() == null) { // if
                                                                                            // not
                                                                                            // null,
                                                                                            // matches
                                                                                            // the
                                                                                            // pattern
                                                                                            // ->
                                                                                            // specified
                                                                                            // in
                                                                                            // model
                                                                                            // class
            throw new VirtualLedgerAuthenticationException(
                    "Please check your inserts! At least one was not formatted correctly!");
        }
        if (this.getUserCredentialRepository().existsUserCredentialEmail(credential.getEmail())) {
            throw new VirtualLedgerAuthenticationException("There already exists an account with this Email address.");
        }
        this.getBankingApiFacade().createUser(credential.getEmail());
        this.getUserCredentialRepository().createUserCredential(credential);

        return "You were registered! " + credential.getEmail();
    }

    public SessionData login(final LoginData loginData) throws InvalidCredentialsException {
        final boolean valid = getUserCredentialRepository().checkLogin(loginData);
        if (!valid) {
            throw new InvalidCredentialsException();
        }
        final String sessionId = getSessionIdGenerator().generate();
        getUserCredentialRepository().persistSessionId(loginData.getEmail(), sessionId);

        final SessionData result = new SessionData();
        result.setEmail(loginData.getEmail());
        result.setSessionid(sessionId);
        return result;
    }

    public void logout(final String email) {
        getUserCredentialRepository().deleteSessionIdsByEmail(email);
    }

    public UserCredentialRepository getUserCredentialRepository() {
        return userCredentialRepository;
    }

    public void setUserCredentialRepository(UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }

    public SessionIdGenerator getSessionIdGenerator() {
        return sessionIdGenerator;
    }

    public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
        this.sessionIdGenerator = sessionIdGenerator;
    }

    public BankingApiFacade getBankingApiFacade() {
        return bankingApiFacade;
    }

    public void setBankingApiFacade(BankingApiFacade bankingApiFacade) {
        this.bankingApiFacade = bankingApiFacade;
    }
}
