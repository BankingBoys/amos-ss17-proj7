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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.dtos.Contact;
import de.fau.amos.virtualledger.dtos.SavingsAccount;
import de.fau.amos.virtualledger.android.views.savings.add.AddSavingsAccountActivity;
import de.fau.amos.virtualledger.android.views.shared.transactionList.DataListening;
import de.fau.amos.virtualledger.android.views.shared.transactionList.Supplier;
import de.fau.amos.virtualledger.dtos.SavingsAccountSubGoal;

public class SavingAccountsFragment extends Fragment implements DataListening {
    @SuppressWarnings("unused")
    private final String TAG = this.getClass().getSimpleName();


    private SavingAccountsAdapter adapter;
    private ListView savingsAccountList;
    private Supplier<SavingsAccount> savingsSupplier;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);

        this.savingsSupplier = new SavingsSupplier(getActivity());
        this.savingsSupplier.addDataListeningObject(this);
        adapter = new SavingAccountsAdapter(getActivity(), R.id.savings_account_list, savingsSupplier.getAll());
        savingsAccountList.setAdapter(adapter);
        this.adapter.sort(new SavingsComparator());
        this.savingsSupplier.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View mainView = inflater.inflate(R.layout.saving_accounts_list, container, false);

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
    public void notifyDataChanged() {
        this.adapter.clear();
        List<SavingsAccount> allSavingAccounts = this.savingsSupplier.getAll();
        logger().info("Refreshing savings overview with " + allSavingAccounts.size() + " accounts from"+this.savingsSupplier);


        allSavingAccounts.add(new SavingsAccount("", "My Goal", 300, 400, new Date(), new Date()));//FIXME delete sample data
        SavingsAccount account = new SavingsAccount("", "My Goal2", 300, 400, new Date(), new Date());
        account.setSubGoals(new ArrayList<SavingsAccountSubGoal>());
        SavingsAccountSubGoal subgoal1 = new SavingsAccountSubGoal();
        subgoal1.setName("TestName");
        subgoal1.setAmount(100.00);
        account.getSubGoals().add(subgoal1);
        SavingsAccountSubGoal subgoal2 = new SavingsAccountSubGoal();
        subgoal2.setName("TestName2");
        subgoal2.setAmount(200.00);
        account.getSubGoals().add(subgoal2);
        account.getAdditionalAssignedUsers().add(new Contact("", "Bernd", "Brot"));
        account.getAdditionalAssignedUsers().add(new Contact("", "Karin", "Kresse"));

        allSavingAccounts.add(account);//FIXME delete sample data

        this.adapter.addAll(allSavingAccounts);
        this.adapter.notifyDataSetChanged();
        this.adapter.sort(new SavingsComparator());
    }

    @Override
    public void onPause() {
        super.onPause();
        this.savingsSupplier.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.savingsSupplier.onResume();
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.toString() + "}");
    }
}
