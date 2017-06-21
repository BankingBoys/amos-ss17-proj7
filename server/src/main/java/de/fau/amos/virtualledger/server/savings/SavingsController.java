package de.fau.amos.virtualledger.server.savings;

import de.fau.amos.virtualledger.dtos.SavingsAccount;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class SavingsController {


    List<SavingsAccount> savingsAccountList = new ArrayList<>();

    public List<SavingsAccount> getSavingAccounts(String email) {

        return savingsAccountList;
    }

    private void addSavingAccount(String email, SavingsAccount savingsAccount) throws SavingsException {

        savingsAccountList.add(savingsAccount);
    }
}

