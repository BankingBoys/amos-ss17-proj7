package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.server.model.DeletedBankAccess;
import de.fau.amos.virtualledger.server.model.DeletedBankAccount;

import javax.enterprise.context.RequestScoped;

/**
 * Created by Georg on 22.05.2017.
 */
@RequestScoped
public class DeletedBankAccountFactory {

    public DeletedBankAccount createDeletedBankAccount(String email, String bankAccessId, String bankAccountId)
    {
        DeletedBankAccount deletedBankAccount = new DeletedBankAccount();
        deletedBankAccount.userEmail = email;
        deletedBankAccount.bankAccessId = bankAccessId;
        deletedBankAccount.bankAccountId = bankAccountId;

        return deletedBankAccount;
    }
}
