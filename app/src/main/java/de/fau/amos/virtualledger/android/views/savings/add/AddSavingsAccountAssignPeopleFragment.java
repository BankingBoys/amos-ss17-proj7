package de.fau.amos.virtualledger.android.views.savings.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.Logger;

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.model.SavingsAccount;
import de.fau.amos.virtualledger.android.views.contacts.ContactsSupplier;
import de.fau.amos.virtualledger.android.views.contacts.add.AddContactsActivity;
import de.fau.amos.virtualledger.android.views.shared.transactionList.DataListening;
import de.fau.amos.virtualledger.dtos.Contact;

public class AddSavingsAccountAssignPeopleFragment extends AddSavingsAccountPage implements DataListening {
    @SuppressWarnings("unused")
    private static final String TAG = AddSavingsAccountAssignPeopleFragment.class.getSimpleName();
    private PeopleAssignedListener peopleAssignetListener;
    private SavingsAccount dataModel;
    private ContactsSupplier contactSupplier;
    private PeopleAdapter adapter;
    private Contact meContact = new Contact("", "Me", "");

    @Override
    public void consumeDataModel(SavingsAccount account) {
        this.dataModel = account;
        if (this.peopleAssignetListener != null) {
            this.peopleAssignetListener.updateText();
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.saving_accounts_add_friends, container, false);
        ButterKnife.bind(this, view);
        this.contactSupplier = new ContactsSupplier(getActivity());
        this.contactSupplier.onResume();
        contactSupplier.addDataListeningObject(this);

        ListView peopleList = (ListView) view.findViewById(R.id.list);

        TextView conclusionTextView = (TextView) view.findViewById(R.id.conclusion_text);

        this.peopleAssignetListener = new PeopleAssignedListener(getContext(), conclusionTextView, conclusionTextView.getText().toString(), this.dataModel);
        adapter = new PeopleAdapter(getActivity(), R.id.list, getContacts(), peopleAssignetListener);
        peopleList.setAdapter(adapter);
        return view;
    }

    @NonNull
    private ArrayList<Contact> getContacts() {
        ArrayList<Contact> allContacts = new ArrayList<>();
        allContacts.add(meContact);
        allContacts.addAll(contactSupplier.getAll());
        return allContacts;
    }


    @Override
    public void fillInData(final SavingsAccount savingsAccount) {
        logger().info("Contacts already synced into data model:" + dataModel.getAdditionalAssignedContacts());
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName());
    }

    @OnClick(R.id.add_contacts)
    public void onClickAddContacts() {
        logger().info("Add contacts called");
        Intent addContactsIntent = new Intent(getActivity(), AddContactsActivity.class);
        startActivity(addContactsIntent);
    }

    @Override
    public void notifyDataChanged() {
        logger().info("Data-Set changed");
        if (this.adapter == null) {
            return;
        }
        logger().info("Refreshing data");
        this.adapter.clear();
        this.adapter.addAll(getContacts());
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        logger().info("Resuming");
        super.onResume();
        this.contactSupplier.addDataListeningObject(this);
    }

    @Override
    public void onPause() {
        logger().info("on Pause");
        super.onPause();
        this.contactSupplier.deregister(this);
    }
}
