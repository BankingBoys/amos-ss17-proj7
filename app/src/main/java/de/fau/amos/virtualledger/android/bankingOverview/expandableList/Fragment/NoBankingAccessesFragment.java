package de.fau.amos.virtualledger.android.bankingOverview.expandableList.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.authentication.login.LoginActivity;
import de.fau.amos.virtualledger.android.bankingOverview.addBankAccess.AddBankAccessActivity;

/**
 * Created by Simon on 21.05.2017.
 */

public class NoBankingAccessesFragment extends Fragment {

    Button addButton;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AddBankAccessActivity.class);
                        getActivity().startActivity(intent);
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
}
