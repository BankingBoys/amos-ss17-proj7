package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.server.model.DeletedBankAccess;

import javax.enterprise.context.RequestScoped;

/**
 * Created by Georg on 22.05.2017.
 */
@RequestScoped
public class DeletedBankAccessFactory {

    public DeletedBankAccess createDeletedBankAccess(String email, String bankAccessId)
    {
        DeletedBankAccess deletedBankAccess = new DeletedBankAccess();
        deletedBankAccess.userEmail = email;
        deletedBankAccess.bankAccessId = bankAccessId;

        return deletedBankAccess;
    }
}
