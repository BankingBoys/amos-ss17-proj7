package de.fau.amos.virtualledger.server.factories;

import org.springframework.stereotype.Component;

import de.fau.amos.virtualledger.server.model.DeletedBankAccess;


@Component

public class DeletedBankAccessFactory {

    public DeletedBankAccess createDeletedBankAccess(String email, String bankAccessId)
    {
        DeletedBankAccess deletedBankAccess = new DeletedBankAccess();
        deletedBankAccess.setUserEmail(email);
        deletedBankAccess.setBankAccessId(bankAccessId);

        return deletedBankAccess;
    }
}
