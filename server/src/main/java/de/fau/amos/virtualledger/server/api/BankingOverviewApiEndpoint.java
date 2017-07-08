package de.fau.amos.virtualledger.server.api;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.dtos.BankAccountSyncResult;
import de.fau.amos.virtualledger.server.auth.KeycloakUtilizer;
import de.fau.amos.virtualledger.server.banking.BankingOverviewController;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletException;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Endpoints for basic banking logic
 */
@RestController
public class BankingOverviewApiEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private KeycloakUtilizer keycloakUtilizer;
    private BankingOverviewController bankingOverviewController;

    @Autowired
    public BankingOverviewApiEndpoint(KeycloakUtilizer keycloakUtilizer, BankingOverviewController bankingOverviewController) {
        this.keycloakUtilizer = keycloakUtilizer;
        this.bankingOverviewController = bankingOverviewController;
    }

    /**
     * Endpoint for getting banking overview data (bankaccesses + bankaccunts).
     * User must be authenticated.
     * 
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "api/banking", produces = "application/json")
    public ResponseEntity<?> getBankingOverviewEndpoint() throws ServletException {
        String username = keycloakUtilizer.getEmail();
        final String token = keycloakUtilizer.getTokenString();

        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your username wasn't found.", HttpStatus.FORBIDDEN);
        }
        LOGGER.info("getBankingOverviewEndpoint of " + username + " was requested");

        return this.getBankingOverview(username, token);
    }

    /**
     * Endpoint for adding a bank access. Sent parameters must not be null or
     * empty. User must be authenticated.
     * 
     * @param bankAccessCredential
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "api/banking", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addBankAccessEndpoint(@RequestBody BankAccessCredential bankAccessCredential) throws ServletException {
        String username = keycloakUtilizer.getEmail();
        final String token = keycloakUtilizer.getTokenString();

        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your username wasn't found.", HttpStatus.FORBIDDEN);
        }
        if (bankAccessCredential.getBankcode() == null//
                || bankAccessCredential.getBankcode().isEmpty()//
                || bankAccessCredential.getBanklogin() == null//
                || bankAccessCredential.getBanklogin().isEmpty()//
                || bankAccessCredential.getPin() == null//
                || bankAccessCredential.getPin().isEmpty()) {
            return new ResponseEntity<>(
                    "Please check your inserted values. None of the parameters must be null or empty.",
                    HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("addBankAccessEndpoint was requested by " + username);

        return this.addBankAccess(username, bankAccessCredential, token);
    }

    /**
     * Endpoint for deleting a bank access. bankAccessId must not be null or
     * empty. User must be authenticated.
     * 
     * @param bankAccessId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "api/banking/{accessId}", produces = "application/json")
    public ResponseEntity<?> deleteBankAccessEndpoint(@PathVariable("accessId") String bankAccessId) throws ServletException {
        String username = keycloakUtilizer.getEmail();

        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your username wasn't found.", HttpStatus.FORBIDDEN);
        }
        if (bankAccessId == null || bankAccessId.isEmpty()) {
            return new ResponseEntity<>(
                    "Please check your inserted values. None of the parameters must be null or empty.",
                    HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("deleteBankAccessEndpoint was requested by " + username);

        return this.deleteBankAccess(username, bankAccessId);
    }

    /**
     * Endpoint for deleting a bank account. bankAccessId and bankAccountId must
     * not be null or empty. User must be authenticated.
     * 
     * @param bankAccessId
     * @param bankAccountId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "api/banking/{accessId}/{accountId}", produces = "application/json")
    public ResponseEntity<?> deleteBankAccountEndpoint(@PathVariable("accessId") String bankAccessId,
            @PathVariable("accountId") String bankAccountId) throws ServletException {
        String username = keycloakUtilizer.getEmail();

        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your username wasn't found.", HttpStatus.FORBIDDEN);
        }
        if (bankAccessId == null || bankAccessId.isEmpty() || bankAccountId == null || bankAccountId.isEmpty()) {
            return new ResponseEntity<>(
                    "Please check your inserted values. None of the parameters must be null or empty.",
                    HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("deleteBankAccountEndpoint was requested by " + username);

        return this.deleteBankAccount(username, bankAccessId, bankAccountId);
    }

    /**
     * Endpoint for synchronizing bank accounts. bankAccountSyncList must not be
     * null, all BankAccountSync must contain parameters that are not null or
     * empty. User must be authenticated.
     * 
     * @param bankAccountSyncList
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "api/banking/sync", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> syncBankAccountsEndpoint(@RequestBody List<BankAccountSync> bankAccountSyncList) throws ServletException {
        String username = keycloakUtilizer.getEmail();

        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Authentication failed! Your username wasn't found.", HttpStatus.FORBIDDEN);
        }
        if (bankAccountSyncList == null) {
            return new ResponseEntity<>(
                    "Please check your inserted values. None of the parameters must be null or empty.",
                    HttpStatus.BAD_REQUEST);
        }
        for (BankAccountSync bankAccountSync : bankAccountSyncList) {
            if (bankAccountSync == null || bankAccountSync.getBankaccessid() == null
                    || bankAccountSync.getBankaccessid().isEmpty() || bankAccountSync.getBankaccountid() == null
                    || bankAccountSync.getBankaccountid().isEmpty() || bankAccountSync.getPin() == null
                    || bankAccountSync.getPin().isEmpty()) {
                return new ResponseEntity<>(
                        "Please check your inserted values. None of the parameters must be null or empty.",
                        HttpStatus.BAD_REQUEST);
            }
        }

        LOGGER.info("syncBankAccountsEndpoint was requested by " + username);

        return this.syncBankAccounts(username, bankAccountSyncList);
    }

    /**
     * Does the logic for getting the banking overview data. Handles exceptions
     * and returns corresponding response codes.
     * 
     * @param username
     * @return
     */
    private ResponseEntity<?> getBankingOverview(String username, final String token) {
        List<BankAccess> bankAccesses = null;
        try {
            bankAccesses = bankingOverviewController.getBankingOverview(username, token);
        } catch (BankingException ex) {
            LOGGER.error("", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bankAccesses, HttpStatus.OK);
    }

    /**
     * Does the logic for adding a bank access. Handles exceptions and returns
     * corresponding response codes.
     * 
     * @param username
     * @param bankAccessCredential
     * @return
     */
    private ResponseEntity<?> addBankAccess(String username, BankAccessCredential bankAccessCredential, final String token) {
        try {
            BankAccess addedBankAccess = bankingOverviewController.addBankAccess(username, bankAccessCredential, token);
            return new ResponseEntity<>(addedBankAccess, HttpStatus.CREATED);
        } catch (BankingException ex) {
            LOGGER.error("", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Does the logic for deleting a bank access. Handles exceptions and returns
     * corresponding response codes.
     * 
     * @param username
     * @param bankAccessId
     * @return
     */
    private ResponseEntity<?> deleteBankAccess(String username, String bankAccessId) {
        try {
            bankingOverviewController.deleteBankAccess(username, bankAccessId);
        } catch (BankingException ex) {
            LOGGER.error("", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Does the logic for deleting a bank account. Handles exceptions and
     * returns corresponding response codes.
     * 
     * @param username
     * @param bankAccessId
     * @param bankAccountId
     * @return
     */
    private ResponseEntity<?> deleteBankAccount(String username, String bankAccessId, String bankAccountId) {
        try {
            bankingOverviewController.deleteBankAccount(username, bankAccessId, bankAccountId);
        } catch (BankingException ex) {
            LOGGER.error("", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Does the logic for synchronizing a list of bank accounts. Handles
     * exceptions and returns corresponding response codes.
     * 
     * @param username
     * @param bankAccountSyncList
     * @return
     */
    private ResponseEntity<?> syncBankAccounts(String username, List<BankAccountSync> bankAccountSyncList) {
        try {
            final BankAccountSyncResult result = bankingOverviewController.syncBankAccounts(username,
                    bankAccountSyncList);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (BankingException ex) {
            LOGGER.error("", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
