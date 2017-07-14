package de.fau.amos.virtualledger.android.views.savings.add;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import de.fau.amos.virtualledger.R;


class PeopleAdapter  extends ArrayAdapter<String> {
    @SuppressWarnings("unused")
    private static final String TAG = PeopleAdapter.class.getSimpleName();

    private Activity activity;

    PeopleAdapter(Activity activity, int layout, ArrayList<String> data) {
        super(activity, layout, data);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.people_list_item, parent, false);
        }

        return convertView;
    }

}
