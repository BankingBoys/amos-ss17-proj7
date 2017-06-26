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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.savings.SavingsProvider;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.SavingsAccountsDataManager;
import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import de.fau.amos.virtualledger.android.views.savings.add.AddSavingsAccountActivity;

public class SavingAccountsFragment extends Fragment implements Observer {
    @SuppressWarnings("unused")
    private final String TAG = this.getClass().getSimpleName();

    @Inject
    SavingsProvider savingsProvider;

    private ListAdapter adapter;
    private ListView savingsAccountList;
    private List<SavingsAccount> savingsList = new ArrayList<>();

    @Inject
    SavingsAccountsDataManager savingsAccountsDataManager;

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View mainView = inflater.inflate(R.layout.saving_accounts_list, container, false);

        this.savingsAccountList = (ListView) mainView.findViewById(R.id.savings_account_list);

        return mainView;
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

    @Override
    public void onResume() {
        super.onResume();
        savingsAccountsDataManager.addObserver(this);

        switch (savingsAccountsDataManager.getSyncStatus()) {
            case NOT_SYNCED:
                savingsAccountsDataManager.sync();
                break;
            case SYNCED:
                onDataChanged();
                break;
        }
    }

    @Override
    public void update(final Observable o, final Object arg) {
        onDataChanged();
    }

    private void onDataChanged() {
        try {
            savingsList = savingsAccountsDataManager.getSavingsAccounts();
            adapter = new SavingAccountsAdapter(getActivity(), R.id.savings_account_list, savingsList);
            savingsAccountList.setAdapter(adapter);
        } catch (final SyncFailedException e) {
            Toast.makeText(getActivity(), "Failed connecting to the server, try again later", Toast.LENGTH_LONG).show();
        }
    }
}
