package de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.Adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.data.BankingDataManager;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.BankAccountNameExtractor;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.DeleteBankAccountAction;
import de.fau.amos.virtualledger.android.views.bankingOverview.deleteBankAccessAccount.LongClickDeleteListenerSingleItem;
import de.fau.amos.virtualledger.android.views.bankingOverview.expandableList.model.Group;
import de.fau.amos.virtualledger.dtos.BankAccount;

public class ExpandableAdapterBanking extends BaseExpandableListAdapter {

    private Activity listActivity;
    private LayoutInflater inflater;
    private final SparseArray<Group> groups;
    private BankingDataManager bankingDataManager;
    private HashMap<BankAccount, Boolean> mappingCheckBoxes = new HashMap<>();

    public ExpandableAdapterBanking(Activity activity, SparseArray<Group> groups, final BankingDataManager bankingDataManager, HashMap<BankAccount, Boolean> mappingCheckBoxes) {
        this.listActivity = activity;
        this.groups = groups;
        inflater = activity.getLayoutInflater();
        this.bankingDataManager = bankingDataManager;
        this.mappingCheckBoxes = mappingCheckBoxes;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final BankAccount children = (BankAccount) getChild(groupPosition, childPosition);
        final String bankName = children.getName();
        final double bankBalance = children.getBalance();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.banking_overview_expandablelist_detail, parent, false);
        }


        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.banking_overview_checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mappingCheckBoxes.put(groups.get(groupPosition).children.get(childPosition), checkBox.isChecked());
            }
        });
        final Boolean checkedStatus = mappingCheckBoxes.get(groups.get(groupPosition).children.get(childPosition)) ;
        if(checkedStatus != null) {
            checkBox.setChecked(checkedStatus);
        }
        BankAccountNameExtractor getName = new BankAccountNameExtractor();
        convertView.setOnLongClickListener(
                new LongClickDeleteListenerSingleItem(
                        listActivity,
                        groups.get(groupPosition).bankAccess,
                        children,
                        getName,
                        new DeleteBankAccountAction(bankingDataManager)
                ));

        final TextView textBankName = (TextView) convertView.findViewById(R.id.bankAccountNameView);
        textBankName.setText(bankName);
        final TextView textBankBalance = (TextView) convertView.findViewById(R.id.bankAccountBalanceView);
        final String bankBalanceString = String.format(Locale.GERMAN, "%.2f", bankBalance);
        textBankBalance.setText(bankBalanceString);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        Group group = (Group) getGroup(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.banking_overview_expandablelist_group, parent, false);
        }

        final CheckedTextView checkedView = (CheckedTextView) convertView.findViewById(R.id.bankAccessNameView);

        final TextView bankBalance = (TextView) convertView.findViewById(R.id.bankAccessBalanceView);
        checkedView.setText(group.bankAccess.getName());
        final double bankBalanceDouble = group.bankAccess.getBalance();
        final String bankBalanceString = String.format(Locale.GERMAN, "%.2f", bankBalanceDouble);
        bankBalance.setText(bankBalanceString);
        checkedView.setChecked(isExpanded);
        return convertView;
    }

    public int getIndexForGroup(Group group) {
        return this.groups.indexOfValue(group);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}