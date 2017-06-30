package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.LoginData;
import de.fau.amos.virtualledger.dtos.SessionData;
import de.fau.amos.virtualledger.dtos.StringApiModel;
import de.fau.amos.virtualledger.server.auth.AuthenticationController;
import de.fau.amos.virtualledger.server.auth.InvalidCredentialsException;
import de.fau.amos.virtualledger.server.auth.VirtualLedgerAuthenticationException;
import de.fau.amos.virtualledger.server.factories.StringApiModelFactory;
import de.fau.amos.virtualledger.server.model.UserCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.security.Principal;

/**
 * Endpoints for authentication / authorization
 */
@RestController
public class AuthApiEndpoint {
    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    AuthenticationController authenticationController;
    StringApiModelFactory stringApiModelFactory;

    @Autowired
    public AuthApiEndpoint(AuthenticationController authenticationController, StringApiModelFactory stringApiModelFactory) {
        this.authenticationController = authenticationController;
        this.stringApiModelFactory = stringApiModelFactory;
    }
    protected AuthApiEndpoint() { }


    /**
     * Endpoint for registering a new user. Parameters must not be null or empty, id has to be null or 0.
     * @param credential
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value =  "api/auth/register", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> registerEndpoint(@RequestBody UserCredential credential) {
    	if(credential.getEmail() == null || credential.getEmail().isEmpty() ||
                credential.getPassword() == null || credential.getPassword().isEmpty() ||
                credential.getFirstname() == null || credential.getFirstname().isEmpty() ||
                credential.getLastname() == null || credential.getLastname().isEmpty() ||
                credential.getId() != 0)
        {
            return new ResponseEntity<>("Please check your inserted values. None of the parameters must be null or empty, id has to be 0.", HttpStatus.BAD_REQUEST);
        }
        logger.info("Registration for " + credential.getEmail() + " was requested.");

        return this.register(credential);
    }

    /**
     * Endpoint for logging in. Parameters must not be null or empty.
     * @param loginData
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value =  "api/auth/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> loginEndpoint(@RequestBody LoginData loginData) {
        if(loginData.getEmail() == null || loginData.getEmail().isEmpty() ||
                loginData.getPassword() == null || loginData.getPassword().isEmpty())
        {
            return new ResponseEntity<>("Please check your inserted values. None of the parameters must be null or empty.", HttpStatus.BAD_REQUEST);
        }
        logger.info("Login of "+ loginData.getEmail() +" was requested.");
        return this.login(loginData);
    }

    /**
     * Endpoint for logging out. User must be authenticated.
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value =  "api/auth/logout", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> logoutEndpoint()
    {
        String username = ((Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
        if(username == null || username.isEmpty())
        {
            return new ResponseEntity<>("Authentication failed! Your email wasn't found.", HttpStatus.FORBIDDEN);
        }
        logger.info("Logout of " + username + " was requested");

        return this.logout(username);
    }


    /**
     * Does the logic operation for registering the user.
     * Also does exception handling.
     * @param credential
     * @return a response with status code depending on result
     */
    private ResponseEntity<?> register(UserCredential credential)
    {
        String responseMsg;
        try
        {
            responseMsg = authenticationController.register(credential);
        } catch(VirtualLedgerAuthenticationException ex)
        {
            logger.error("", ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        StringApiModel responseObj = stringApiModelFactory.createStringApiModel(responseMsg);
        return new ResponseEntity<>(responseObj, HttpStatus.OK);
    }

    /**
     * Does the logic operation for logging in a user.
     * Also does exception handling.
     * @param loginData
     * @return
     */
    private ResponseEntity<?> login(LoginData loginData)
    {
        final SessionData sessionData;
        try {
            sessionData = authenticationController.login(loginData);
        } catch (InvalidCredentialsException ex) {
            logger.error("", ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(sessionData, HttpStatus.OK);
    }

    /**
     * Does the logic operation for logging out a user
     * @param username
     * @return
     */
    private ResponseEntity<?> logout(String username)
    {
        authenticationController.logout(username);
        return new ResponseEntity<>(stringApiModelFactory.createStringApiModel("You were logged out! " + username), HttpStatus.OK);
    }
}
