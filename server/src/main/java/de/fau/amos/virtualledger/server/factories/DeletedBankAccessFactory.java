package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.server.model.DeletedBankAccess;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.enterprise.context.RequestScoped;


@Component
@Scope("request")
public class DeletedBankAccessFactory {

    public DeletedBankAccess createDeletedBankAccess(String email, String bankAccessId)
    {
        DeletedBankAccess deletedBankAccess = new DeletedBankAccess();
        deletedBankAccess.userEmail = email;
        deletedBankAccess.bankAccessId = bankAccessId;

        return deletedBankAccess;
    }
}
