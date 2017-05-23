package de.fau.amos.virtualledger.android.deleteaction;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.ExpandableList.Adapter.ExpandableAdapterBanking;
import de.fau.amos.virtualledger.android.ExpandableList.model.Group;
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
    private ExpandableAdapterBanking adapter;

    /**
     * LongClick lisntens to a list
     * @param listenedObject = the referenced activity
     * @param elementList = the list of modelobject presented in the view
     * @param getName = creates a name of a single model object out of the list
     * @param approvedAction = executes the action after user confirms the delete dialog
     *
     */
    public LongClickDeleteListenerList(ExpandableAdapterBanking adapter,Activity listenedObject, List<BankAccess> elementList, BiFunction<BankAccess,BankAccount,String> getName, BiConsumer<BankAccess, BankAccount> approvedAction) {
        this.listenedObject = listenedObject;
        this.bankAccesses = elementList;
        this.getName = getName;
        this.approvedAction = approvedAction;
        this.adapter = adapter;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TextView bankBalance = (TextView) view.findViewById(R.id.bankAccessNameView);

        ExpandableListView listView = (ExpandableListView) parent;
        Group group = (Group) listView.getAdapter().getItem(position);
        int index = this.adapter.getIndexForGroup(group);

        DeleteDialog tDeleteDialog = new DeleteDialog(listenedObject, this.bankAccesses.get(index),null, getName, approvedAction);
        tDeleteDialog.show();
        return true;
    }


    private BankAccess getAccessByName(List<BankAccess> list, String name){
        for (BankAccess bankAccess: list) {
            if (bankAccess.getName().equals(name))
                return bankAccess;
        }
        throw new IllegalArgumentException("No bank access found with name:"+name);
    }
}
