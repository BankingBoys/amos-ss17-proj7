package de.fau.amos.virtualledger.android.views.contacts;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.ContactsDataManager;
import de.fau.amos.virtualledger.android.data.SyncFailedException;
import de.fau.amos.virtualledger.android.views.contacts.add.AddContactsActivity;

/**
 * Created by Simon on 04.07.2017.
 */

public class NoContactsFragment extends Fragment implements Observer{

    Button addButton;
    @Inject
    ContactsDataManager contactsDataManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        ((App) getActivity().getApplication()).getNetComponent().inject(this);
        addButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent addContactsIntent = new Intent(getActivity(), AddContactsActivity.class);
                        startActivity(addContactsIntent);
                    }
                }
        );

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_no_contacts_saved, container, false);
        addButton = (Button) view.findViewById(R.id.contacts_add_button);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        contactsDataManager.addObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        contactsDataManager.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            if(contactsDataManager.getAll().size() > 0) {
                getActivity().getFragmentManager().popBackStack();
            }
        } catch (SyncFailedException e) {
            Toast.makeText(getActivity(), "Error in getting Contacts", Toast.LENGTH_SHORT).show();
        }
    }
}


