package de.fau.amos.virtualledger.android.views.savings.add;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.logging.Logger;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.dtos.Contact;


class PeopleAdapter extends ArrayAdapter<Contact> {

    private Activity activity;
    private PeopleAssignedListener listener;

    PeopleAdapter(Activity activity, int layout, ArrayList<Contact> data, PeopleAssignedListener listener) {
        super(activity, layout, data);
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Contact person = super.getItem(position);

        String personName = person.getFirstName() + " " + person.getLastName();
        if ("Me".equals(person.getFirstName())) {
            personName = "Me";
        }

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.people_list_item, parent, false);
        }

        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        checkBox.setText(personName);
        logger().info("PersonName:" + personName);
        if ("Me".equals(personName)) {
            checkBox.setClickable(false);
            checkBox.setEnabled(false);
            checkBox.setChecked(true);
            this.listener.seĺectPerson(person);
        } else {
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        PeopleAdapter.this.listener.seĺectPerson(person);
                    } else {
                        PeopleAdapter.this.listener.deselectPerson(person);
                    }
                }
            });
        }

        return convertView;
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName());
    }

}
