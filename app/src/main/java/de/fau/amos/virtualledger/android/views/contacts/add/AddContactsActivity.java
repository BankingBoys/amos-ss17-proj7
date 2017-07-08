package de.fau.amos.virtualledger.android.views.contacts.add;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.ContactsDataManager;

/**
 * Created by Simon on 08.07.2017.
 */

public class AddContactsActivity extends AppCompatActivity {
   @SuppressWarnings("unused")
    private static final String tag = AddContactsActivity.class.getSimpleName();

    @Inject
    ContactsDataManager contactsDataManager;

    @BindView(R.id.email_addcontacts)
    EditText emailAdr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getNetComponent().inject(this);
        setContentView(R.layout.contacts_activity_add);
        ButterKnife.bind(this);
    }





}
