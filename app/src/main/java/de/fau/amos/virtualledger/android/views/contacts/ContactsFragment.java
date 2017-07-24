package de.fau.amos.virtualledger.android.views.contacts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import java.util.List;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.views.contacts.add.AddContactsActivity;
import de.fau.amos.virtualledger.android.views.shared.transactionList.DataListening;
import de.fau.amos.virtualledger.dtos.Contact;

/**
 * Created by Simon on 01.07.2017.
 */

public class ContactsFragment extends Fragment implements DataListening {

    private ContactsAdapter adapter;
    private ListView contactListView;
    private ContactsSupplier contactSupplier;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.contactSupplier = new ContactsSupplier(getActivity());
        this.contactSupplier.addDataListeningObject(this);
        adapter = new ContactsAdapter(getActivity(), R.id.contacts_list, contactSupplier.getAll());
        contactListView.setAdapter(adapter);
        this.adapter.sort(new ContactsComparator());
        this.contactSupplier.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.contacts_list, container, false);
        this.contactListView = (ListView) view.findViewById(R.id.contacts_list);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.contacts_add_bar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contacts_add_bar_add:
                final Intent addContactsIntent = new Intent(getActivity(), AddContactsActivity.class);
                startActivity(addContactsIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void notifyDataChanged() {
        this.adapter.clear();
        List<Contact> allContacts= this.contactSupplier.getAll();
        if(allContacts == null || allContacts.size() == 0) {
            final Fragment fragment = new NoContactsFragment();
            openFragment(fragment);
        }
        logger().info("Refreshing contacts overview with " + allContacts + " contacts from: "+this.contactSupplier);
        this.adapter.addAll(allContacts);
        this.adapter.notifyDataSetChanged();
        this.adapter.sort(new ContactsComparator());
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName() + "{" + this.toString() + "}");
    }

    @Override
    public void onPause() {
        super.onPause();
        this.contactSupplier.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.contactSupplier.onResume();
    }

    /**
     * opens a fragment through replacing another fragment
     */
    private void openFragment(final Fragment fragment) {
        if (null != fragment) {
            final FragmentManager manager = getFragmentManager();
            final FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_menu_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

}
