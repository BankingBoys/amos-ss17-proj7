package de.fau.amos.virtualledger.android.Fragments;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.android.ListView.Adapter.BankAccessListAdapter;
import de.fau.amos.virtualledger.dtos.BankAccess;

/**
 * Created by Simon on 20.05.2017.
 */

public class BankAccessListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    BankAccessListAdapter bankAccessAdapter;


    //For Test Purposes fake BankAccesses

    BankAccess test = new BankAccess("0", "hallo");
    BankAccess test2 = new BankAccess("1", "hallo2");
    List<BankAccess> testList = new ArrayList<BankAccess>();



   /* //TOdo: use the List of BankAccesses for the real List
    String[] a = new String[] {"A", "B", "C", "D"};
    ArrayAdapter<String> bankAccessAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_bankaccess, )*/



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Todo: get the real Bank Account information
        testList.add(test);
        testList.add(test2);
        bankAccessAdapter = new BankAccessListAdapter(getActivity(), testList);
        setListAdapter(bankAccessAdapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                Toast.makeText(getActivity(), "On long click listener", Toast.LENGTH_LONG).show();
                return true;
            }
        });
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
}
