package de.fau.amos.virtualledger.server.controllers;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccessCredential;
import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.server.banking.model.BankingException;
import de.fau.amos.virtualledger.server.factories.*;
import de.fau.amos.virtualledger.server.banking.api.BankingApiFacade;
import de.fau.amos.virtualledger.server.banking.model.BankAccessBankingModel;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import de.fau.amos.virtualledger.server.model.DeletedBankAccess;
import de.fau.amos.virtualledger.server.model.DeletedBankAccount;
import de.fau.amos.virtualledger.server.persistence.BankAccessRepository;
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

    public void addBankAccess(String email, BankAccessCredential bankAccessCredential) throws BankingException
    {
        BankAccessBankingModel bankAccessBankingModel = bankAccessBankingModelFactory.createBankAccessBankingModel(email, bankAccessCredential);
        bankingApiFacade.addBankAccess(email, bankAccessBankingModel);
    }

    public void deleteBankAccess(String email, String bankAccessId) throws BankingException
    {
        DeletedBankAccess deletedBankAccess = deletedBankAccessFactory.createDeletedBankAccess(email, bankAccessId);
        deletedBankAccessesRepository.createDeletedBankAccess(deletedBankAccess);
    }

    public void deleteBankAccount(String email, String bankAccessId, String bankAccountId) throws BankingException
    {
        DeletedBankAccount deletedBankAccount = deletedBankAccountFactory.createDeletedBankAccount(email, bankAccessId, bankAccountId);
        deletedBankAccountRepository.createDeletedBankAccount(deletedBankAccount);
    }


    private List<BankAccount> getBankingAccounts(String email, String bankAccesId) throws BankingException
    {
        List<BankAccountBankingModel> bankingModel = bankingApiFacade.getBankAccounts(email, bankAccesId);
        List<BankAccount> bankAccounts = bankAccountFactory.createBankAccounts(bankingModel);
        return bankAccounts;
    }

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
