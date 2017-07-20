package de.fau.amos.virtualledger.android.views.contacts;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.views.contacts.delete.ContactsDeleteDialog;
import de.fau.amos.virtualledger.dtos.Contact;

/**
 * Created by Simon on 01.07.2017.
 */

public class ContactsAdapter extends ArrayAdapter<Contact> {

    private Activity activity;

    public ContactsAdapter(Activity activity, int layout, List<Contact> contacts) {
        super(activity, layout, contacts);
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contacts_list_item, parent, false);
        }
        final Contact contact = super.getItem(position);

        TextView contactName = (TextView) convertView.findViewById(R.id.id_contactName);
        contactName.setText(contact.getFirstName() + " " + contact.getLastName());

        contactName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final ContactsDeleteDialog deleteDialog = new ContactsDeleteDialog(activity, getContext(), contact);
                deleteDialog.show();
                return true;
            }
        });

        return convertView;
    }


}
