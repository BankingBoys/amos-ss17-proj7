package de.fau.amos.virtualledger.server.factories;

import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.server.banking.model.BankAccountBankingModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

public class BankAccountFactory {

    public BankAccount createBankAccount(BankAccountBankingModel bankingModel) {
        double balance = 0;
        if (bankingModel.getBankAccountBalance() != null) {
            balance = bankingModel.getBankAccountBalance().getReadyHbciBalance();
        }

        BankAccount bankAccount = new BankAccount(bankingModel.getId(), bankingModel.getType() != null ? bankingModel.getType() : "", balance);
        return bankAccount;
    }

    public List<BankAccount> createBankAccounts(List<BankAccountBankingModel> bankingModelList) {
        List<BankAccount> bankAccountsResult = new ArrayList<BankAccount>();

        for (BankAccountBankingModel bankingModel : bankingModelList) {
            bankAccountsResult.add(this.createBankAccount(bankingModel));
        }
        return bankAccountsResult;
    }
}
