package de.fau.amos.virtualledger.server.bank;

import de.fau.amos.virtualledger.server.model.BankAccess;


import de.fau.amos.virtualledger.server.model.BankAccount;
import de.fau.amos.virtualledger.server.persistence.BankAccessRepository;
import de.fau.amos.virtualledger.server.persistence.BankAccountRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 * Controller / Service class for BankAccounts and Access
 */
@RequestScoped
public class BankController {

    /**
     *
     */
    @Inject
    BankAccessRepository bankAccessRepository;

    @Inject
    BankAccountRepository bankAccountRepository;



    /**
     * create new  bank Access , if attributes are null or don't follow the specific pattern, an exception is thrown
     * @param access
     * @return
     * @throws InvalidBanksException
     */
    public String createAccess(BankAccess access) throws InvalidBanksException {
        if (access == null || access.getBankName() == null ||  access.getBankLogin()==null
                || access.getBankPassword()==null || access.getBic()==null || access.getUserId()==0 )  { // if not null, matches the pattern -> specified in model class
            throw new InvalidBanksException("missing some inputs ");
        }


        this.bankAccessRepository.createAccess(access);

        return "You have add this BankAccess  " + access.getBankName();
    }


    /**
     * delete bank Access , if attributes are null or don't follow the specific pattern, an exception is thrown
     * @param access
     * @return
     * @throws InvalidBanksException
     */
    public String deleteAccess(BankAccess access) throws InvalidBanksException{

        if (access ==  null)  { // if not null, matches the pattern -> specified in model class
            throw new InvalidBanksException("missing some inputs ");
        }

        bankAccessRepository.deleteAccess(access);

        return "You Had delete Access Successfully ";

    }

/**
 * create new  bank Account , if attributes are null or don't follow the specific pattern, an exception is thrown
 * @param account
 * @return
 * @throws InvalidBanksException
 */
    public  String createAccount(BankAccount account) throws InvalidBanksException{

        if (account == null || account.getAccountName() == null ||  account.getAccountAccessId()==0)  { // if not null, matches the pattern -> specified in model class
            throw new InvalidBanksException("missing some inputs ");
        }
        //  if (this.bankAccesslRepository.existsUserCredentialEmail(credential.getEmail())) {
        // throw new VirtualLedgerAuthenticationException("There already exists an account with this Email address.");
        //}

        this.bankAccountRepository.createAccount(account);

        return "You were add this account : " + account.getAccountName();


    }

    /**
     * delete bank Account , if attributes are null or don't follow the specific pattern, an exception is thrown
     * @param account
     * @return
     * @throws InvalidBanksException
     */
    public String deleteAccount(BankAccount account) throws InvalidBanksException{

        if (account ==  null)  { // if not null, matches the pattern -> specified in model class
            throw new InvalidBanksException("missing some inputs ");
        }

        bankAccessRepository.deleteAccount(account);

        return "You Had delete Account Successfully ";

    }

}
