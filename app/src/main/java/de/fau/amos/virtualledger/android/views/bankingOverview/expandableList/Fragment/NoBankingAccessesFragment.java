package de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.views.bankingOverview.addBankAccess.AddBankAccessActivity;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.data.SyncFailedException;

/**
 * Created by Simon on 21.05.2017.
 */

public class NoBankingAccessesFragment extends Fragment implements Observer{

    Button addButton;
    @Inject
    BankingDataManager bankingDataManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
        addButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AddBankAccessActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().getFragmentManager().popBackStack();
                    }
                }
        );

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.banking_overview_no_bank_accesses, container, false);
        addButton = (Button) view.findViewById(R.id.banking_overview_no_bank_access_button_id);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bankingDataManager.addObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        bankingDataManager.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            if(bankingDataManager.getBankAccesses().size() > 0) {
                getActivity().getFragmentManager().popBackStack();
            }
        } catch (SyncFailedException e) {
            Toast.makeText(getActivity(), "Error in getting Bank accesses", Toast.LENGTH_SHORT).show();
        }
    }
}
