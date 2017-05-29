package de.fau.amos.virtualledger.android.bankingOverview.expandableList.Adapter;

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
import de.fau.amos.virtualledger.android.bankingOverview.expandableList.model.Group;
import de.fau.amos.virtualledger.android.api.banking.BankingProvider;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.BankAccountNameExtractor;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.DeleteBankAccountAction;
import de.fau.amos.virtualledger.android.bankingOverview.deleteBankAccessAccount.LongClickDeleteListenerSingleItem;
import de.fau.amos.virtualledger.dtos.BankAccount;

public class ExpandableAdapterBanking extends BaseExpandableListAdapter {

    /**
     *
     */
    public Activity listActivity;
    private LayoutInflater inflater;
    private final SparseArray<Group> groups;
    // TODO refavtor so dagger injects directly into deleteAction
    private BankingProvider bankingProvider;

    /**
     *
     */
    public ExpandableAdapterBanking(Activity activity, SparseArray<Group> groups) {
        this.listActivity = activity;
        this.groups = groups;
        inflater = activity.getLayoutInflater();
    }

    /**
     *
     */
    public void setBankingProvider(BankingProvider bankingProvider) {
        this.bankingProvider = bankingProvider;
    }

    /**
     *
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    /**
     *
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    /**
     *
     * @param groupPosition - position on the view where the group is located
     * @param childPosition - position of the associated child
     * @param isLastChild - flag if the current child on the childPosition is the last child
     * @param convertView - associated view of the children
     * @param parent - ViewGroup parent of the current view
     * @return resulting view
     * click listener for the children can be placed here
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
            convertView = inflater.inflate(R.layout.banking_overview_expandablelist_detail, null);
        }

        BankAccountNameExtractor getName = new BankAccountNameExtractor();
        convertView.setOnLongClickListener(
                new LongClickDeleteListenerSingleItem(
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
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    /**
     *
     */
    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    /**
     *
     */
    @Override
    public int getGroupCount() {
        return groups.size();
    }

    /**
     *
     */
    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    /**
     *
     */
    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    /**
     *
     */
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    /**
     * @param groupPosition - position of the group
     * @param isExpanded    - if the group is expanded
     * @param convertView   - view which needs to be called
     * @param parent        - parent of the view
     * @return group view
     * click listener can be located here
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        Group group = (Group) getGroup(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.banking_overview_expandablelist_group, null);
        }

        CheckedTextView checkedView = null;
        TextView bankBalance = null;

        checkedView = (CheckedTextView) convertView.findViewById(R.id.bankAccessNameView);

        bankBalance = (TextView) convertView.findViewById(R.id.bankAccessBalanceView);
        checkedView.setText(group.bankAccess.getName());
        double bankBalanceDouble = group.bankAccess.getBalance();
        String bankBalanceString = String.format(Locale.GERMAN, "%.2f", bankBalanceDouble);
        bankBalance.setText(bankBalanceString);
        checkedView.setChecked(isExpanded);
        return convertView;
    }

    public int getIndexForGroup(Group group) {
        return this.groups.indexOfValue(group);
    }

    /**
     *
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     *
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}