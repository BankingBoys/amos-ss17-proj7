package de.fau.amos.virtualledger.server.banking;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.dtos.BankAccountSync;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.factories.*;
import de.fau.amos.virtualledger.server.banking.adorsys.api.BankingApiFacade;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.model.DeletedBankAccess;
import de.fau.amos.virtualledger.server.model.DeletedBankAccount;
import de.fau.amos.virtualledger.server.persistence.DeletedBankAccessesRepository;
import de.fau.amos.virtualledger.server.persistence.DeletedBankAccountsRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georg on 20.05.2017.
 */
@RequestScoped
public class BankingOverviewController {

    @Inject
    BankingApiFacade bankingApiFacade;

    @Inject
    BankAccountFactory bankAccountFactory;

    @Inject
    BankAccessBankingModelFactory bankAccessBankingModelFactory;

    @Inject
    BankAccessFactory bankAccessFactory;

    @Inject
    DeletedBankAccessesRepository deletedBankAccessesRepository;

    @Inject
    DeletedBankAccountsRepository deletedBankAccountRepository;

    @Inject
    DeletedBankAccessFactory deletedBankAccessFactory;

    @Inject
    DeletedBankAccountFactory deletedBankAccountFactory;

    /**
     * loads all the bank accesses and accounts embedded matching to the email from adorsys api (or from dummies, depending on configuration)
     * also filters the received data to only return the not deleted ones
     * @param email
     * @return
     * @throws BankingException
     */
    public List<BankAccess> getBankingOverview(String email) throws BankingException
    {
        List<BankAccessBankingModel> bankingModelList = bankingApiFacade.getBankAccesses(email);
        List<BankAccess> bankAccessesList = bankAccessFactory.createBankAccesses(bankingModelList);

        filterBankAccessWithDeleted(email, bankAccessesList);

        for(BankAccess bankAccess: bankAccessesList)
        {
            List<BankAccount> bankAccounts = this.getBankingAccounts(email, bankAccess.getId());
            filterBankAccountsWithDeleted(email, bankAccess.getId(), bankAccounts);
            bankAccess.setBankaccounts(bankAccounts);
        }

        return bankAccessesList;
    }

    /**
     * adds a bank access, uses adorsys api if configured to store on user with email as username
     * @param email
     * @param bankAccessCredential
     * @throws BankingException
     */
    public void addBankAccess(String email, BankAccessCredential bankAccessCredential) throws BankingException
    {
        BankAccessBankingModel bankAccessBankingModel = bankAccessBankingModelFactory.createBankAccessBankingModel(email, bankAccessCredential);
        bankingApiFacade.addBankAccess(email, bankAccessBankingModel);
    }

    /**
     * deletes a bank access.
     * In fact, no real delete is done, but entries are made into db so that filtering works when getBankingOverview is called.
     * @param email
     * @param bankAccessId
     * @throws BankingException
     */
    public void deleteBankAccess(String email, String bankAccessId) throws BankingException
    {
        DeletedBankAccess deletedBankAccess = deletedBankAccessFactory.createDeletedBankAccess(email, bankAccessId);
        deletedBankAccessesRepository.createDeletedBankAccess(deletedBankAccess);
    }

    /**
     * deletes a bank account.
     * In fact, no real delete is done, but entries are made into db so that filtering works when getBankingOverview is called.
     * @param email
     * @param bankAccessId
     * @param bankAccountId
     * @throws BankingException
     */
    public void deleteBankAccount(String email, String bankAccessId, String bankAccountId) throws BankingException
    {
        DeletedBankAccount deletedBankAccount = deletedBankAccountFactory.createDeletedBankAccount(email, bankAccessId, bankAccountId);
        deletedBankAccountRepository.createDeletedBankAccount(deletedBankAccount);
    }

    public void syncBankAccounts(String email, List<BankAccountSync> bankAccountSyncList) throws BankingException
    {
        for(BankAccountSync bankAccountSync: bankAccountSyncList)
        {
            bankingApiFacade.syncBankAccount(email, bankAccountSync.getBankaccessid(), bankAccountSync.getBankaccountid(), bankAccountSync.getPin());
        }
    }

    /**
     * loads all the bank accounts matching to the email from adorsys api (or from dummies, depending on configuration)
     * also filters the received data to only return the not deleted ones
     * @param email
     * @param bankAccesId
     * @return
     * @throws BankingException
     */
    private List<BankAccount> getBankingAccounts(String email, String bankAccesId) throws BankingException
    {
        List<BankAccountBankingModel> bankingModel = bankingApiFacade.getBankAccounts(email, bankAccesId);
        List<BankAccount> bankAccounts = bankAccountFactory.createBankAccounts(bankingModel);
        return bankAccounts;
    }

    /**
     * filters a list of bank accesses with the ones in database that are marked as deleted
     * @param email
     * @param bankAccessList
     */
    private void filterBankAccessWithDeleted(String email, List<BankAccess> bankAccessList)
    {
        List<DeletedBankAccess> deletedAccessList = deletedBankAccessesRepository.getDeletedBankAccessIdsByEmail(email);

        List<BankAccess> foundBankAccesses = new ArrayList<BankAccess>();
        for (DeletedBankAccess deletedAccess: deletedAccessList) {
            for (BankAccess bankAccess: bankAccessList) {
                if(bankAccess.getId().equals(deletedAccess.bankAccessId)) {
                    foundBankAccesses.add(bankAccess);
                }
            }
        }
        bankAccessList.removeAll(foundBankAccesses);
    }

    /**
     * filters a list of bank accounts with the ones in database that are marked as deleted
     * @param email
     * @param bankAccessId
     * @param bankAccountList
     */
    private void filterBankAccountsWithDeleted(String email, String bankAccessId, List<BankAccount> bankAccountList)
    {
        List<DeletedBankAccount> deletedAccountList = deletedBankAccountRepository.getDeletedBankAccountIdsByEmailAndAccessId(email, bankAccessId);

        List<BankAccount> foundBankAccounts = new ArrayList<BankAccount>();
        for (DeletedBankAccount deletedAccount: deletedAccountList) {
            for (BankAccount bankAccount: bankAccountList) {
                if(bankAccount.getBankid().equals(deletedAccount.bankAccountId)) {
                    foundBankAccounts.add(bankAccount);
                }
            }
        }
        bankAccountList.removeAll(foundBankAccounts);
    }
}
