package de.fau.amos.virtualledger.server.savings;

import de.fau.amos.virtualledger.server.model.SavingsAccount;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class SavingsController {


    List<SavingsAccount> savingsAccountList = new ArrayList<>();

    public List<SavingsAccount> getSavingAccounts(String email) {

        return savingsAccountList;
    }

    public void addSavingAccount(String email, SavingsAccount savingsAccount) throws SavingsException {

        savingsAccount.setId("SavingsAccountId" + savingsAccountList.size());
        savingsAccountList.add(savingsAccount);
    }
}

