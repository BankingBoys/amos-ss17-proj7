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


class PeopleAdapter extends ArrayAdapter<String> {
    @SuppressWarnings("unused")
    private static final String TAG = PeopleAdapter.class.getSimpleName();

    private Activity activity;

    PeopleAdapter(Activity activity, int layout, ArrayList<String> data) {
        super(activity, layout, data);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String personName = super.getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.people_list_item, parent, false);
        }
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        checkBox.setText(personName);
        logger().info("PersonName:"+personName);
        if ("Me".equals(personName)){
            checkBox.setClickable(false);
            checkBox.setEnabled(false);
            checkBox.setChecked(true);
        }
        return convertView;
    }

    private Logger logger() {
        return Logger.getLogger(this.getClass().getCanonicalName());
    }

}
