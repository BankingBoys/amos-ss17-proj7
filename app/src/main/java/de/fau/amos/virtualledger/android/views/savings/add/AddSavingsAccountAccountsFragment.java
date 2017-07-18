package de.fau.amos.virtualledger.android.views.savings.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.SyncStatus;
import de.fau.amos.virtualledger.dtos.SavingsAccount;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

public class AddSavingsAccountAccountsFragment extends AddSavingsAccountPage {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountAccountsFragment.class.getSimpleName();
    private BankAccountSelectedListener bankAccountSelectedListener;
    private BankAccount bankAccount;
    private BankAccountAdapter adapter;

    @Inject
    BankingDataManager bankingDataManager;

    @OnClick(R.id.add_savings_account_button_accounts)
    public void onClickChooseAccountsButton() {
        //TODO
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
        final View view = inflater.inflate(R.layout.saving_accounts_add_fragment_accounts, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void fillInData(final SavingsAccount addSavingsAccountResult) {
        //TODO
    }

    @NonNull
    private ArrayList<BankAccount> getBankAccounts() {
        ArrayList<BankAccount> allBankAccounts = new ArrayList<>();
        List<BankAccess> accesses = new ArrayList<>();
        if(bankingDataManager.getSyncStatus() == SyncStatus.SYNCED) {
            try {
                accesses  = bankingDataManager.getBankAccesses();
            } catch (Exception e) {
                logger().info(TAG + " could not get bank accesses");
            }
            for(BankAccess bankAccess: accesses) {
                allBankAccounts.addAll(bankAccess.getBankaccounts());
            }
        }
        return allBankAccounts;
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName());
    }
}
