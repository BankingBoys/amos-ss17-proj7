package de.fau.amos.virtualledger.android.views.menu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.authentication.oidc.OidcAuthenticationActivity;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Fragment.ExpandableBankFragment;
import de.fau.amos.virtualledger.android.views.contacts.ContactsFragment;
import de.fau.amos.virtualledger.android.views.savings.SavingAccountsFragment;
import de.fau.amos.virtualledger.android.views.settings.SettingsActivity;
import de.fau.amos.virtualledger.android.views.transactionOverview.TransactionOverviewFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainMenu.class.getSimpleName();

    public static final String EXTRA_STARTING_FRAGMENT = "EXTRA_STARTING_FRAGMENT";
    public static final String EXTRA_RECENT_ACTIVITY_ADD_ACCESS = "EXTRA_RECENT_ACTIVITY_ADD_ACCESS";

    private Map<String, Boolean> checkedBankAccounts = new HashMap<>();
    private boolean recentlyAddedAccess = false;

    public enum AppFragment {
        BANK_ACCESSES, TRANSACTION_OVERVIEW, SAVING_ACCOUNTS, CONTACTS
    }

    @Inject
    AuthenticationProvider authenticationProvider;
    @Inject
    BankingDataManager bankingDataManager;

    @BindView(R.id.main_menu_drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_menu_navigation_view)
    NavigationView navigationView;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getNetComponent().inject(this);

        setContentView(R.layout.main_menu_sliding_tab);
        ButterKnife.bind(this);

        initializeDrawer();

        //set Menu-Icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recentlyAddedAccess = getIntent().getBooleanExtra(EXTRA_RECENT_ACTIVITY_ADD_ACCESS, false);

        final AppFragment extraAppFragment = (AppFragment) getIntent().getSerializableExtra(EXTRA_STARTING_FRAGMENT);
        switchToFragment(extraAppFragment != null ? extraAppFragment : AppFragment.TRANSACTION_OVERVIEW);
    }

    private void initializeDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.main_menu_drawer_opened, R.string.main_menu_drawer_closed) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        // add the Toggle as Listener
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    /**
     * Switch to the specified fragment
     */
    private void switchToFragment(final AppFragment fragment) {
        switch (fragment) {
            case BANK_ACCESSES:
                setTitle(getString(R.string.banking_overview_title));
                navigationView.setCheckedItem(R.id.main_menu_nav_bank_accesses);

                final ExpandableBankFragment expandableBankFragment = new ExpandableBankFragment();
                openFragment(expandableBankFragment);
                break;
            case TRANSACTION_OVERVIEW:
                setTitle(getString(R.string.transaction_overview_title));
                navigationView.setCheckedItem(R.id.main_menu_nav_transaction_overview);

                final TransactionOverviewFragment transactionOverviewFragment = new TransactionOverviewFragment();
                transactionOverviewFragment.setCheckedMap(checkedBankAccounts);
                transactionOverviewFragment.setRecentlyAddedAccessFlag(recentlyAddedAccess);
                openFragment(transactionOverviewFragment);
                break;
            case SAVING_ACCOUNTS:
                setTitle(getString(R.string.saving_accounts_title));
                navigationView.setCheckedItem(R.id.main_menu_nav_saving_accounts);

                final SavingAccountsFragment savingAccountsFragment = new SavingAccountsFragment();
                openFragment(savingAccountsFragment);
                break;
            case CONTACTS:
                setTitle(R.string.contacts_title);
                navigationView.setCheckedItem(R.id.main_menu_nav_contacts);
                final ContactsFragment contactsFragment = new ContactsFragment();
                openFragment(contactsFragment);
                break;
            default:
                Log.e(TAG, "Trying to change to unhandled fragment " + fragment.name());
                break;
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        checkedBankAccounts.clear(); // TODO maybe find better solution?!
        switch (item.getItemId()) {
            case R.id.main_menu_nav_bank_accesses:
                switchToFragment(AppFragment.BANK_ACCESSES);
                break;
            case R.id.main_menu_nav_transaction_overview:
                switchToFragment(AppFragment.TRANSACTION_OVERVIEW);
                break;
            case R.id.main_menu_nav_saving_accounts:
                switchToFragment(AppFragment.SAVING_ACCOUNTS);
                break;
            case R.id.main_menu_nav_contacts:
                switchToFragment(AppFragment.CONTACTS);
                break;
            case R.id.main_menu_nav_settings:
                startActivity(new Intent(MainMenu.this, SettingsActivity.class));
                break;
            case R.id.main_menu_nav_logout:
                executeLogout();
                break;
            default:
                Log.e(TAG, "Unhandled menu item: " + item.getTitle());
                break;
        }
        drawerLayout.closeDrawers();

        return true;
    }

    private void openFragment(Fragment fragment) {
        if (null != fragment) {
            final FragmentManager manager = getFragmentManager();
            final FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_menu_content, fragment);
            transaction.commit();
        }
    }

    private void executeLogout() {
        authenticationProvider.logout()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(@io.reactivex.annotations.NonNull String s) throws Exception {

                                   authenticationProvider.deleteSavedLoginData(getApplicationContext());
                                   final Intent intent = new Intent(getApplicationContext(), OidcAuthenticationActivity.class);
                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(intent);
                                   finish();
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(@io.reactivex.annotations.NonNull Throwable throwable) {
                                   Log.e(TAG, "Error occured in Observable from logout.");
                                   Toast toast = Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT);
                                   toast.show();
                               }
                           }
                );
    }

    public void switchToTransactionOverview(final HashMap<String, Boolean> checkedBankAccounts) {
        this.checkedBankAccounts = checkedBankAccounts;
        switchToFragment(AppFragment.TRANSACTION_OVERVIEW);
    }
}
