package de.fau.amos.virtualledger.android.Fragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by sebastian on 21.05.17.
 */

public class LongClickListener implements AdapterView.OnItemLongClickListener {

    private final ListFragment listenedObject;

    public LongClickListener(ListFragment listenedObject){
        this.listenedObject = listenedObject;
    }
    
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(listenedObject.getActivity());
        alert.setTitle("DELETE CONFIRMATION");
        alert.setMessage("Are you sure to delete the bankaccess no. "+id);
        alert.setPositiveButton("YES", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do your work here
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("NO", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();


        return true;
    }
}
