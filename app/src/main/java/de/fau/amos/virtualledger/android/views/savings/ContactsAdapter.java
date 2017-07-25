package de.fau.amos.virtualledger.android.views.savings;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.dtos.Contact;

public class ContactsAdapter extends ArrayAdapter<Contact> {


    public ContactsAdapter(Activity activity, int layout, List<Contact> data) {
        super(activity, layout, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contacts_list_item_small, parent, false);
        }
        final Contact contact = this.getItem(position);
        updateText(convertView, R.id.name, contact.getFirstName() + " " + contact.getLastName());
        return convertView;
    }

    private void updateText(View convertView, int id, String text) {
        TextView goalBalance = (TextView) convertView.findViewById(id);
        goalBalance.setText(text);
    }
}
