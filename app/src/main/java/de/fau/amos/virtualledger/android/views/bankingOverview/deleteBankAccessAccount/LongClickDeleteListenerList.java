package de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Adapter.ExpandableAdapterBanking;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.model.Group;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.functions.BiConsumer;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.functions.BiFunction;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;

/**
 * LongClick lisntens to a list
 * Created by sebastian on 21.05.17.
 */

public class LongClickDeleteListenerList implements AdapterView.OnItemLongClickListener {

    private final Activity listenedObject;
    private List<BankAccess> bankAccesses = new ArrayList<BankAccess>();
    private BiFunction<BankAccess, BankAccount, String> getName;
    private BiConsumer<BankAccess, BankAccount> approvedAction;
    private ExpandableAdapterBanking adapter;


    /**
     * @param adapter        the adapter for getting the GroupNumber out of the absolute GroupIndex (is different when groups are exapnded)
     * @param listenedObject the activity
     * @param elementList    the  list of accesses
     * @param getName        the naming function
     * @param approvedAction the action if the delete dialog is approved
     */
    public LongClickDeleteListenerList(ExpandableAdapterBanking adapter, Activity listenedObject, List<BankAccess> elementList, BiFunction<BankAccess, BankAccount, String> getName, BiConsumer<BankAccess, BankAccount> approvedAction) {
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

        DeleteDialog tDeleteDialog = new DeleteDialog(view.getContext(),listenedObject, this.bankAccesses.get(index), null, getName, approvedAction);
        tDeleteDialog.show();
        return true;
    }
}
