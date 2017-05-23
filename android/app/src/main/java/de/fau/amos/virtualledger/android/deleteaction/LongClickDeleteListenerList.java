package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.functions.BiConsumer;
import de.fau.amos.virtualledger.android.functions.BiFunction;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * LongClick lisntens to a list
 * Created by sebastian on 21.05.17.
 */

public class LongClickDeleteListenerList implements AdapterView.OnItemLongClickListener {

    private final Activity listenedObject;
    private List<BankAccess> bankAccesses = new ArrayList<BankAccess>();
    private BiFunction<BankAccess,BankAccount,String> getName;
    private BiConsumer<BankAccess,BankAccount> approvedAction;

    /**
     * LongClick lisntens to a list
     * @param listenedObject = the referenced activity
     * @param elementList = the list of modelobject presented in the view
     * @param getName = creates a name of a single model object out of the list
     * @param approvedAction = executes the action after user confirms the delete dialog
     */
    public LongClickDeleteListenerList(Activity listenedObject, List<BankAccess> elementList, BiFunction<BankAccess,BankAccount,String> getName, BiConsumer<BankAccess, BankAccount> approvedAction) {
        this.listenedObject = listenedObject;
        this.bankAccesses = elementList;
        this.getName = getName;
        this.approvedAction = approvedAction;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TextView bankBalance = (TextView) view.findViewById(R.id.bankAccessNameView);
        BankAccess clickedModelObject = getAccessByName(this.bankAccesses, bankBalance.getText().toString());


        DeleteDialog tDeleteDialog = new DeleteDialog(listenedObject, clickedModelObject,null, getName, approvedAction);
        tDeleteDialog.show();
        return true;
    }


    private BankAccess getAccessByName(List<BankAccess> list, String name){
        for (BankAccess t: list) {
            if (getName.apply(t,null).equals(name))
                return t;
        }
        throw new IllegalArgumentException("No bank access found with name:"+name);
    }
}
