package de.fau.amos.virtualledger.android.views.contacts.add;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import de.fau.amos.virtualledger.android.data.ContactsDataManager;

/**
 * Created by Simon on 08.07.2017.
 */

public class AddContactsActivity extends AppCompatActivity {
   @SuppressWarnings("unused")
    private static final String tag = AddContactsActivity.class.getSimpleName();

    @Inject
    ContactsDataManager contactsDataManager;


}
