package de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount;

import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.functions.BiConsumer;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Action for deletion of a BankAccount
 */
public class DeleteBankAccountAction implements BiConsumer<BankAccess, BankAccount> {

    private BankingDataManager bankingDataManager;

    public DeleteBankAccountAction(final BankingDataManager bankingDataManager) {
        this.bankingDataManager = bankingDataManager;
    }

    @Override
    public void accept(final BankAccess bankAccess,final BankAccount bankAccount) {
        bankingDataManager.deleteBankAccount(bankAccess.getId(), bankAccount.getBankid());

    }
}
