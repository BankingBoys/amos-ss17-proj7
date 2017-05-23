package de.fau.amos.virtualledger.android.ExpandableList.Adapter;

/**
 * Created by Simon on 21.05.2017.
 */

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.Locale;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.ExpandableList.model.Group;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.deleteaction.BankAccessNameExtractor;
import de.fau.amos.virtualledger.dtos.BankAccess;
import de.fau.amos.virtualledger.dtos.BankAccount;
import de.fau.amos.virtualledger.android.deleteaction.BankAccountNameExtractor;
import de.fau.amos.virtualledger.android.deleteaction.DeleteBankAccountAction;
import de.fau.amos.virtualledger.android.deleteaction.LongClickDeleteListenerSingleItem;

public class ExpandableAdapterBanking extends BaseExpandableListAdapter {

    /**
     *
     */
    public Activity listActivity;
    private final SparseArray<Group> groups;
    private LayoutInflater inflater;

    // TODO refavtor so dagger injects directly into deleteAction
    private BankingProvider bankingProvider;
    public void setBankingProvider(BankingProvider bankingProvider) {
        this.bankingProvider = bankingProvider;
    }

    /**
     *
     * @param activity from where the adapter is called
     * @param groups which groups are used for the adapter
     * @methodtype constructor
     */
    public ExpandableAdapterBanking(Activity activity, SparseArray<Group> groups) {
        this.listActivity = activity;
        this.groups = groups;
        inflater = activity.getLayoutInflater();
    }

    /**
     *
     * @param groupPosition - position of the group
     * @param childPosition - position of the children
     * @return child
     * @methodtype getter
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    /**
     *
     * @param groupPosition - position of the group
     * @param childPosition - position of the children
     * @return Id of child
     * @methodtype getter
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    /**
     *
     * @param groupPosition - position of the group
     * @param childPosition - position of the children
     * @param isLastChild - checks if it's the last child
     * @param convertView - view which needs to be called
     * @param parent - parent of the View
     * @return child view
     * @methodtype getter
     */
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final BankAccount children = (BankAccount) getChild(groupPosition, childPosition);
        final String bankName = children.getName();
        final double bankBalance = children.getBalance();
        TextView textBankName = null;
        TextView textBankBalance = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expandablelistrow_detail, null);
        }

        BankAccountNameExtractor getName = new BankAccountNameExtractor();
        convertView.setOnLongClickListener(
                new LongClickDeleteListenerSingleItem<BankAccess,BankAccount>(
                        listActivity,
                        groups.get(groupPosition).bankAccess,
                        children,
                        getName,
                        new DeleteBankAccountAction(listActivity, getName, bankingProvider)
                ));

        textBankName = (TextView) convertView.findViewById(R.id.bankAccountNameView);
        textBankName.setText(bankName);
        textBankBalance = (TextView) convertView.findViewById(R.id.bankAccountBalanceView);
        textBankBalance.setText(String.valueOf(bankBalance));
        return convertView;
    }

    /**
     *
     * @param groupPosition - position of the group
     * @return count of children
     * @methodtype getter
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    /**
     *
     * @param groupPosition - position of the group
     * @return Group
     * @methodtype getter
     */
    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    /**
     *
     * @return get count of group
     * @methodtype getter
     */
    @Override
    public int getGroupCount() {
        return groups.size();
    }

    /**
     *
     * @param groupPosition - position of the group
     */
    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    /**
     *
     * @param groupPosition - position of the group
     */
    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    /**
     *
     * @param groupPosition - position of the group
     * @return Id of group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    /**
     *
     * @param groupPosition - position of the group
     * @param isExpanded - if the group is expanded
     * @param convertView - view which needs to be called
     * @param parent - parent of the view
     * @return group view
     * @methodtype getter
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        Group group = (Group) getGroup(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expandablelistrow_group, null);
        }

        CheckedTextView checkedView = null;
        TextView bankBalance = null;

        checkedView = (CheckedTextView) convertView.findViewById(R.id.bankAccessNameView);

        bankBalance = (TextView) convertView.findViewById(R.id.bankAccessBalanceView);
        checkedView.setText(group.bankAccess.getName());
        double bankBalanceDouble = group.bankAccess.getBalance();
        String bankBalanceString = String.format(Locale.GERMAN, "%.2f",bankBalanceDouble);
        bankBalance.setText(bankBalanceString);
        checkedView.setChecked(isExpanded);
        return convertView;
    }

    /**
     *
     * @return stable ids boolean
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     *
     * @param groupPosition - position of the group
     * @param childPosition - position of the children
     * @return boolean selectable child
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}