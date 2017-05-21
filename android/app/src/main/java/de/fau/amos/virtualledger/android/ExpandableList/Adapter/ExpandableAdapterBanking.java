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

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.android.ExpandableList.Group;
import de.fau.amos.virtualledger.dtos.BankAccount;

public class ExpandableAdapterBanking extends BaseExpandableListAdapter {

    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public Activity activity;

    public ExpandableAdapterBanking(Activity act, SparseArray<Group> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
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
        textBankName = (TextView) convertView.findViewById(R.id.bankAccountNameView);
        textBankName.setText(bankName);
        textBankBalance = (TextView) convertView.findViewById(R.id.bankAccountBalanceView);
        textBankBalance.setText(String.valueOf(bankBalance));
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
            convertView = inflater.inflate(R.layout.expandablelistrow_group, null);
        }
        ((CheckedTextView) convertView).setText(group.bankAccess.getName()+ "   " + group.bankAccess.getBalance());
        ((CheckedTextView) convertView).setChecked(isExpanded);
       return convertView;
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
