package de.fau.amos.virtualledger.android.views.shared.totalAmount;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.BankingSyncFailedException;
import de.fau.amos.virtualledger.android.views.shared.transactionList.ItemCheckedMap;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * Created by Georg on 09.06.2017.
 */

public class TotalAmountFragment extends Fragment implements Observer{

    private static final String TAG = TotalAmountFragment.class.getSimpleName();

    private ItemCheckedMap itemCheckedMap = new ItemCheckedMap(new HashMap<String, Boolean>());

    @BindView(R.id.total_amount_balance)
    TextView totalBalanceText;

    @Inject
    BankingDataManager bankingDataManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shared_total_amount, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bankingDataManager.addObserver(this);

        switch (bankingDataManager.getSyncStatus()) {
            case NOT_SYNCED:
                bankingDataManager.sync();
                break;
            case SYNCED:
                onBankingDataChanged();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        bankingDataManager.deleteObserver(this);
    }

    private void onBankingDataChanged() {
        try {
            List<BankAccess> bankAccessList = bankingDataManager.getBankAccesses();
            double totalAmountSum = computeBalanceOfCheckedAccounts();
            setTotalBalance(totalAmountSum);
        } catch (BankingSyncFailedException ex) {
            Toast.makeText(getActivity(), "Failed connecting to the server, try again later", Toast.LENGTH_LONG).show();
        }
    }

    private double computeBalanceOfCheckedAccounts() {
        List<BankAccess> bankAccessList = new ArrayList<>();
        double filteredBalance = 0.0;
        try {
            bankAccessList = bankingDataManager.getBankAccesses();
        } catch (BankingSyncFailedException e) {
            return 0.0;
        }

        if (itemCheckedMap.hasItemsChecked()) {
            for (BankAccess bankAccess : bankAccessList) {
                for (BankAccount bankAccount : bankAccess.getBankaccounts()) {
                    if (this.itemCheckedMap.shouldBePresented(bankAccount.getBankid())) {
                        filteredBalance += bankAccount.getBalance();
                    }
                }
            }
        } else {
            for (BankAccess bankAccess : bankAccessList) {
                filteredBalance += bankAccess.getBalance();
            }
        }
        return filteredBalance;
    }

    private void changeColorOfBalance(double balance) {
        if (balance < 0) {
            int redColor = ContextCompat.getColor(this.getActivity(), R.color.colorNegativeAmount);
            totalBalanceText.setTextColor(redColor);
        } else if (balance == 0) {
            int blueColor = ContextCompat.getColor(this.getActivity(), R.color.colorBankingOverview);
            totalBalanceText.setTextColor(blueColor);
        } else {
            int greenColor = ContextCompat.getColor(this.getActivity(), R.color.colorBankingOverviewLightGreen);
            totalBalanceText.setTextColor(greenColor);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        onBankingDataChanged();
    }

    public void setTotalBalance(double totalBalance) {
        String bankBalanceString = String.format(Locale.GERMAN, "%.2f", totalBalance);
        changeColorOfBalance(totalBalance);
        totalBalanceText.setText(bankBalanceString);
    }

    public void setCheckedMap(ItemCheckedMap itemCheckedMap) {
        this.itemCheckedMap = itemCheckedMap;
    }
}
