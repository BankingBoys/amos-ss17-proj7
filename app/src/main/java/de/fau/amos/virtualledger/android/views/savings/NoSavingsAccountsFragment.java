package de.fau.amos.virtualledger.android.views.savings;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.SavingsAccountsDataManager;
import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.views.savings.add.AddSavingsAccountActivity;

/**
 * Created by Simon on 24.07.2017.
 */

public class NoSavingsAccountsFragment extends Fragment implements Observer {

    Button addButton;
    @Inject
    SavingsAccountsDataManager savingsAccountsDataManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
        addButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent addSavingsActitivy = new Intent(getActivity(), AddSavingsAccountActivity.class);
                        startActivity(addSavingsActitivy);
                    }
                }
        );

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_no_contacts_saved, container, false);
        addButton = (Button) view.findViewById(R.id.contacts_add_button);
        addButton.setText(R.string.saving_accounts_app_bar_add);
        TextView textView = (TextView) view.findViewById(R.id.contacts_no_contacts_saved_text_view);
        textView.setText(R.string.saving_accounts_no_accounts_added);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        savingsAccountsDataManager.addObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        savingsAccountsDataManager.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            if(savingsAccountsDataManager.getAll().size() > 0) {
                getActivity().getFragmentManager().popBackStack();
            }
        } catch (SyncFailedException e) {
            Toast.makeText(getActivity(), "Error in getting Savings", Toast.LENGTH_SHORT).show();
        }
    }
}
