package de.fau.amos.virtualledger.android.views.savings;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.views.savings.add.AddSavingsAccountActivity;

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
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.saving_accounts_list, container, false);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.saving_accounts_app_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saving_accounts_app_bar_add:
                final Intent addSavingsAccountIntent = new Intent(getActivity(), AddSavingsAccountActivity.class);
                startActivity(addSavingsAccountIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
