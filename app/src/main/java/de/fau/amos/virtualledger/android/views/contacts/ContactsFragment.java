package de.fau.amos.virtualledger.android.views.contacts;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

/**
 * Created by Simon on 01.07.2017.
 */

public class ContactsFragment extends Fragment {
    private ContactsAdapter adapter;
    private ListView contactListView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
