package de.fau.amos.virtualledger.android.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.App;
import de.fau.amos.virtualledger.android.ListView.Adapter.BankAccessListAdapter;
import de.fau.amos.virtualledger.android.MainActivity_Menu;
import de.fau.amos.virtualledger.android.auth.AuthenticationProvider;
import de.fau.amos.virtualledger.dtos.BankAccess;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import javax.inject.Inject;

/**
 * Created by Simon on 20.05.2017.
 */

public class BankAccessListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     *
     */
    @Inject
    AuthenticationProvider authenticationProvider;

    BankAccessListAdapter bankAccessAdapter;

    List<BankAccess> testList = new ArrayList<BankAccess>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
        //Todo: get the real Bank Account information
        testList  = authenticationProvider.getBankAccess();
        if(testList == null) {
            testList  = authenticationProvider.getBankAccess();
        }
        if(testList == null) {
            Fragment fragment = new NoBankingAccessesFragment();
            openFragment(fragment);
        }
        bankAccessAdapter = new BankAccessListAdapter(getActivity(), testList);
        setListAdapter(bankAccessAdapter);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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
}
