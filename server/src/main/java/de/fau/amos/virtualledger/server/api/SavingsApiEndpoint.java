package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.server.auth.KeycloakUtilizer;
import de.fau.amos.virtualledger.server.model.BankAccountIdentifier;
import de.fau.amos.virtualledger.server.model.SavingsAccountAddWrapper;
import de.fau.amos.virtualledger.server.model.SavingsAccountEntity;
import de.fau.amos.virtualledger.server.savings.SavingsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Endpoints for savings
 */
@RestController
public class SavingsApiEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private KeycloakUtilizer keycloakUtilizer;
    private SavingsController savingsController;

    @Autowired
    public SavingsApiEndpoint(KeycloakUtilizer keycloakUtilizer, SavingsController savingsController) {
        this.keycloakUtilizer = keycloakUtilizer;
        this.savingsController = savingsController;
    }


    /**
     * Gets all available saving accounts to the authenticated user
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "api/savings", produces = "application/json")
    public ResponseEntity<?> getSavingAccountsEndpoint() throws ServletException {
        String username = keycloakUtilizer.getEmail();

        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your email wasn't found.", HttpStatus.FORBIDDEN);
        }
        LOGGER.info("getSavingAccountsEndpoint of " + username + " was requested");

        return this.getSavingAccounts(username);
    }

    /**
     * Adds a saving accounts to the authenticated user
     *
     * @return status 201 if successful
     */
    @RequestMapping(method = RequestMethod.POST, value = "api/savings", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addSavingAccountEndpoint(@RequestBody SavingsAccountAddWrapper savingsAccountAddWrapper) throws ServletException {
        String username = keycloakUtilizer.getEmail();

        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your email wasn't found.", HttpStatus.FORBIDDEN);
        }

        SavingsAccountEntity savingsAccountEntity = savingsAccountAddWrapper.getSavingsAccountEntity();
        List<BankAccountIdentifier> bankAccountIdentifierList = savingsAccountAddWrapper.getBankAccountIdentifierList();
        final List<String> usersEmails = savingsAccountAddWrapper.getUsersEmails();
        if (savingsAccountEntity == null || savingsAccountEntity.getName() == null || savingsAccountEntity.getName().isEmpty()
                || savingsAccountEntity.getFinaldate() == null
                        /*|| bankAccountIdentifierList == null
                || bankAccountIdentifierList.size() == 0*/
                ) {
            return new ResponseEntity<>(
                    "Please check your inserted values. None of the parameters must be null or empty except id. Id must not been set!",
                    HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("addSavingAccountEndpoint of " + username + " was requested");

        return this.addSavingAccount(username, savingsAccountEntity, bankAccountIdentifierList, usersEmails);
    }

    /**
     * Does the logic for adding a saving account to a specific user. Handles
     * exceptions and returns corresponding response codes.
     *
     * @param username
     * @param savingsAccountEntity
     * @param usersEmails
     * @return status 201 if successful
     */
    private ResponseEntity<?> addSavingAccount(String username, SavingsAccountEntity savingsAccountEntity, List<BankAccountIdentifier> bankAccountIdentifierList, List<String> usersEmails) {

        savingsController.addSavingAccount(username, savingsAccountEntity, bankAccountIdentifierList, usersEmails);
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

        List<SavingsAccountEntity> savingsAccountEntityList = savingsController.getSavingAccounts(username);
        return new ResponseEntity<Object>(savingsAccountEntityList, HttpStatus.OK);
    }

}
