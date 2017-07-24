package de.fau.amos.virtualledger.android.views.savings;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.fau.amos.virtualledger.R;
import de.fau.amos.virtualledger.dtos.BankAccountIdentifier;

public class AccessAdapter extends ArrayAdapter<BankAccountIdentifier> {


    public AccessAdapter(Activity activity, int layout, List<BankAccountIdentifier> data) {
        super(activity, layout, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bankaccess_list_item_small, parent, false);
        }
        final BankAccountIdentifier bankAccess = this.getItem(position);
        updateText(convertView, R.id.name, bankAccess.getAccountid());
        return convertView;
    }

    private void updateText(View convertView, int id, String text) {
        TextView goalBalance = (TextView) convertView.findViewById(id);
        goalBalance.setText(text);
    }
}
