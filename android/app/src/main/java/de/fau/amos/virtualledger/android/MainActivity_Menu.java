package de.fau.amos.virtualledger.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.android.menu.adapter.MenuAdapter;
import de.fau.amos.virtualledger.android.menu.model.ItemSlidingMenu;
import retrofit2.Retrofit;

/**
 * Created by Simon on 13.05.2017.
 */

public class MainActivity_Menu extends AppCompatActivity {

    LoginActivity loginContext = new LoginActivity();

    @Inject
    AuthenticationProvider authenticationProvider;

    /**
     *
     */
    @Inject
    Retrofit retrofit;


    private List<ItemSlidingMenu> slidingItems;
    private MenuAdapter menuAdapter;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private RelativeLayout content;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        //init
        init();

        //items for slide list
        configureItemsForMenu();

        //set Menu-Icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //title -- use if a title is necessary
        //setTitle(slidingItems.get(0).getImageTitle());

        //select
        listView.setItemChecked(0, true);

        //Close sliding menu
        drawerLayout.closeDrawer(listView);

        //starting fragment -- if necessary add the start fragment here
        //replaceFragment(0);

        //click on items
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> root, View view, int pos, long id) {
                        //title
                        setTitle(slidingItems.get(pos).getImageTitle());
                        //items selected
                        listView.setItemChecked(pos, true);

                        replaceFragment(pos);

                        //Close
                        drawerLayout.closeDrawer(listView);
                    }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {

            /**
             *
             * @param drawerView
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            /**
             *
             * @param drawerView
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        // add the Toggle as Listener
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

    }

    /**
     *@methodtype initialization
     */
    private void init() {
        ((App) getApplication()).getNetComponent().inject(this);
        listView = (ListView) findViewById(R.id.sliding_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content = (RelativeLayout)findViewById(R.id.content);
        slidingItems = new ArrayList<>();
    }

    /**
     *@methodtype initialization
     */
    private void configureItemsForMenu() {
        slidingItems.add(new ItemSlidingMenu(R.drawable.icon_logout, "Logout"));
/*        slidingItems.add(new ItemSlidingMenu(R.drawable.icon_logout, "Login"));*/
        menuAdapter = new MenuAdapter(this, slidingItems);
        listView.setAdapter(menuAdapter);
    }

    /**
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     *
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.m_add_bank_access:
                final Intent addBankAccessIntent = new Intent(this, AddBankAccessActivity.class);
                startActivity(addBankAccessIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    /**
     *
     * @param pos
     */
    private void replaceFragment(int pos) {
        Fragment fragment = null;
        switch (pos) {
            case 0:
                executeNextActivity();
                break;
            //new Fragments can be added her
            default:
                fragment = new Fragment();
                openFragment(fragment);
                break;
        }

    }

    /**
     *
     * @param fragment
     */
    private void openFragment(Fragment fragment) {
        if(null!=fragment) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }


/*    *//**
     *
     *
     *//*
    private void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }*/

    /**
     *
     */
    public void executeNextActivity(){
        logout();
        authenticationProvider.deleteSavedLoginData(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     *
     */
    public void logout() {
        authenticationProvider.logout();
    }

}
