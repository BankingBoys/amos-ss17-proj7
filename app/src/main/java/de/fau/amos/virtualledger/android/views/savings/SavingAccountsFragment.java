package de.fau.amos.virtualledger.android.views.savings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;

public class SavingAccountsFragment extends Fragment {
    @SuppressWarnings("unused")
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.saving_accounts_list, container, false);
    }

}
