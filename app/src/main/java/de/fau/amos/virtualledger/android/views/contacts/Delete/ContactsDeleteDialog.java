package de.fau.amos.virtualledger.android.views.contacts.Delete;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import de.fau.amos.virtualledger.dtos.Contact;

/**
 * Created by Simon on 18.07.2017.
 */

public class ContactsDeleteDialog {

    private Activity activity;
    private Context context;
    private Contact contact;

    public ContactsDeleteDialog(Activity activity, Context context, Contact contact) {
        this.activity = activity;
        this.context = context;
        this.contact = contact;
    }

    public void show() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("DELETE CONFIRMATION");
        alert.setMessage("Are you sure to delete " + this.contact.getFirstName()+" from your contact List?");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ContactsDeleteAction deleteAction = new ContactsDeleteAction(activity, contact);
                deleteAction.delete();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();

    }


}
