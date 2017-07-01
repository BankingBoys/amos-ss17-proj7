package de.fau.amos.virtualledger.android.views.contacts;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.model.Contact;

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
        return convertView;
    }
}
