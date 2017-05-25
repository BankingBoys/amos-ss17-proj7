package de.fau.amos.virtualledger.android.bankingOverview.expandableList.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fau.amos.virtualledger.R;

/**
 * Created by Simon on 21.05.2017.
 */

public class NoBankingAccessesFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.no_bank_accesses, container, false);
        return view;
    }
}
