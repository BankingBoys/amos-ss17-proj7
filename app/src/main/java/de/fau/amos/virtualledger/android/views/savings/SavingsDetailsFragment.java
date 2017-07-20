package de.fau.amos.virtualledger.android.views.savings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;

/**
 * Created by sebastian on 20.07.17.
 */

public class SavingsDetailsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.savings_account_details, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

}
