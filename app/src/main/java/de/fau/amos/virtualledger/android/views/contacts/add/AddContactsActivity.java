package de.fau.amos.virtualledger.android.views.contacts.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.api.sync.Toaster;
import de.fau.amos.virtualledger.android.dagger.App;
import de.fau.amos.virtualledger.android.data.ContactsDataManager;
import de.fau.amos.virtualledger.android.views.menu.MainMenu;
import de.fau.amos.virtualledger.dtos.Contact;

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

    @OnClick(R.id.button_addContacts_submit)
    void submit() {
        final String email = emailAdr.getText().toString();

        Toaster toaster = new Toaster(getApplicationContext())//
                .pushSuccessMessage(email + " was successfully added").pushTechnicalErrorMessage(
                        "User not found/No connection to server, please try again");

        contactsDataManager.add(new Contact(email), toaster);

        Intent intent = new Intent(this, MainMenu.class);
        intent.putExtra(MainMenu.EXTRA_STARTING_FRAGMENT, MainMenu.AppFragment.CONTACTS);
        startActivity(intent);

        finish();
    }

}
