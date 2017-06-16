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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.authentication.login.LoginActivity;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.views.bankingOverview.addBankAccess.AddBankAccessActivity;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Fragment.ExpandableBankFragment;
import de.fau.amos.virtualledger.android.views.settings.SettingsActivity;
import de.fau.amos.virtualledger.android.views.transactionOverview.TransactionOverviewFragment;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainMenu.class.getSimpleName();

    @Inject
    AuthenticationProvider authenticationProvider;
    @Inject
    BankingDataManager bankingDataManager;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private HashMap<String, Boolean> mappingCheckBoxes = new HashMap<>();
    private int startingFragment;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_sliding_tab);
        Bundle bundle = getIntent().getExtras();
        startingFragment = 2;
        if(bundle != null) {
            startingFragment = bundle.getInt("startingFragment");
            mappingCheckBoxes = (HashMap) bundle.get("checkedMap");
        }
        //init
        init();

        //set Menu-Icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        replaceFragment(startingFragment);

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
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        ((App) getApplication()).getNetComponent().inject(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    protected void onResume() {
        startingFragment = 2;
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.m_add_bank_access:
                final Intent addBankAccessIntent = new Intent(this, AddBankAccessActivity.class);
                startActivity(addBankAccessIntent);
                break;
            case R.id.m_refresh:
                bankingDataManager.sync();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    /**
     * replaces the current fragment with chosen one from the user
     */
    private void replaceFragment(int pos) {
        switch (pos) {
            case 0:
                executeLogout();
                break;

            case 1:
                ExpandableBankFragment fragment = new ExpandableBankFragment();
                openFragment(fragment);
                break;

            case 2:
                TransactionOverviewFragment fragment2;
                fragment2 = new TransactionOverviewFragment();
                fragment2.setCheckedMap(mappingCheckBoxes);
                openFragment(fragment2);
                break;

            //new Fragments can be added her
            default:
                Log.e(TAG, "Menu item pos: {" + pos + "} not found");
                Fragment fragment4;
                fragment4 = new TransactionOverviewFragment();
                openFragment(fragment4);
                break;
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_bank_accesses:
                //title
                setTitle(item.getTitle());
                //items selected
                navigationView.setCheckedItem(item.getItemId());

                ExpandableBankFragment fragment = new ExpandableBankFragment();
                openFragment(fragment);
                break;
            case R.id.nav_transaction_overview:
                //title
                setTitle(item.getTitle());
                //items selected
                navigationView.setCheckedItem(item.getItemId());

                TransactionOverviewFragment fragment2;
                fragment2 = new TransactionOverviewFragment();
                fragment2.setCheckedMap(mappingCheckBoxes);
                openFragment(fragment2);
                break;
            case R.id.nav_settings:
                startActivity(new Intent(MainMenu.this, SettingsActivity.class));
                break;
            case R.id.nav_logout:
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
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void executeLogout() {
        authenticationProvider.logout();
        authenticationProvider.deleteSavedLoginData(this);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
