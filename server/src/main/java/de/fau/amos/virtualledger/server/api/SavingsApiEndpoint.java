package de.fau.amos.virtualledger.server.api;

import java.lang.invoke.MethodHandles;
import java.security.Principal;
import java.util.List;

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

import de.fau.amos.virtualledger.server.model.SavingsAccount;
import de.fau.amos.virtualledger.server.savings.SavingsController;

/**
 * Endpoints for savings
 */
@RestController
public class SavingsApiEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private SavingsController savingsController;

    @Autowired
    public SavingsApiEndpoint(SavingsController savingsController) {
        this.savingsController = savingsController;
    }

    protected SavingsApiEndpoint() {
    }

    /**
     * Gets all available saving accounts to the authenticated user
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "api/savings", produces = "application/json")
    public ResponseEntity<?> getSavingAccountsEndpoint() {

        String username = ((Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your email wasn't found.", HttpStatus.FORBIDDEN);
        }
        LOGGER.info("getSavingAccountsEndpoint of " + username + " was requested");

        return this.getSavingAccounts(username);
    }

    /**
     * Adds a saving accounts to the authenticated user
     *
     * @param savingsAccount
     * @return status 201 if successful
     */
    @RequestMapping(method = RequestMethod.POST, value = "api/savings", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addSavingAccountEndpoint(@RequestBody SavingsAccount savingsAccount) {

        String username = ((Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your email wasn't found.", HttpStatus.FORBIDDEN);
        }
        if (savingsAccount == null || savingsAccount.name == null || savingsAccount.name.isEmpty()
                || savingsAccount.finaldate == null) {
            return new ResponseEntity<>(
                    "Please check your inserted values. None of the parameters must be null or empty except id. Id must not been set!",
                    HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("getSavingAccountsEndpoint of " + username + " was requested");

        return this.addSavingAccount(username, savingsAccount);
    }

    /**
     * Does the logic for adding a saving account to a specific user. Handles
     * exceptions and returns corresponding response codes.
     * 
     * @param username
     * @param savingsAccount
     * @return status 201 if successful
     */
    private ResponseEntity<?> addSavingAccount(String username, SavingsAccount savingsAccount) {

        savingsController.addSavingAccount(username, savingsAccount);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Does the logic for getting all saving accounts to a specific user.
     * Handles exceptions and returns corresponding response codes.
     * 
     * @param username
     * @return
     */
    private ResponseEntity<?> getSavingAccounts(String username) {

        List<SavingsAccount> savingsAccountList = savingsController.getSavingAccounts(username);
        return new ResponseEntity<Object>(savingsAccountList, HttpStatus.OK);
    }

}
