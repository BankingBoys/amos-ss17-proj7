package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.server.model.DeletedBankAccess;
import de.fau.amos.virtualledger.server.model.DeletedBankAccount;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.enterprise.context.RequestScoped;


@Component
@Scope("request")
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
